/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.project;

import java.util.Date;

/**
 * Project for Editor. This is the central of the Editing framework, beside of
 * the Editor.
 *
 * <p>The project is configed via CommonConfig.
 *
 * <p>Pre/post editing mechanism called ProjectBuildMechanism can be call upon
 * init/finish of the project.
 *
 * <p>This implementation use Ant/Graddle as default external build tools,
 * associated with Java and Groovy as you can imagine.
 *
 * <p>This implementation also depend in XML, it use XStream to serialize
 * non-structure XML of project properties, and use Sirix to save them in
 * versoning xml structure. This enable a scalable corporative architurecture,
 * in which serveral user can work upon the project at the same time.
 *
 * @author hungcuong
 */
public class Project {

    public String name;
    public String version;
    public String author;
    public String type;
    public Date startDate;
    public int status;
    public String projectPath;
    public String restAddress;
    
}
