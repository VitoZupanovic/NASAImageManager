/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author vitoz
 */
public final class TagTransferable implements Transferable {

    private final Tag tag;

    public static final DataFlavor TAG_FLAVOR = new DataFlavor(Tag.class, "Tag");

    private final DataFlavor[] supportedFlavors = {TAG_FLAVOR};

    public TagTransferable(Tag tag) {
        this.tag = tag;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (DataFlavor dataFlavor : supportedFlavors) {
            if (dataFlavor.equals(flavor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            return tag;
        }
        throw new UnsupportedFlavorException(flavor);
    }
}
