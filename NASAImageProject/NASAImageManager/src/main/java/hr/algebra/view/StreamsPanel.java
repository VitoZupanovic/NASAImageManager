package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.factory.RepositoryFactory;
import hr.algebra.model.NasaImage;
import hr.algebra.utilities.MessageUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author vitoz
 */
public class StreamsPanel extends javax.swing.JPanel {

    private Repository repository;

    public StreamsPanel() {
        initComponents();
        init();
    }

    private void init() {
        try {
            repository = RepositoryFactory.getRepository();
            runStreamDemo();
        } catch (Exception ex) {
            Logger.getLogger(StreamsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Streams error", "Cannot initialize streams panel.");
        }
    }

    private void runStreamDemo() {
        try {
            List<NasaImage> nasaImages = repository.selectNasaImages();

            if (nasaImages.isEmpty()) {
                taResults.setText("There are no NASA images in the database.");
                return;
            }

            StringBuilder sb = new StringBuilder();

            sb.append("ADVANCED STREAM OPERATIONS DEMO\n");
            sb.append("================================\n\n");

            sb.append("Total images in database: ")
                    .append(nasaImages.size())
                    .append("\n\n");

            long distinctCategoryCount = nasaImages.stream()
                    .map(image -> image.getCategory().getName())
                    .distinct()
                    .count();

            sb.append("1. distinct()\n");
            sb.append("Distinct category count: ")
                    .append(distinctCategoryCount)
                    .append("\n\n");

            List<NasaImage> newestImages = nasaImages.stream()
                    .sorted(Comparator.comparing(NasaImage::getPublishedDate).reversed())
                    .collect(Collectors.toList());

            sb.append("2. sorted()\n");
            sb.append("Newest image: ")
                    .append(newestImages.get(0).getTitle())
                    .append("\n\n");

            List<NasaImage> imagesAfterSkippingFirstTwo = newestImages.stream()
                    .skip(2)
                    .collect(Collectors.toList());

            sb.append("3. skip()\n");
            sb.append("Images after skipping first 2 newest images: ")
                    .append(imagesAfterSkippingFirstTwo.size())
                    .append("\n\n");

            Optional<NasaImage> oldestImage = nasaImages.stream()
                    .min(Comparator.comparing(NasaImage::getPublishedDate));

            sb.append("4. min()\n");
            oldestImage.ifPresent(image
                    -> sb.append("Oldest image: ")
                            .append(image.getTitle())
                            .append("\n"));

            sb.append("\n");

            Optional<NasaImage> newestImage = nasaImages.stream()
                    .max(Comparator.comparing(NasaImage::getPublishedDate));

            sb.append("5. max()\n");
            newestImage.ifPresent(image
                    -> sb.append("Newest image: ")
                            .append(image.getTitle())
                            .append("\n"));

            sb.append("\n");

            Optional<NasaImage> firstImageWithLongTitle = nasaImages.stream()
                    .filter(image -> image.getTitle() != null && image.getTitle().length() > 20)
                    .findFirst();

            sb.append("6. findFirst()\n");
            sb.append("First image with title longer than 20 characters: ");
            sb.append(firstImageWithLongTitle
                    .map(NasaImage::getTitle)
                    .orElse("Not found"));
            sb.append("\n\n");

            Optional<NasaImage> anyImageWithDescription = nasaImages.stream()
                    .filter(image -> image.getDescription() != null && !image.getDescription().trim().isEmpty())
                    .findAny();

            sb.append("7. findAny()\n");
            sb.append("Any image with description: ");
            sb.append(anyImageWithDescription
                    .map(NasaImage::getTitle)
                    .orElse("Not found"));
            sb.append("\n\n");

            boolean hasImagesWithPicture = nasaImages.stream()
                    .anyMatch(image -> image.getPicturePath() != null && !image.getPicturePath().trim().isEmpty());

            sb.append("8. anyMatch()\n");
            sb.append("At least one image has a picture path: ")
                    .append(hasImagesWithPicture)
                    .append("\n\n");

            boolean noImagesWithoutTitle = nasaImages.stream()
                    .noneMatch(image -> image.getTitle() == null || image.getTitle().trim().isEmpty());

            sb.append("9. noneMatch()\n");
            sb.append("No images without title: ")
                    .append(noImagesWithoutTitle)
                    .append("\n\n");

            Map<String, Long> imagesByCategory = nasaImages.stream()
                    .collect(Collectors.groupingBy(
                            image -> image.getCategory().getName(),
                            Collectors.counting()
                    ));

            sb.append("10. Collectors.groupingBy() + Collectors.counting()\n");
            imagesByCategory.forEach((category, count)
                    -> sb.append(category)
                            .append(": ")
                            .append(count)
                            .append(" image(s)\n"));

            taResults.setText(sb.toString());
            taResults.setCaretPosition(0);

        } catch (Exception ex) {
            Logger.getLogger(StreamsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Streams error", "Unable to run stream demo.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        lbDescription = new javax.swing.JLabel();
        btnRunDemo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taResults = new javax.swing.JTextArea();

        setMinimumSize(new java.awt.Dimension(900, 550));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Streams / Statistics");

        lbDescription.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDescription.setText("This panel demonstrates advanced Java Stream operations on NASA image data.");

        btnRunDemo.setText("Refresh / Run stream demo");
        btnRunDemo.addActionListener(this::btnRunDemoActionPerformed);

        taResults.setEditable(false);
        taResults.setColumns(20);
        taResults.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        taResults.setLineWrap(true);
        taResults.setRows(5);
        taResults.setWrapStyleWord(true);
        jScrollPane1.setViewportView(taResults);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRunDemo))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbDescription)
                .addGap(18, 18, 18)
                .addComponent(btnRunDemo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunDemoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunDemoActionPerformed
        runStreamDemo();
    }//GEN-LAST:event_btnRunDemoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRunDemo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDescription;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTextArea taResults;
    // End of variables declaration//GEN-END:variables
}
