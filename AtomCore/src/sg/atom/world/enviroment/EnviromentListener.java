/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.enviroment;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface EnviromentListener {

    public void preEnviroment(AtomEnviroment enviroment);

    public void postEnviroment(AtomEnviroment enviroment);

    public void changeEnviroment(AtomEnviroment enviroment);
}
