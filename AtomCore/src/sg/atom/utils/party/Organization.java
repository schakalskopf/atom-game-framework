/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package sg.atom.utils.party;

import java.util.List;

public interface Organization extends Party {

    List<Party> getChildren();

    List<Party> getExternalChildren();

    String getOrganizationType();

    void setOrganizationType(String value);
    
    //List<MatrixRelationship> getMatrixedChildren();
}
