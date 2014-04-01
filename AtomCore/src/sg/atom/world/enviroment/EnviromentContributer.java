/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.enviroment;

/**
 * Pre or post processing of enviroments.
 *
 * @author cuong.nguyenmanh2
 */
public interface EnviromentContributer {

    public void contributeEnviroment(AtomEnviroment enviroment);
}
