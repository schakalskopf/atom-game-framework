/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui;

import com.google.inject.AbstractModule;
import org.junit.Test;
import sg.atom.core.AtomMain;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TestGUIInjection {

    public class GUIInjectModule extends AbstractModule {

        @Override
        protected void configure() {

            bind(AtomMain.class).to(AtomMain.class);
            //bind(CreditCardProcessor.class).to(PaypalCreditCardProcessor.class);
        }
    }

    @Test
    public void testInjection() {
    }
}
