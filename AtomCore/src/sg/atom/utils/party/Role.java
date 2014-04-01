/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package sg.atom.utils.party;

import java.util.List;

/**
 * Role or membership determinate right and relation of a person or
 * PartyCommonObject in the organization.
 *
 * @author cuong.nguyenmanh2
 */
public interface Role extends DateEffectiveObject {

    List<Party> getParty();

    String getName();

    void setName(String value);

    PartyCommonObject getOwner();

    void setOwner(PartyCommonObject value);
}
