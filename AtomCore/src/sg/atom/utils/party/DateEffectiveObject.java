/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sg.atom.utils.party;

import java.util.Date;

public interface DateEffectiveObject {

	Date getStart();

	void setStart(Date value);

	Date getEnd();

	void setEnd(Date value);

	boolean isEffectiveNow();

	boolean isEffective(Date date);

} 
