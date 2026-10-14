/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.factory.RepositoryFactory;
import hr.algebra.model.Tag;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.view.model.TagTableModel;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;

/**
 *
 * @author vitoz
 */
public class TagsPanel extends javax.swing.JPanel {

    private Repository repository;
    private TagTableModel tagTableModel;
    private Tag selectedTag;

    public TagsPanel() {
        initComponents();
        init();
    }

    private void init() {
        try {
            hideErrors();
            initRepository();
            initTable();
            clearForm();
        } catch (Exception ex) {
            Logger.getLogger(TagsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Cannot initialize tags panel.");
        }
    }

    private void hideErrors() {
        lbNameError.setVisible(false);
    }

    private void initRepository() throws Exception {
        repository = RepositoryFactory.getRepository();
    }

    private void initTable() throws Exception {
        tbTags.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbTags.setAutoCreateRowSorter(true);
        tbTags.setRowHeight(25);

        tagTableModel = new TagTableModel(repository.selectTags());
        tbTags.setModel(tagTableModel);
    }

    private boolean formValid() {
        hideErrors();

        boolean ok = true;

        if (tfName.getText().trim().isEmpty()) {
            ok = false;
            lbNameError.setVisible(true);
        }

        return ok;
    }

    private void loadTable() throws Exception {
        tagTableModel.setTags(repository.selectTags());
    }

    private void clearForm() {
        hideErrors();
        tfName.setText("");
        selectedTag = null;
    }

    private void showTag() {
        clearForm();

        int selectedRow = tbTags.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int rowIndex = tbTags.convertRowIndexToModel(selectedRow);
        int selectedTagId = (int) tagTableModel.getValueAt(rowIndex, 0);

        try {
            Optional<Tag> optTag = repository.selectTag(selectedTagId);

            if (optTag.isPresent()) {
                selectedTag = optTag.get();
                fillForm(selectedTag);
            }

        } catch (Exception ex) {
            Logger.getLogger(TagsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to show selected tag.");
        }
    }

    private void fillForm(Tag tag) {
        tfName.setText(tag.getName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTags = new javax.swing.JTable();
        lbName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lbNameError = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnAdd3 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(900, 380));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Tags");

        tbTags.setModel(new javax.swing.table.DefaultTableModel(
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
        tbTags.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTagsMouseClicked(evt);
            }
        });
        tbTags.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbTagsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbTags);

        lbName.setText("Name:");

        lbNameError.setForeground(java.awt.Color.red);
        lbNameError.setText("X");

        btnUpdate.setText("Update");
        btnUpdate.setPreferredSize(new java.awt.Dimension(100, 32));
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setText("Delete");
        btnDelete.setPreferredSize(new java.awt.Dimension(100, 32));
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnClear.setText("Clear");
        btnClear.setPreferredSize(new java.awt.Dimension(100, 32));
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnAdd3.setText("Add");
        btnAdd3.setPreferredSize(new java.awt.Dimension(100, 32));
        btnAdd3.addActionListener(this::btnAdd3ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lbTitle)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(312, 312, 312)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbTagsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTagsKeyReleased
        showTag();
    }//GEN-LAST:event_tbTagsKeyReleased

    private void tbTagsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTagsMouseClicked
        showTag();
    }//GEN-LAST:event_tbTagsMouseClicked

    private void btnAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd3ActionPerformed
        if (!formValid()) {
            return;
        }

        try {
            Tag tag = new Tag(tfName.getText().trim());

            repository.createTag(tag);
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Add tag", "Tag added successfully.");

        } catch (Exception ex) {
            Logger.getLogger(TagsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Add error", "Unable to add tag. It may already exist.");
        }
    }//GEN-LAST:event_btnAdd3ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedTag == null) {
            MessageUtils.showInformationMessage("Update tag", "Please select a tag first.");
            return;
        }

        if (!formValid()) {
            return;
        }

        try {
            selectedTag.setName(tfName.getText().trim());

            repository.updateTag(selectedTag.getId(), selectedTag);
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Update tag", "Tag updated successfully.");

        } catch (Exception ex) {
            Logger.getLogger(TagsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Update error", "Unable to update tag.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedTag == null) {
            MessageUtils.showInformationMessage("Delete tag", "Please select a tag first.");
            return;
        }

        if (!MessageUtils.showConfirmDialog(
                "Delete tag",
                "Do you really want to delete this tag?")) {
            return;
        }

        try {
            repository.deleteTag(selectedTag.getId());
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Delete tag", "Tag deleted successfully.");

        } catch (Exception ex) {
            Logger.getLogger(TagsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Delete error", "Unable to delete tag.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
        tbTags.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd3;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNameError;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable tbTags;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
