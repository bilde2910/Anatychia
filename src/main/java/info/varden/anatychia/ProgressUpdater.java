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
public abstract class ProgressUpdater {
    public abstract void updated(int value, int max);
    public abstract void setIndeterminate(boolean indeterminate);
}
