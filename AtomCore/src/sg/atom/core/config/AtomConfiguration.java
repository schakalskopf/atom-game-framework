/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.config;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;

/**
 * Concept borrowed from Apache Configuration, Avalon and Spring.
 *
 * FIXME: Replace with Apache Configuration!
 *
 * @author cuong.nguyenmanh2
 */
@Deprecated
public interface AtomConfiguration {

    public AbstractConfiguration getConfiguration() throws ConfigurationException;

    public void apply() throws ConfigurationException, IllegalArgumentException;
}
