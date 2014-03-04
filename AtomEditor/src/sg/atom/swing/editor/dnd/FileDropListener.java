/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.swing.editor.dnd;

import com.google.common.base.Predicate;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 * Simple FileDropListener listen to file drag of the user.
 * @author cuong.nguyenmanh2
 */
public abstract class FileDropListener implements DropTargetListener {
    
    public Predicate<File> fileAllowCondition;
    public String fileExtensionAllow = "*.*";
    public String fileMultiExtensionsAllow;
    
    @Override
    public void drop(DropTargetDropEvent event) {

        // Accept copy drops
        event.acceptDrop(DnDConstants.ACTION_COPY);

        // Get the transfer which can provide the dropped item data
        Transferable transferable = event.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {
            
            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    List files = (List) transferable.getTransferData(flavor);
                    
                    boolean oneAccepted = false;
                    // Loop them through
                    for (Object obj : files) {
                        File file = (File) obj;
                        // Print out the file path
                        if (acceptFile(file)) {
                            eachAcceptedFile(file);
                            oneAccepted = true;
                        } else {
                            eachDeniedFile(file);
                        }
                    }
                    
                }
                
            } catch (Exception e) {
                failed(e);
            }
        }

        // Inform that the drop is complete
        event.dropComplete(true);
        
    }
    
    @Override
    public void dragEnter(DropTargetDragEvent event) {
        // Blink the target component;
    }
    
    @Override
    public void dragExit(DropTargetEvent event) {
        // Blink the target component;
    }
    
    @Override
    public void dragOver(DropTargetDragEvent event) {
    }
    
    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
        
    }
    
    public abstract void eachAcceptedFile(File file);
    
    public abstract void eachDeniedFile(File file) throws IOException;
    
    public boolean acceptFile(File file) {
        if (fileAllowCondition != null) {
            return fileAllowCondition.apply(file);
        } else if (fileMultiExtensionsAllow != null && !fileMultiExtensionsAllow.isEmpty()) {
            String[] split = fileMultiExtensionsAllow.split("|");
            for (String wc : split) {
                FileFilter fileFilter = new WildcardFileFilter(wc);
                if (fileFilter.accept(file)) {
                    return true;
                }
            }
            
        } else if (fileExtensionAllow != null && !fileExtensionAllow.isEmpty()) {
            FileFilter fileFilter = new WildcardFileFilter(fileExtensionAllow);
            return fileFilter.accept(file);
        }
        return true;
    }
    
    public void failed(Exception e) {
        e.printStackTrace();
    }
}
