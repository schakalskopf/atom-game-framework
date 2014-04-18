/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.player;

import java.util.Date;
import java.util.Iterator;
import org.apache.commons.configuration.AbstractConfiguration;

/**
 * Profile (Common implementation) is usually seen as a gamer's representor
 * (heavyweight) in the game community, with account name and associated data.
 *
 * @author atomix
 */
public class PlayerProfile extends AbstractConfiguration{

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

    @Override
    protected void addPropertyDirect(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getProperty(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<String> getKeys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
