/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.varden.anatychia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Marius
 */
public class MaterialDataList {
    private final ArrayList<MaterialData> materials;
    private final HashMap<MaterialType, Long> typeCount = new HashMap<MaterialType, Long>();
    private int sectors = 0;
    
    public MaterialDataList() {
        this.materials = new ArrayList<MaterialData>();
    }
    
    public MaterialDataList(MaterialData[] materials) {
        this.materials = new ArrayList<MaterialData>(Arrays.asList(materials));
    }
    
    public MaterialData[] toArray() {
        return this.materials.toArray(new MaterialData[0]);
    }
    
    public ArrayList<MaterialData> toList() {
        return this.materials;
    }
    
    public MaterialData get(MaterialType type, String name) {
        for (MaterialData m : this.materials) {
            if (m.getName().equals(name) && m.getType().equals(type)) {
                return m;
            }
        }
        return new MaterialData(type, name, 0);
    }
    
    public void set(MaterialData material) {
        long base = 0;
        if (this.typeCount.containsKey(material.getType())) {
            base = this.typeCount.get(material.getType());
        }
        for (int i = 0; i < this.materials.size(); i++) {
            if (this.materials.get(i).getName().equals(material.getName()) && this.materials.get(i).getType().equals(material.getType())) {
                long prev = this.materials.get(i).getQuantity();
                this.materials.set(i, material);
                this.typeCount.put(material.getType(), base - prev + material.getQuantity());
                return;
            }
        }
        this.materials.add(material);
        this.typeCount.put(material.getType(), base + material.getQuantity());
    }
    
    public void increment(MaterialData material) {
        long base = 0;
        if (this.typeCount.containsKey(material.getType())) {
            base = this.typeCount.get(material.getType());
        }
        for (MaterialData m : this.materials) {
            if (m.getName().equals(material.getName()) && m.getType().equals(material.getType())) {
                m.setQuantity(m.getQuantity() + material.getQuantity());
                this.typeCount.put(material.getType(), base + material.getQuantity());
                return;
            }
        }
        this.materials.add(material);
        this.typeCount.put(material.getType(), base + material.getQuantity());
    }
    
    public int getSectors() {
        return this.sectors;
    }
    
    public void setSectorsAbsolute(int sectors) {
        this.sectors = sectors;
    }
    
    public void setSectorsRelative(int sectors) {
        this.sectors += sectors;
    }
    
    public long getTotalQuantity(MaterialType type) {
        return this.typeCount.get(type);
    }
}
