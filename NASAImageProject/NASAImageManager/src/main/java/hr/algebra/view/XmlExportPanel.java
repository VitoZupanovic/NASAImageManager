/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.factory.RepositoryFactory;
import hr.algebra.model.NasaImage;
import hr.algebra.model.NasaImageArchive;
import hr.algebra.utilities.JAXBUtils;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.view.model.NasaImageTableModel;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author vitoz
 */
public class XmlExportPanel extends javax.swing.JPanel {

    private Repository repository;
    private NasaImageTableModel nasaImageTableModel;
    private Optional<File> selectedFile = Optional.empty();

    public XmlExportPanel() {
        initComponents();
        init();
    }

    private void init() {
        try {
            initRepository();
            initTable();
            lbStatus.setText("Status: Ready");
        } catch (Exception ex) {
            Logger.getLogger(XmlExportPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Cannot initialize XML export panel.");
        }
    }

    private void initRepository() throws Exception {
        repository = RepositoryFactory.getRepository();
    }

    private void initTable() throws Exception {
        tbNasaImages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbNasaImages.setAutoCreateRowSorter(true);
        tbNasaImages.setRowHeight(25);

        nasaImageTableModel = new NasaImageTableModel(repository.selectNasaImages());
        tbNasaImages.setModel(nasaImageTableModel);
    }

    private Optional<File> chooseXmlFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("XML documents (*.xml)", "xml"));
        chooser.setDialogTitle("Choose XML export location");
        chooser.setApproveButtonText("Save");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            if (!file.toString().toLowerCase().endsWith(".xml")) {
                file = new File(file.toString() + ".xml");
            }

            return Optional.of(file);
        }

        return Optional.empty();
    }

    private Optional<NasaImage> getSelectedNasaImage() throws Exception {
        int selectedRow = tbNasaImages.getSelectedRow();

        if (selectedRow == -1) {
            return Optional.empty();
        }

        int rowIndex = tbNasaImages.convertRowIndexToModel(selectedRow);
        int selectedNasaImageId = (int) nasaImageTableModel.getValueAt(rowIndex, 0);

        return repository.selectNasaImage(selectedNasaImageId);
    }

    private void exportImages(List<NasaImage> nasaImages, File file) throws Exception {
        NasaImageArchive archive = new NasaImageArchive(nasaImages);
        JAXBUtils.save(archive, file.getAbsolutePath());
    }

    private void exportAllImages(File file) throws Exception {
        Predicate<NasaImage> validImage = image
                -> image.getTitle() != null
                && !image.getTitle().trim().isEmpty()
                && image.getPublishedDate() != null;

        Function<NasaImage, LocalDateTime> sortByPublishedDate
                = image -> image.getPublishedDate();

        Consumer<List<NasaImage>> updateStatus = images
                -> lbStatus.setText("Status: Exported " + images.size() + " image(s).");

        int limit = (int) spExportLimit.getValue();
        boolean oldestFirst = rbOldest.isSelected();

        List<NasaImage> nasaImages = repository.selectNasaImagesForExport(
                validImage,
                sortByPublishedDate,
                oldestFirst,
                limit
        );

        if (nasaImages.isEmpty()) {
            MessageUtils.showErrorMessage("Export error", "There are no NASA images to export.");
            return;
        }

        exportImages(nasaImages, file);

        updateStatus.accept(nasaImages);
        MessageUtils.showInformationMessage("XML export", "NASA images exported successfully.");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgExportMode = new javax.swing.ButtonGroup();
        lbTitle = new javax.swing.JLabel();
        lbDescription = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNasaImages = new javax.swing.JTable();
        lbFilePath = new javax.swing.JLabel();
        tfFilePath = new javax.swing.JTextField();
        btnChooseLocation = new javax.swing.JButton();
        btnExportSelected = new javax.swing.JButton();
        btnExportAll = new javax.swing.JButton();
        lbStatus = new javax.swing.JLabel();
        btnRefreshTable = new javax.swing.JButton();
        lbExportFirst = new javax.swing.JLabel();
        spExportLimit = new javax.swing.JSpinner();
        lbExportLimitHelp = new javax.swing.JLabel();
        lbExportOrder = new javax.swing.JLabel();
        rbNewest = new javax.swing.JRadioButton();
        rbOldest = new javax.swing.JRadioButton();

        setMinimumSize(new java.awt.Dimension(900, 450));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("XML Export");

        lbDescription.setText("Export NASA images to XML using JAXB");

        tbNasaImages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbNasaImages);

        lbFilePath.setText("Selected file:");

        tfFilePath.setEditable(false);

        btnChooseLocation.setText("Choose location");
        btnChooseLocation.addActionListener(this::btnChooseLocationActionPerformed);

        btnExportSelected.setText("Export selected");
        btnExportSelected.addActionListener(this::btnExportSelectedActionPerformed);

        btnExportAll.setText("Export all");
        btnExportAll.addActionListener(this::btnExportAllActionPerformed);

        lbStatus.setText("Status: Ready");

        btnRefreshTable.setText("Refresh table");
        btnRefreshTable.addActionListener(this::btnRefreshTableActionPerformed);

        lbExportFirst.setText("Export first:");

        spExportLimit.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        lbExportLimitHelp.setText("images (0 = all images)");

        lbExportOrder.setText("Export order:");

        bgExportMode.add(rbNewest);
        rbNewest.setSelected(true);
        rbNewest.setText("Newest first");

        bgExportMode.add(rbOldest);
        rbOldest.setText("Oldest first");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 841, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChooseLocation)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportAll, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbDescription)
                        .addGap(532, 532, 532)
                        .addComponent(btnRefreshTable))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbExportFirst, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbFilePath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spExportLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbExportLimitHelp))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbExportOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbNewest)
                        .addGap(18, 18, 18)
                        .addComponent(rbOldest)))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefreshTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbExportFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spExportLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbExportLimitHelp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbExportOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbNewest)
                    .addComponent(rbOldest))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExportAll)
                    .addComponent(btnExportSelected)
                    .addComponent(btnChooseLocation)
                    .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChooseLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseLocationActionPerformed
        Optional<File> chosenFile = chooseXmlFile();

        if (chosenFile.isPresent()) {
            selectedFile = chosenFile;
            tfFilePath.setText(selectedFile.get().getAbsolutePath());
            lbStatus.setText("Status: Export location selected.");
        } else {
            lbStatus.setText("Status: Export location was not changed.");
        }
    }//GEN-LAST:event_btnChooseLocationActionPerformed

    private void btnExportSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportSelectedActionPerformed
        if (selectedFile.isEmpty()) {
            MessageUtils.showInformationMessage("XML export", "Please choose export location first.");
            return;
        }

        try {
            Optional<NasaImage> selectedImage = getSelectedNasaImage();

            if (selectedImage.isEmpty()) {
                MessageUtils.showInformationMessage("XML export", "Please select a NASA image first.");
                return;
            }

            exportImages(Arrays.asList(selectedImage.get()), selectedFile.get());

            lbStatus.setText("Status: Selected NASA image exported.");
            MessageUtils.showInformationMessage("XML export", "Selected NASA image exported successfully.");

        } catch (Exception ex) {
            Logger.getLogger(XmlExportPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("XML export", "Unable to export selected NASA image.");
        }
    }//GEN-LAST:event_btnExportSelectedActionPerformed

    private void btnExportAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportAllActionPerformed
        selectedFile.ifPresentOrElse(
                file -> {
                    try {
                        exportAllImages(file);
                    } catch (Exception ex) {
                        Logger.getLogger(XmlExportPanel.class.getName()).log(Level.SEVERE, null, ex);
                        MessageUtils.showErrorMessage("XML export", "Unable to export NASA images.");
                    }
                },
                () -> MessageUtils.showInformationMessage(
                        "XML export",
                        "Please choose export location first."
                )
        );
    }//GEN-LAST:event_btnExportAllActionPerformed

    private void btnRefreshTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTableActionPerformed
        try {
            nasaImageTableModel.setNasaImages(repository.selectNasaImages());
            tbNasaImages.clearSelection();

            lbStatus.setText("Status: Table refreshed.");
            MessageUtils.showInformationMessage("Refresh table", "NASA images table refreshed.");
        } catch (Exception ex) {
            Logger.getLogger(XmlExportPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Refresh error", "Unable to refresh NASA images table.");
        }
    }//GEN-LAST:event_btnRefreshTableActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgExportMode;
    private javax.swing.JButton btnChooseLocation;
    private javax.swing.JButton btnExportAll;
    private javax.swing.JButton btnExportSelected;
    private javax.swing.JButton btnRefreshTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDescription;
    private javax.swing.JLabel lbExportFirst;
    private javax.swing.JLabel lbExportLimitHelp;
    private javax.swing.JLabel lbExportOrder;
    private javax.swing.JLabel lbFilePath;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JRadioButton rbNewest;
    private javax.swing.JRadioButton rbOldest;
    private javax.swing.JSpinner spExportLimit;
    private javax.swing.JTable tbNasaImages;
    private javax.swing.JTextField tfFilePath;
    // End of variables declaration//GEN-END:variables
}
