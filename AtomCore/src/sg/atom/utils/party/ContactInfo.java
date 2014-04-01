/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package sg.atom.utils.party;

public interface ContactInfo extends DateEffectiveObject {

    String getCategory();

    void setCategory(String value);

    Party getOwner();

    void setOwner(Party value);
}
