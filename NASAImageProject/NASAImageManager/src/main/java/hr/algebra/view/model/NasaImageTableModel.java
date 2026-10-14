/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.NasaImage;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author vitoz
 */
public class NasaImageTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "Id",
        "Title",
        "Link",
        "Description",
        "Picture path",
        "Published date",
        "Category"
    };

    private List<NasaImage> nasaImages;

    public NasaImageTableModel(List<NasaImage> nasaImages) {
        this.nasaImages = nasaImages;
    }

    public void setNasaImages(List<NasaImage> nasaImages) {
        this.nasaImages = nasaImages;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return nasaImages.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NasaImage nasaImage = nasaImages.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                nasaImage.getId();
            case 1 ->
                nasaImage.getTitle();
            case 2 ->
                nasaImage.getLink();
            case 3 ->
                nasaImage.getDescription();
            case 4 ->
                nasaImage.getPicturePath();
            case 5 ->
                nasaImage.getPublishedDate().format(NasaImage.DATE_FORMATTER);
            case 6 ->
                nasaImage.getCategory();
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
