/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.AppUser;
import hr.algebra.model.Category;
import hr.algebra.model.Tag;
import hr.algebra.model.NasaImage;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author vitoz
 */
public interface Repository {

    Optional<AppUser> loginUser(String username, String passwordHash) throws Exception;

    int registerUser(AppUser user) throws Exception;

    List<AppUser> selectUsers() throws Exception;

    int createCategory(Category category) throws Exception;

    void updateCategory(int id, Category category) throws Exception;

    void deleteCategory(int id) throws Exception;

    Optional<Category> selectCategory(int id) throws Exception;

    List<Category> selectCategories() throws Exception;

    int createTag(Tag tag) throws Exception;

    void updateTag(int id, Tag tag) throws Exception;

    void deleteTag(int id) throws Exception;

    Optional<Tag> selectTag(int id) throws Exception;

    List<Tag> selectTags() throws Exception;

    int createNasaImage(NasaImage nasaImage) throws Exception;

    void createNasaImages(List<NasaImage> nasaImages) throws Exception;

    void updateNasaImage(int id, NasaImage nasaImage) throws Exception;

    void deleteNasaImage(int id) throws Exception;

    Optional<NasaImage> selectNasaImage(int id) throws Exception;

    List<NasaImage> selectNasaImages() throws Exception;

    void assignTagToImage(int nasaImageId, int tagId) throws Exception;

    void removeTagFromImage(int nasaImageId, int tagId) throws Exception;

    List<Tag> selectTagsForImage(int nasaImageId) throws Exception;

    void clearApplicationData() throws Exception;

    default List<NasaImage> selectNasaImagesForExport(
            Predicate<NasaImage> filter,
            Function<NasaImage, LocalDateTime> sortBy,
            boolean oldestFirst,
            int limit) throws Exception {

        Comparator<NasaImage> comparator = Comparator.comparing(sortBy);

        if (!oldestFirst) {
            comparator = comparator.reversed();
        }

        long exportLimit = limit <= 0 ? Long.MAX_VALUE : limit;

        return selectNasaImages()
                .stream()
                .filter(filter)
                .sorted(comparator)
                .limit(exportLimit)
                .collect(Collectors.toList());
    }
}
