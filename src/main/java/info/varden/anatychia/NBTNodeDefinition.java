/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import org.jnbt.Tag;

/**
 *
 * @author Marius
 */
public class NBTNodeDefinition {
    private final String nbtPath;
    private final Class<? extends Tag> type;
    
    public NBTNodeDefinition(String nbtPath, Class<? extends Tag> type) {
        this.nbtPath = nbtPath;
        this.type = type;
    }
    
    public String getNBTPath() {
        return this.nbtPath;
    }
    
    public Class<? extends Tag> getTagType() {
        return this.type;
    }
}
