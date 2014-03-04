/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.config;

import java.util.Map;

/**
 * Simple configs archive with String parameters as key.
 *
 * @author cuong.nguyenmanh2
 */
public interface IConfigurable {

    public void config(String key, Object value);

    public Object getConfig(String key);

    public <T> T getConfig(String key, Class<T> clazz);

    public Map<String, Object> getConfigs();
}
