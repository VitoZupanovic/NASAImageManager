/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vitoz
 */
@XmlRootElement(name = "nasaImageArchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class NasaImageArchive {

    @XmlElementWrapper(name = "nasaImages")
    @XmlElement(name = "nasaImage")
    private List<NasaImage> nasaImages;

    public NasaImageArchive() {
    }

    public NasaImageArchive(List<NasaImage> nasaImages) {
        this.nasaImages = nasaImages;
    }

    public List<NasaImage> getNasaImages() {
        return nasaImages;
    }
}
