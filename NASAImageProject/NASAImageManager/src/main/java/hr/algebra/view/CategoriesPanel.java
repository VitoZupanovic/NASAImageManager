/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.factory.RepositoryFactory;
import hr.algebra.model.Category;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.view.model.CategoryTableModel;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;

/**
 *
 * @author vitoz
 */
public class CategoriesPanel extends javax.swing.JPanel {

    private Repository repository;
    private CategoryTableModel categoryTableModel;
    private Category selectedCategory;

    public CategoriesPanel() {
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
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Cannot initialize categories panel.");
        }
    }

    private void hideErrors() {
        lbNameError.setVisible(false);
    }

    private void initRepository() throws Exception {
        repository = RepositoryFactory.getRepository();
    }

    private void initTable() throws Exception {
        tbCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbCategories.setAutoCreateRowSorter(true);
        tbCategories.setRowHeight(25);

        categoryTableModel = new CategoryTableModel(repository.selectCategories());
        tbCategories.setModel(categoryTableModel);
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
        categoryTableModel.setCategories(repository.selectCategories());
    }

    private void clearForm() {
        hideErrors();
        tfName.setText("");
        selectedCategory = null;
    }

    private void showCategory() {
        clearForm();

        int selectedRow = tbCategories.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int rowIndex = tbCategories.convertRowIndexToModel(selectedRow);
        int selectedCategoryId = (int) categoryTableModel.getValueAt(rowIndex, 0);

        try {
            Optional<Category> optCategory = repository.selectCategory(selectedCategoryId);

            if (optCategory.isPresent()) {
                selectedCategory = optCategory.get();
                fillForm(selectedCategory);
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to show selected category.");
        }
    }

    private void fillForm(Category category) {
        tfName.setText(category.getName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCategories = new javax.swing.JTable();
        lbName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lbNameError = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnAdd3 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(880, 420));
        setPreferredSize(new java.awt.Dimension(890, 450));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Categories");

        tbCategories.setModel(new javax.swing.table.DefaultTableModel(
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
        tbCategories.setPreferredSize(new java.awt.Dimension(800, 250));
        tbCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCategoriesMouseClicked(evt);
            }
        });
        tbCategories.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCategoriesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbCategories);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbCategoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCategoriesMouseClicked
        showCategory();
    }//GEN-LAST:event_tbCategoriesMouseClicked

    private void tbCategoriesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbCategoriesKeyReleased
        showCategory();
    }//GEN-LAST:event_tbCategoriesKeyReleased

    private void btnAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd3ActionPerformed
        if (!formValid()) {
            return;
        }

        try {
            Category category = new Category(tfName.getText().trim());

            repository.createCategory(category);
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Add category", "Category added successfully.");

        } catch (Exception ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Add error", "Unable to add category. It may already exist.");
        }
    }//GEN-LAST:event_btnAdd3ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedCategory == null) {
            MessageUtils.showInformationMessage("Update category", "Please select a category first.");
            return;
        }

        if (!formValid()) {
            return;
        }

        try {
            selectedCategory.setName(tfName.getText().trim());

            repository.updateCategory(selectedCategory.getId(), selectedCategory);
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Update category", "Category updated successfully.");

        } catch (Exception ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Update error", "Unable to update category.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedCategory == null) {
            MessageUtils.showInformationMessage("Delete category", "Please select a category first.");
            return;
        }

        if ("NASA Image of the Day".equals(selectedCategory.getName())) {
            MessageUtils.showInformationMessage("Delete category", "Default category cannot be deleted.");
            return;
        }

        if (!MessageUtils.showConfirmDialog(
                "Delete category",
                "Do you really want to delete this category?")) {
            return;
        }

        try {
            repository.deleteCategory(selectedCategory.getId());
            loadTable();
            clearForm();

            MessageUtils.showInformationMessage("Delete category", "Category deleted successfully.");

        } catch (Exception ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Delete error", "Unable to delete category. It may be used by a NASA image.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
        tbCategories.clearSelection();
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
    private javax.swing.JTable tbCategories;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
