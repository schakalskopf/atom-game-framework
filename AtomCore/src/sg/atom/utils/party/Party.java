/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package sg.atom.utils.party;

import com.sun.corba.se.pept.transport.ContactInfo;
import java.security.Identity;
import java.util.List;
import sg.atom.utils.datastructure.unstructured.Tagged;

public interface Party extends Tagged {

    List<ContactInfo> getContactInfo();

    List<Identity> getIdentity();

    Organization getParent();

    void setParent(Organization value);

    String getName();

    void setName(String value);

    String getUid();

    void setUid(String value);

    List<Party> getPath();

    void setExternalParent(Organization externalParent);
}
