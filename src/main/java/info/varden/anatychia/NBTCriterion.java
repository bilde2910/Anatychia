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
public class NBTCriterion {
    private final NBTNodeDefinition nodePath;
    private final ValueMatchData matchData;
    
    public NBTCriterion(NBTNodeDefinition node, ValueMatchData matchData) {
        this.nodePath = node;
        this.matchData = matchData;
    }
    
    public NBTCriterion(NBTNodeDefinition node, String value, MatchMode matchMode) {
        this.nodePath = node;
        this.matchData = new ValueMatchData(value, matchMode);
    }
    
    public NBTCriterion(String nbtPath, Class<? extends Tag> tagType, ValueMatchData matchData) {
        this.nodePath = new NBTNodeDefinition(nbtPath, tagType);
        this.matchData = matchData;
    }
    
    public NBTCriterion(String nbtPath, Class<? extends Tag> tagType, String value, MatchMode matchMode) {
        this.nodePath = new NBTNodeDefinition(nbtPath, tagType);
        this.matchData = new ValueMatchData(value, matchMode);
    }
    
    public NBTNodeDefinition getNodeDefinition() {
        return this.nodePath;
    }
    
    public ValueMatchData getValueMatchData() {
        return this.matchData;
    }
    
    public String getNBTPath() {
        return this.nodePath.getNBTPath();
    }
    
    public Class<? extends Tag> getTagType() {
        return this.nodePath.getTagType();
    }
    
    public String getValue() {
        return this.matchData.getValue();
    }
    
    public MatchMode getMatchMode() {
        return this.matchData.getMatchMode();
    }
    
    @Override
    public String toString() {
        return "NBTCriteria{nbtPath=" + this.getNBTPath() + ", tagType=" + this.getTagType().toString() + ", value=" + this.getValue() + ", matchMode=" + this.getMatchMode().toString() + "}";
    }
}
