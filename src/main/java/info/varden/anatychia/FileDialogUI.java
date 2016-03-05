/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Marius
 */
public class FileDialogUI {
    private static final JFileChooser jfc = new JFileChooser();
    //private static final FileFilter filterAllNbtJson = createFilter(new String[] {".nbt", ".json"}, "All supported formats (*.nbt; *.json)");
    private static final FileFilter filterAllNbt = createFilter(new String[] {".nbt"}, "All supported formats (*.nbt)");
    private static final FileFilter filterNbt = createFilter(new String[] {".nbt"}, "Named Binary Tag files (*.nbt)");
    private static final FileFilter filterJson = createFilter(new String[] {".json"}, "JavaScript Object Notation files (*.json)");
    private static final FileFilter filterXml = createFilter(new String[] {".xml"}, "Extensible Markup Language files (*.xml)");
    private static final FileFilter filterYaml = createFilter(new String[] {".yml", ".yaml"}, "Yet Another Markup Language files (*.yml; *.yaml)");
    
    private static FileFilter createFilter(final String[] extensions, final String description) {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                }
                for (String extension : extensions) {
                    if (pathname.getName().endsWith(extension)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }
    
    private static void removeAllFilters() {
        jfc.setAcceptAllFileFilterUsed(false);
        FileFilter[] ffs = jfc.getChoosableFileFilters();
        for (FileFilter ff : ffs) {
            jfc.removeChoosableFileFilter(ff);
        }
    }
    
    public static JFileChooser getFilterOpenChooser() {
        removeAllFilters();
        jfc.addChoosableFileFilter(filterAllNbt);
        jfc.addChoosableFileFilter(filterNbt);
        jfc.setFileFilter(filterAllNbt);
        return jfc;
    }
    
    public static JFileChooser getFilterSaveChooser() {
        removeAllFilters();
        jfc.addChoosableFileFilter(filterNbt);
        jfc.addChoosableFileFilter(filterJson);
        jfc.addChoosableFileFilter(filterXml);
        jfc.addChoosableFileFilter(filterYaml);
        jfc.setFileFilter(filterNbt);
        return jfc;
    }
}
