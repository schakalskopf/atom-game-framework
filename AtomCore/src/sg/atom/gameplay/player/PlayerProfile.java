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

    private String id;
    private int uid;
    private String realName;
    private int sex;
    private int country;
    private Date birthday;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public boolean validateProfile(){
        return true;
    }
}
