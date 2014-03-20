/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.bean;

import java.util.Properties;

@Deprecated
/**
 * Replaced with BeanUtils.
 * 
 * <p>Taken from Chain's ContextBase.
 * 
 * @author cuong.nguyenmanh2
 */
public interface EditableBean {

    public Properties getProperties();

    public void setProperty(String propName, Object value);

    public Object getProperty(String value);

    public <T> T getProperty(Class<T> clazz, String value);
}
