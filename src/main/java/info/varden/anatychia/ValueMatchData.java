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
public class ValueMatchData {
    private final String value;
    private final MatchMode matchMode;
    
    public ValueMatchData(String value, MatchMode matchMode) {
        this.value = value;
        this.matchMode = matchMode;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public MatchMode getMatchMode() {
        return this.matchMode;
    }
}
