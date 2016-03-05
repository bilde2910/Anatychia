/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import java.io.File;

/**
 *
 * @author Marius
 */
public class SaveData {
    private final File location;
    private final String folderName;
    private final String levelName;
    
    public SaveData(File location, String folderName, String levelName) {
        this.location = location;
        this.folderName = folderName;
        this.levelName = levelName;
    }
    
    public File getLocation() {
        return this.location;
    }
    
    public String getFolderName() {
        return this.folderName;
    }
    
    public String getLevelName() {
        return this.levelName;
    }
}
