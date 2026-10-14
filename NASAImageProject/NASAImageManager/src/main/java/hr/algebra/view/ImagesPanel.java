/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.factory.RepositoryFactory;
import hr.algebra.model.Category;
import hr.algebra.model.NasaImage;
import hr.algebra.model.Tag;
import hr.algebra.model.TagTransferable;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.IconUtils;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.view.model.NasaImageTableModel;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

/**
 *
 * @author vitoz
 */
public class ImagesPanel extends javax.swing.JPanel {

    private static final String DIR = "assets";

    private List<JTextComponent> validationFields;
    private List<JLabel> errorLabels;

    private Repository repository;
    private NasaImageTableModel nasaImageTableModel;

    private DefaultListModel<Tag> allTagsModel;
    private DefaultListModel<Tag> imageTagsModel;

    private NasaImage selectedNasaImage;

    public ImagesPanel() {
        initComponents();
        init();
    }

    private void init() {
        try {
            initValidation();
            hideErrors();
            initRepository();
            initCategories();
            initTable();
            initTagLists();
            initDragNDrop();
            clearForm();
        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Cannot initialize images panel.");
        }
    }

    private void initValidation() {
        validationFields = Arrays.asList(
                tfTitle,
                tfLink,
                tfPublishedDate,
                tfPicturePath,
                taDescription
        );

        errorLabels = Arrays.asList(
                lbTitleError,
                lbLinkError,
                lbPublishedDateError,
                lbPicturePathError,
                lbDescriptionError
        );
    }

    private void hideErrors() {
        errorLabels.forEach(e -> e.setVisible(false));
    }

    private void initRepository() throws Exception {
        repository = RepositoryFactory.getRepository();
    }

    private void initCategories() throws Exception {
        DefaultComboBoxModel<Category> model = new DefaultComboBoxModel<>();

        for (Category category : repository.selectCategories()) {
            model.addElement(category);
        }

        cbCategory.setModel(model);
    }

    private void initTable() throws Exception {
        tbNasaImages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbNasaImages.setAutoCreateRowSorter(true);
        tbNasaImages.setRowHeight(25);

        nasaImageTableModel = new NasaImageTableModel(repository.selectNasaImages());
        tbNasaImages.setModel(nasaImageTableModel);
    }

    private void loadTable() throws Exception {
        nasaImageTableModel.setNasaImages(repository.selectNasaImages());
    }

    private boolean formValid() {
        hideErrors();

        boolean ok = true;

        for (int i = 0; i < validationFields.size(); i++) {
            boolean empty = validationFields.get(i).getText().trim().isEmpty();

            if (empty) {
                ok = false;
                errorLabels.get(i).setVisible(true);
            }
        }

        try {
            LocalDateTime.parse(tfPublishedDate.getText().trim(), NasaImage.DATE_FORMATTER);
        } catch (Exception ex) {
            ok = false;
            lbPublishedDateError.setVisible(true);
        }

        return ok;
    }

    private String uploadPicture() throws IOException {
        String picturePath = tfPicturePath.getText().trim();

        if (picturePath.startsWith(DIR + File.separator)) {
            return picturePath;
        }

        String ext = picturePath.substring(picturePath.lastIndexOf("."));
        String pictureName = UUID.randomUUID() + ext;
        String localPicturePath = DIR + File.separator + pictureName;

        FileUtils.copy(picturePath, localPicturePath);

        return localPicturePath;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNasaImages = new javax.swing.JTable();
        lbImageTitle = new javax.swing.JLabel();
        lbLink = new javax.swing.JLabel();
        lbPublishedDate = new javax.swing.JLabel();
        lbCategory = new javax.swing.JLabel();
        tfTitle = new javax.swing.JTextField();
        tfLink = new javax.swing.JTextField();
        tfPublishedDate = new javax.swing.JTextField();
        cbCategory = new javax.swing.JComboBox<>();
        lbDescription = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDescription = new javax.swing.JTextArea();
        lbPicturePath = new javax.swing.JLabel();
        tfPicturePath = new javax.swing.JTextField();
        btnChooseImage = new javax.swing.JButton();
        lbIcon = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        lbTitleError = new javax.swing.JLabel();
        lbLinkError = new javax.swing.JLabel();
        lbPublishedDateError = new javax.swing.JLabel();
        lbPicturePathError = new javax.swing.JLabel();
        lbDescriptionError = new javax.swing.JLabel();
        lbAllTags = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lsImageTags = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        lsAllTags = new javax.swing.JList<>();
        lbImageTags = new javax.swing.JLabel();
        btnRemoveSelected = new javax.swing.JButton();
        btnRefreshTag = new javax.swing.JButton();
        btnRefreshCategories = new javax.swing.JButton();
        btnRefreshImages = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(950, 720));
        setPreferredSize(new java.awt.Dimension(950, 720));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("NASA Images");

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
        tbNasaImages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNasaImagesMouseClicked(evt);
            }
        });
        tbNasaImages.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbNasaImagesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbNasaImages);

        lbImageTitle.setText("Title:");

        lbLink.setText("Link:");

        lbPublishedDate.setText("Published date:");

        lbCategory.setText("Category:");

        lbDescription.setText("Description:");

        taDescription.setColumns(20);
        taDescription.setLineWrap(true);
        taDescription.setRows(5);
        jScrollPane2.setViewportView(taDescription);

        lbPicturePath.setText("Picture path:");

        tfPicturePath.setEditable(false);
        tfPicturePath.setName("Date"); // NOI18N

        btnChooseImage.setText("Choose image:");
        btnChooseImage.addActionListener(this::btnChooseImageActionPerformed);

        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbIcon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAdd.setText("Add");
        btnAdd.setPreferredSize(new java.awt.Dimension(100, 32));
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnDelete.setText("Delete");
        btnDelete.setPreferredSize(new java.awt.Dimension(100, 32));
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnClear.setText("Clear");
        btnClear.setPreferredSize(new java.awt.Dimension(100, 32));
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnUpdate.setText("Update");
        btnUpdate.setPreferredSize(new java.awt.Dimension(100, 32));
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        lbTitleError.setForeground(java.awt.Color.red);
        lbTitleError.setText("X");

        lbLinkError.setForeground(java.awt.Color.red);
        lbLinkError.setText("X");

        lbPublishedDateError.setForeground(java.awt.Color.red);
        lbPublishedDateError.setText("X");

        lbPicturePathError.setForeground(java.awt.Color.red);
        lbPicturePathError.setText("X");

        lbDescriptionError.setForeground(java.awt.Color.red);
        lbDescriptionError.setText("X");

        lbAllTags.setText("All tags:");

        jScrollPane3.setPreferredSize(new java.awt.Dimension(250, 100));

        lsImageTags.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lsImageTags.setDropMode(javax.swing.DropMode.ON);
        jScrollPane3.setViewportView(lsImageTags);

        jScrollPane4.setPreferredSize(new java.awt.Dimension(250, 100));

        lsAllTags.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lsAllTags.setDragEnabled(true);
        jScrollPane4.setViewportView(lsAllTags);

        lbImageTags.setText("Image tags for selected image:");

        btnRemoveSelected.setText("Remove selected tag");
        btnRemoveSelected.setPreferredSize(new java.awt.Dimension(143, 32));
        btnRemoveSelected.addActionListener(this::btnRemoveSelectedActionPerformed);

        btnRefreshTag.setText("Refresh tags");
        btnRefreshTag.setPreferredSize(new java.awt.Dimension(143, 32));
        btnRefreshTag.addActionListener(this::btnRefreshTagActionPerformed);

        btnRefreshCategories.setText("Refresh categories");
        btnRefreshCategories.addActionListener(this::btnRefreshCategoriesActionPerformed);

        btnRefreshImages.setText("Refresh images");
        btnRefreshImages.setPreferredSize(new java.awt.Dimension(114, 32));
        btnRefreshImages.addActionListener(this::btnRefreshImagesActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 23, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbAllTags))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbImageTags))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnRemoveSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(btnRefreshTag, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnRefreshImages, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(3, 3, 3))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbPicturePath)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tfPicturePath, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbPicturePathError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(btnChooseImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbDescriptionError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24)
                                                .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(lbLink, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbPublishedDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbImageTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(tfTitle)
                                                .addComponent(tfLink, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                                                .addComponent(tfPublishedDate, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(btnRefreshCategories)))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lbTitleError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lbLinkError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lbPublishedDateError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGap(7, 7, 7)))))
                    .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbImageTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTitleError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbLink)
                    .addComponent(tfLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbLinkError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPublishedDate)
                    .addComponent(tfPublishedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPublishedDateError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCategory)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefreshCategories))
                .addGap(18, 18, 18)
                .addComponent(lbDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbDescriptionError)
                    .addComponent(jScrollPane2)
                    .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPicturePath)
                    .addComponent(tfPicturePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseImage)
                    .addComponent(lbPicturePathError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefreshTag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoveSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefreshImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbAllTags)
                            .addComponent(lbImageTags))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbNasaImagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNasaImagesMouseClicked
        showNasaImage();
    }//GEN-LAST:event_tbNasaImagesMouseClicked

    private void tbNasaImagesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNasaImagesKeyReleased
        showNasaImage();
    }//GEN-LAST:event_tbNasaImagesKeyReleased

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
        tbNasaImages.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnChooseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseImageActionPerformed
        Optional<File> optFile = FileUtils.uploadFile("Images", "jpg", "jpeg", "png");

        if (optFile.isEmpty()) {
            return;
        }

        File file = optFile.get();

        tfPicturePath.setText(file.getAbsolutePath());
        setIcon(lbIcon, file);
    }//GEN-LAST:event_btnChooseImageActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!formValid()) {
            return;
        }

        try {
            String localPicturePath = uploadPicture();

            NasaImage nasaImage = new NasaImage(
                    tfTitle.getText().trim(),
                    tfLink.getText().trim(),
                    taDescription.getText().trim(),
                    localPicturePath,
                    LocalDateTime.parse(tfPublishedDate.getText().trim(), NasaImage.DATE_FORMATTER),
                    (Category) cbCategory.getSelectedItem()
            );

            repository.createNasaImage(nasaImage);
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Add image", "NASA image added successfully.");

        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Add error", "Unable to add NASA image.");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedNasaImage == null) {
            MessageUtils.showInformationMessage("Update image", "Please select a NASA image first.");
            return;
        }

        if (!formValid()) {
            return;
        }

        try {
            String picturePath = tfPicturePath.getText().trim();

            if (!picturePath.equals(selectedNasaImage.getPicturePath())) {
                if (selectedNasaImage.getPicturePath() != null) {
                    Files.deleteIfExists(Paths.get(selectedNasaImage.getPicturePath()));
                }

                picturePath = uploadPicture();
            }

            selectedNasaImage.setTitle(tfTitle.getText().trim());
            selectedNasaImage.setLink(tfLink.getText().trim());
            selectedNasaImage.setDescription(taDescription.getText().trim());
            selectedNasaImage.setPicturePath(picturePath);
            selectedNasaImage.setPublishedDate(LocalDateTime.parse(tfPublishedDate.getText().trim(), NasaImage.DATE_FORMATTER));
            selectedNasaImage.setCategory((Category) cbCategory.getSelectedItem());

            repository.updateNasaImage(selectedNasaImage.getId(), selectedNasaImage);
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Update image", "NASA image updated successfully.");

        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Update error", "Unable to update NASA image.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedNasaImage == null) {
            MessageUtils.showInformationMessage("Delete image", "Please select a NASA image first.");
            return;
        }

        if (!MessageUtils.showConfirmDialog(
                "Delete image",
                "Do you really want to delete this NASA image?")) {
            return;
        }

        try {
            if (selectedNasaImage.getPicturePath() != null) {
                Files.deleteIfExists(Paths.get(selectedNasaImage.getPicturePath()));
            }

            repository.deleteNasaImage(selectedNasaImage.getId());
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Delete image", "NASA image deleted successfully.");

        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Delete error", "Unable to delete NASA image.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRemoveSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveSelectedActionPerformed
        if (selectedNasaImage == null) {
            MessageUtils.showInformationMessage("Remove tag", "Please select a NASA image first.");
            return;
        }

        Tag selectedTag = lsImageTags.getSelectedValue();

        if (selectedTag == null) {
            MessageUtils.showInformationMessage("Remove tag", "Please select a tag to remove.");
            return;
        }

        try {
            repository.removeTagFromImage(selectedNasaImage.getId(), selectedTag.getId());
            loadImageTagsModel();

            MessageUtils.showInformationMessage("Remove tag", "Tag removed from image.");

        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Remove error", "Unable to remove tag.");
        }
    }//GEN-LAST:event_btnRemoveSelectedActionPerformed

    private void btnRefreshTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTagActionPerformed
        try {
            loadAllTagsModel();
            MessageUtils.showInformationMessage("Refresh tags", "Tags refreshed.");
        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Refresh error", "Unable to refresh tags.");
        }
    }//GEN-LAST:event_btnRefreshTagActionPerformed

    private void btnRefreshCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshCategoriesActionPerformed
        try {
            initCategories();
            MessageUtils.showInformationMessage("Refresh categories", "Categories refreshed.");
        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Refresh error", "Unable to refresh categories.");
        }
    }//GEN-LAST:event_btnRefreshCategoriesActionPerformed

    private void btnRefreshImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshImagesActionPerformed
        try {
            loadTable();
            clearForm();
            tbNasaImages.clearSelection();

            MessageUtils.showInformationMessage("Refresh images", "NASA images refreshed.");
        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Refresh error", "Unable to refresh NASA images.");
        }
    }//GEN-LAST:event_btnRefreshImagesActionPerformed

    private void showNasaImage() {
        clearForm();

        int selectedRow = tbNasaImages.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int rowIndex = tbNasaImages.convertRowIndexToModel(selectedRow);
        int selectedNasaImageId = (int) nasaImageTableModel.getValueAt(rowIndex, 0);

        try {
            Optional<NasaImage> optNasaImage = repository.selectNasaImage(selectedNasaImageId);

            if (optNasaImage.isPresent()) {
                selectedNasaImage = optNasaImage.get();
                fillForm(selectedNasaImage);
                loadImageTagsModel();
            }

        } catch (Exception ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to show selected NASA image.");
        }
    }

    private void fillForm(NasaImage nasaImage) {
        tfTitle.setText(nasaImage.getTitle());
        tfLink.setText(nasaImage.getLink());
        taDescription.setText(nasaImage.getDescription());
        tfPublishedDate.setText(nasaImage.getPublishedDate().format(NasaImage.DATE_FORMATTER));
        cbCategory.setSelectedItem(nasaImage.getCategory());

        if (nasaImage.getPicturePath() != null && Files.exists(Paths.get(nasaImage.getPicturePath()))) {
            tfPicturePath.setText(nasaImage.getPicturePath());
            setIcon(lbIcon, new File(nasaImage.getPicturePath()));
        }
    }

    private void setIcon(JLabel label, File file) {
        try {
            label.setIcon(IconUtils.createIcon(file, label.getWidth(), label.getHeight()));
        } catch (IOException ex) {
            Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to show image.");
        }
    }

    private void clearForm() {
        hideErrors();

        validationFields.forEach(e -> e.setText(""));

        if (cbCategory.getItemCount() > 0) {
            cbCategory.setSelectedIndex(0);
        }

        selectedNasaImage = null;

        if (imageTagsModel != null) {
            imageTagsModel.clear();
        }

        if (getClass().getResource("/assets/no_image.png") != null) {
            lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/no_image.png")));
        } else {
            lbIcon.setIcon(null);
        }
    }

    private void initTagLists() throws Exception {
        allTagsModel = new DefaultListModel<>();
        imageTagsModel = new DefaultListModel<>();

        loadAllTagsModel();

        lsAllTags.setModel(allTagsModel);
        lsImageTags.setModel(imageTagsModel);
    }

    private void loadAllTagsModel() throws Exception {
        allTagsModel.clear();

        for (Tag tag : repository.selectTags()) {
            allTagsModel.addElement(tag);
        }
    }

    private void loadImageTagsModel() throws Exception {
        imageTagsModel.clear();

        if (selectedNasaImage == null) {
            return;
        }

        for (Tag tag : repository.selectTagsForImage(selectedNasaImage.getId())) {
            imageTagsModel.addElement(tag);
        }
    }

    private void initDragNDrop() {
        lsAllTags.setDragEnabled(true);
        lsAllTags.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsAllTags.setTransferHandler(new ExportTagHandler());

        lsImageTags.setDropMode(DropMode.ON);
        lsImageTags.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsImageTags.setTransferHandler(new ImportTagHandler());
    }

    private class ExportTagHandler extends TransferHandler {

        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            Tag selectedTag = lsAllTags.getSelectedValue();

            if (selectedTag == null) {
                return null;
            }

            return new TagTransferable(selectedTag);
        }
    }

    private class ImportTagHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(TagTransferable.TAG_FLAVOR);
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (selectedNasaImage == null) {
                MessageUtils.showInformationMessage("Assign tag", "Please select a NASA image first.");
                return false;
            }

            Transferable transferable = support.getTransferable();

            try {
                Tag tag = (Tag) transferable.getTransferData(TagTransferable.TAG_FLAVOR);

                repository.assignTagToImage(selectedNasaImage.getId(), tag.getId());
                loadImageTagsModel();

                return true;

            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ImagesPanel.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Assign tag", "Unable to assign tag.");
            }

            return false;
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnChooseImage;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefreshCategories;
    private javax.swing.JButton btnRefreshImages;
    private javax.swing.JButton btnRefreshTag;
    private javax.swing.JButton btnRemoveSelected;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<Category> cbCategory;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbAllTags;
    private javax.swing.JLabel lbCategory;
    private javax.swing.JLabel lbDescription;
    private javax.swing.JLabel lbDescriptionError;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbImageTags;
    private javax.swing.JLabel lbImageTitle;
    private javax.swing.JLabel lbLink;
    private javax.swing.JLabel lbLinkError;
    private javax.swing.JLabel lbPicturePath;
    private javax.swing.JLabel lbPicturePathError;
    private javax.swing.JLabel lbPublishedDate;
    private javax.swing.JLabel lbPublishedDateError;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTitleError;
    private javax.swing.JList<Tag> lsAllTags;
    private javax.swing.JList<Tag> lsImageTags;
    private javax.swing.JTextArea taDescription;
    private javax.swing.JTable tbNasaImages;
    private javax.swing.JTextField tfLink;
    private javax.swing.JTextField tfPicturePath;
    private javax.swing.JTextField tfPublishedDate;
    private javax.swing.JTextField tfTitle;
    // End of variables declaration//GEN-END:variables
}
