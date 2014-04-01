/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package sg.atom.utils.datastructure.unstructured;

import java.util.List;

/**
 * To tag object as "mark" it in unique way. Tag can be seen as comparible index, allow a collection or a data container "clustering" its elements. For example: 
 *
 * <p>In fact any object can be a Tag, the tag interface is just the way to get
 * a clean design with useful infos.
 *
 * @author cuong.nguyenmanh2
 */
public interface Tag {

    String getName();

    void setName(String value);

    List<String> getValue();

    String getComment();

    void setComment(String value);
}
