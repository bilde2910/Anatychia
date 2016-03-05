/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

/**
 *
 * @author Marius
 */
public enum MaterialType {
    BLOCK("Block", 1),
    ITEM("Item", 2),
    DIMENSION("Dimension", 4),
    ENTITY("Entity", 8);
    
    private final String name;
    private final int id;
    
    private MaterialType(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getID() {
        return this.id;
    }
    
    public static MaterialType getFromString(String name) {
        for (MaterialType type : MaterialType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
    
    public static MaterialType getFromID(int id) {
        for (MaterialType type : MaterialType.values()) {
            if (type.getID() == id) {
                return type;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
