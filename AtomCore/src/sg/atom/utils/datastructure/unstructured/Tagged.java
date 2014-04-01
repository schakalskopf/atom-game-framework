/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package sg.atom.utils.datastructure.unstructured;

import java.util.List;

public interface Tagged {

    List<Tag> getTags();

    String getComment();

    void setComment(String value);
}
