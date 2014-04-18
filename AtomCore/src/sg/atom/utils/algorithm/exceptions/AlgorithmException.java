/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.algorithm.exceptions;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class AlgorithmException extends RuntimeException {

    public AlgorithmException(String message) {
        super(message);
    }

    public AlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }
}
