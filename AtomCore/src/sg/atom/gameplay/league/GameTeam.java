/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import com.sun.corba.se.pept.transport.ContactInfo;
import java.security.Identity;
import java.util.ArrayList;
import java.util.List;
import sg.atom.gameplay.player.Player;
import sg.atom.utils.datastructure.unstructured.Tag;
import sg.atom.utils.party.Organization;
import sg.atom.utils.party.Party;

/**
 * A named group of players, who play in a league or a match. In a competitive
 * gameplay, a Team is group of player that may have the same overal
 * configurations and policies, a Team is a Party and has Roles.
 *
 * @author atomix
 */
public class GameTeam<T extends Player, L extends GameLeague> implements Party{

    public GameLeague league;
    public Integer id;
    public String name;
    public ArrayList<T> members;

    public GameTeam(GameLeague league) {
        this.league = league;
    }

    public GameLeague getLeague() {
        return this.league;
    }

    @Override
    public List<ContactInfo> getContactInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Identity> getIdentity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Organization getParent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setParent(Organization value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setName(String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUid(String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Party> getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setExternalParent(Organization externalParent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Tag> getTags() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getComment() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setComment(String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
