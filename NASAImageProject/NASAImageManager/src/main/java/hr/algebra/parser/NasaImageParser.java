/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parser;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Category;
import hr.algebra.model.NasaImage;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;
import javax.xml.namespace.QName;

/**
 *
 * @author vitoz
 */
public class NasaImageParser {

    private static final String RSS_URL = "https://www.nasa.gov/feeds/iotd-feed/";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static final Category DEFAULT_CATEGORY = new Category(1, "NASA Image of the Day");

    public static List<NasaImage> parse() throws IOException, XMLStreamException {
        List<NasaImage> nasaImages = new ArrayList<>();

        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);

        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<RssTag> rssTag = Optional.empty();
            NasaImage nasaImage = null;
            StartElement startElement = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        rssTag = RssTag.from(qName);

                        if (rssTag.isPresent() && rssTag.get() == RssTag.ITEM) {
                            nasaImage = new NasaImage();
                            nasaImage.setCategory(DEFAULT_CATEGORY);
                            nasaImages.add(nasaImage);
                        }

                        if (rssTag.isPresent()
                                && rssTag.get() == RssTag.ENCLOSURE
                                && nasaImage != null
                                && nasaImage.getPicturePath() == null) {

                            Attribute urlAttribute = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));

                            if (urlAttribute != null) {
                                handlePicture(nasaImage, urlAttribute.getValue());
                            }
                        }
                    }

                    case XMLStreamConstants.CHARACTERS -> {
                        if (rssTag.isPresent() && nasaImage != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();

                            if (data.isEmpty()) {
                                break;
                            }

                            switch (rssTag.get()) {
                                case TITLE ->
                                    nasaImage.setTitle(data);
                                case LINK ->
                                    nasaImage.setLink(data);
                                case DESCRIPTION ->
                                    nasaImage.setDescription(data);
                                case PUB_DATE ->
                                    nasaImage.setPublishedDate(parsePublishedDate(data));
                            }
                        }
                    }
                }
            }
        }

        return nasaImages.stream()
                .filter(image -> image.getPicturePath() != null && !image.getPicturePath().trim().isEmpty())
                .collect(Collectors.toList());
    }

    private static LocalDateTime parsePublishedDate(String data) {
        return ZonedDateTime
                .parse(data, DateTimeFormatter.RFC_1123_DATE_TIME)
                .toLocalDateTime();
    }

    private static void handlePicture(NasaImage nasaImage, String pictureUrl) {
        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));

            if (ext.length() > 5) {
                ext = EXT;
            }

            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);

            nasaImage.setPicturePath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(NasaImageParser.class.getName()).log(
                    Level.WARNING,
                    "Image could not be downloaded: " + pictureUrl
            );
        }
    }
}
