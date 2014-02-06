/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.player;

import java.util.Date;

/**
 * Profile (Common implementation) is usually seen as a gamer's representor
 * (heavyweight) in the game community, with account name and associated data.
 *
 * @author atomix
 */
public class PlayerProfile {

    public String id;
    public int uid;
    public String realName;
    public int sex;
    public int country;
    public Date birthday;
}
