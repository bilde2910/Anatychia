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
public enum MatchMode {
    EQUALS(1),
    NOT_EQUALS(2),
    SMALLER_THAN(4),
    EQUALS_OR_GREATER(9),
    GREATER_THAN(8),
    EQUALS_OR_SMALLER(5);
    
    private int compareMode = -1;
    
    private MatchMode(int compareMode) {
        this.compareMode = compareMode;
    }
    
    public static MatchMode getModeFromString(String mode) {
        if (mode.equals("Value equals")) {
            return MatchMode.EQUALS;
        } else if (mode.equals("Value is not")) {
            return MatchMode.NOT_EQUALS;
        } else if (mode.equals("Value is smaller than")) {
            return MatchMode.SMALLER_THAN;
        } else if (mode.equals("Value is greater than")) {
            return MatchMode.GREATER_THAN;
        } else if (mode.equals("Value equals or is smaller than")) {
            return MatchMode.EQUALS_OR_SMALLER;
        } else if (mode.equals("Value equals or is greater than")) {
            return MatchMode.EQUALS_OR_GREATER;
        } else {
            return null;
        }
    }
    
    public static MatchMode getModeFromIntMode(int mode) {
        for (MatchMode m : MatchMode.values()) {
            if (m.getCompareMode() == mode) {
                return m;
            }
        }
        return null;
    }
    
    public int getCompareMode() {
        return this.compareMode;
    }
    
    public String getHumanName() {
        String t = toString().replace("_", " ").toLowerCase();
        t = t.substring(0, 1).toUpperCase() + t.substring(1);
        return t;
    }
}
