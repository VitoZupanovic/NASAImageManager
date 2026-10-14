/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.AppUser;
import hr.algebra.model.Category;
import hr.algebra.model.NasaImage;
import hr.algebra.model.Role;
import hr.algebra.model.Tag;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author vitoz
 */
public class SqlRepository implements Repository {

    private static final String ID_APP_USER = "IDAppUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD_HASH = "PasswordHash";
    private static final String ROLE_NAME = "RoleName";

    private static final String LOGIN_USER = "{ CALL loginUser (?,?) }";
    private static final String REGISTER_USER = "{ CALL registerUser (?,?,?) }";
    private static final String SELECT_USERS = "{ CALL selectUsers }";

    private static final String ID_CATEGORY = "IDCategory";
    private static final String ID_TAG = "IDTag";
    private static final String NAME = "Name";

    private static final String CREATE_CATEGORY = "{ CALL createCategory (?,?) }";
    private static final String UPDATE_CATEGORY = "{ CALL updateCategory (?,?) }";
    private static final String DELETE_CATEGORY = "{ CALL deleteCategory (?) }";
    private static final String SELECT_CATEGORY = "{ CALL selectCategory (?) }";
    private static final String SELECT_CATEGORIES = "{ CALL selectCategories }";

    private static final String CREATE_TAG = "{ CALL createTag (?,?) }";
    private static final String UPDATE_TAG = "{ CALL updateTag (?,?) }";
    private static final String DELETE_TAG = "{ CALL deleteTag (?) }";
    private static final String SELECT_TAG = "{ CALL selectTag (?) }";
    private static final String SELECT_TAGS = "{ CALL selectTags }";

    private static final String ID_NASA_IMAGE = "IDNasaImage";
    private static final String TITLE = "Title";
    private static final String LINK = "Link";
    private static final String DESCRIPTION = "Description";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String CATEGORY_NAME = "CategoryName";

    private static final String CREATE_NASA_IMAGE = "{ CALL createNasaImage (?,?,?,?,?,?,?) }";
    private static final String UPDATE_NASA_IMAGE = "{ CALL updateNasaImage (?,?,?,?,?,?,?) }";
    private static final String DELETE_NASA_IMAGE = "{ CALL deleteNasaImage (?) }";
    private static final String SELECT_NASA_IMAGE = "{ CALL selectNasaImage (?) }";
    private static final String SELECT_NASA_IMAGES = "{ CALL selectNasaImages }";

    private static final String ASSIGN_TAG_TO_IMAGE = "{ CALL assignTagToImage (?,?) }";
    private static final String REMOVE_TAG_FROM_IMAGE = "{ CALL removeTagFromImage (?,?) }";
    private static final String SELECT_TAGS_FOR_IMAGE = "{ CALL selectTagsForImage (?) }";

    private static final String CLEAR_APPLICATION_DATA = "{ CALL clearApplicationData }";

    @Override
    public Optional<AppUser> loginUser(String username, String passwordHash) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(LOGIN_USER)) {

            stmt.setString(USERNAME, username);
            stmt.setString(PASSWORD_HASH, passwordHash);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new AppUser(
                            rs.getInt(ID_APP_USER),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD_HASH),
                            Role.valueOf(rs.getString(ROLE_NAME))
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public int registerUser(AppUser user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(REGISTER_USER)) {

            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD_HASH, user.getPassword());
            stmt.registerOutParameter(ID_APP_USER, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_APP_USER);
        }
    }

    @Override
    public List<AppUser> selectUsers() throws Exception {
        List<AppUser> users = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USERS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new AppUser(
                        rs.getInt(ID_APP_USER),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD_HASH),
                        Role.valueOf(rs.getString(ROLE_NAME))
                ));
            }
        }

        return users;
    }

    @Override
    public int createCategory(Category category) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_CATEGORY)) {

            stmt.setString(NAME, category.getName());
            stmt.registerOutParameter(ID_CATEGORY, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_CATEGORY);
        }
    }

    @Override
    public void updateCategory(int id, Category category) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_CATEGORY)) {

            stmt.setInt(ID_CATEGORY, id);
            stmt.setString(NAME, category.getName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteCategory(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_CATEGORY)) {

            stmt.setInt(ID_CATEGORY, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Category> selectCategory(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_CATEGORY)) {

            stmt.setInt(ID_CATEGORY, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Category(
                            rs.getInt(ID_CATEGORY),
                            rs.getString(NAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Category> selectCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_CATEGORIES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt(ID_CATEGORY),
                        rs.getString(NAME)
                ));
            }
        }

        return categories;
    }

    @Override
    public int createTag(Tag tag) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_TAG)) {

            stmt.setString(NAME, tag.getName());
            stmt.registerOutParameter(ID_TAG, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_TAG);
        }
    }

    @Override
    public void updateTag(int id, Tag tag) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_TAG)) {

            stmt.setInt(ID_TAG, id);
            stmt.setString(NAME, tag.getName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteTag(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_TAG)) {

            stmt.setInt(ID_TAG, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Tag> selectTag(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_TAG)) {

            stmt.setInt(ID_TAG, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Tag(
                            rs.getInt(ID_TAG),
                            rs.getString(NAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Tag> selectTags() throws Exception {
        List<Tag> tags = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_TAGS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tags.add(new Tag(
                        rs.getInt(ID_TAG),
                        rs.getString(NAME)
                ));
            }
        }

        return tags;
    }

    @Override
    public int createNasaImage(NasaImage nasaImage) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_NASA_IMAGE)) {

            stmt.setString(TITLE, nasaImage.getTitle());
            stmt.setString(LINK, nasaImage.getLink());
            stmt.setString(DESCRIPTION, nasaImage.getDescription());
            stmt.setString(PICTURE_PATH, nasaImage.getPicturePath());
            stmt.setString(PUBLISHED_DATE, nasaImage.getPublishedDate().format(NasaImage.DATE_FORMATTER));
            stmt.setInt(ID_CATEGORY, nasaImage.getCategory().getId());
            stmt.registerOutParameter(ID_NASA_IMAGE, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_NASA_IMAGE);
        }
    }

    @Override
    public void createNasaImages(List<NasaImage> nasaImages) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_NASA_IMAGE)) {

            for (NasaImage nasaImage : nasaImages) {
                stmt.setString(TITLE, nasaImage.getTitle());
                stmt.setString(LINK, nasaImage.getLink());
                stmt.setString(DESCRIPTION, nasaImage.getDescription());
                stmt.setString(PICTURE_PATH, nasaImage.getPicturePath());
                stmt.setString(PUBLISHED_DATE, nasaImage.getPublishedDate().format(NasaImage.DATE_FORMATTER));
                stmt.setInt(ID_CATEGORY, nasaImage.getCategory().getId());
                stmt.registerOutParameter(ID_NASA_IMAGE, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateNasaImage(int id, NasaImage nasaImage) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_NASA_IMAGE)) {

            stmt.setInt(ID_NASA_IMAGE, id);
            stmt.setString(TITLE, nasaImage.getTitle());
            stmt.setString(LINK, nasaImage.getLink());
            stmt.setString(DESCRIPTION, nasaImage.getDescription());
            stmt.setString(PICTURE_PATH, nasaImage.getPicturePath());
            stmt.setString(PUBLISHED_DATE, nasaImage.getPublishedDate().format(NasaImage.DATE_FORMATTER));
            stmt.setInt(ID_CATEGORY, nasaImage.getCategory().getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteNasaImage(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_NASA_IMAGE)) {

            stmt.setInt(ID_NASA_IMAGE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<NasaImage> selectNasaImage(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_NASA_IMAGE)) {

            stmt.setInt(ID_NASA_IMAGE, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    NasaImage nasaImage = createNasaImageFromResultSet(rs);
                    nasaImage.getTags().addAll(selectTagsForImage(nasaImage.getId()));
                    return Optional.of(nasaImage);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<NasaImage> selectNasaImages() throws Exception {
        List<NasaImage> nasaImages = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_NASA_IMAGES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                nasaImages.add(createNasaImageFromResultSet(rs));
            }
        }

        return nasaImages;
    }

    @Override
    public void assignTagToImage(int nasaImageId, int tagId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(ASSIGN_TAG_TO_IMAGE)) {

            stmt.setInt(ID_NASA_IMAGE, nasaImageId);
            stmt.setInt(ID_TAG, tagId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void removeTagFromImage(int nasaImageId, int tagId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(REMOVE_TAG_FROM_IMAGE)) {

            stmt.setInt(ID_NASA_IMAGE, nasaImageId);
            stmt.setInt(ID_TAG, tagId);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Tag> selectTagsForImage(int nasaImageId) throws Exception {
        List<Tag> tags = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_TAGS_FOR_IMAGE)) {

            stmt.setInt(ID_NASA_IMAGE, nasaImageId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tags.add(new Tag(
                            rs.getInt(ID_TAG),
                            rs.getString(NAME)
                    ));
                }
            }
        }

        return tags;
    }

    @Override
    public void clearApplicationData() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CLEAR_APPLICATION_DATA)) {

            stmt.executeUpdate();
        }
    }

    private NasaImage createNasaImageFromResultSet(ResultSet rs) throws Exception {
        Category category = new Category(
                rs.getInt(ID_CATEGORY),
                rs.getString(CATEGORY_NAME)
        );

        return new NasaImage(
                rs.getInt(ID_NASA_IMAGE),
                rs.getString(TITLE),
                rs.getString(LINK),
                rs.getString(DESCRIPTION),
                rs.getString(PICTURE_PATH),
                java.time.LocalDateTime.parse(rs.getString(PUBLISHED_DATE), NasaImage.DATE_FORMATTER),
                category
        );
    }

}
