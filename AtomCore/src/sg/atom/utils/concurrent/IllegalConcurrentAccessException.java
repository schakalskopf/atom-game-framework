/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.concurrent;

import java.io.IOException;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class IllegalConcurrentAccessException extends RuntimeException {

    public IllegalConcurrentAccessException(String message) {
        super(message);
    }

    public IllegalConcurrentAccessException(String message, Exception e) {
        super(message, e);
    }
}
