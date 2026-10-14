/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Category;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author vitoz
 */
public class CategoryTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "Id",
        "Name"
    };

    private List<Category> categories;

    public CategoryTableModel(List<Category> categories) {
        this.categories = categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Category category = categories.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                category.getId();
            case 1 ->
                category.getName();
            default ->
                throw new RuntimeException("No such column");
        };
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
