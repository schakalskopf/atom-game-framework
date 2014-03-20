/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.ui;

import java.util.List;
import sg.atom.managex.api.function.AtomFunction;

/**
 * Facade for functions.
 *
 * <p> High level of Container than Panel or Control, for AtomFunction.
 *
 * @author cuong.nguyenmanh2
 */
public interface AtomFunctionContainer {

    public List<AtomFunction> getFunctionList();
}
