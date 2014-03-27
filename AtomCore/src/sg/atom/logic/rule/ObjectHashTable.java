/*
 * sg.atom.logic.rule - The Java Embedded Object Production System
 * Copyright (c) 2000   Carlos Figueira Filho
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Contact: Carlos Figueira Filho (csff@cin.ufpe.br)
 */

package sg.atom.logic.rule;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class models a hash table used by the object base. It's used to
 * improve the search for the objects of a certain class, including its
 * subclasses.
 *
 * @author Carlos Figueira Filho (<a href="mailto:csff@di.ufpe.br">csff@di.ufpe.br</a>)
 * @version 0.02  21 Mar 2000   Support for interfaces. Implemented interfaces are
 *                               treated as superclass of a given class.
 * @history 0.01  12 Mar 2000   Class adapted from previous version of sg.atom.logic.rule.
 */
class ObjectHashTable {

	/**
	 * The superclasses/subclasses mapping. Its elements will be Vectors,
	 * as there can be more than one subclass per class. Actually, this word
	 * is a little misused, as we consider interfaces implemented by a class
	 * as its superclasses too.
	 */
	private Hashtable subClasses;

	/**
	 * The class/objects mapping. Its elements will be Vectors, as there can
	 * be more than one element per class.
	 */
	private Hashtable objects;

	/**
	 * Creates a new ObjectHashTable.
	 */
	public ObjectHashTable() {
		subClasses = new Hashtable();
		objects = new Hashtable();
	}

	/**
	 * Returns a vector with the objects of a given class.
	 *
	 * @param className the class whose objects are to be returned.
	 * @return all the objects of the given class (including those of
	 *          its subclasses).
	 */
	public Vector getObjects(String className) {
		Object obj = objects.get(className);
		Vector v = new Vector();
		Vector aux;
		if (obj != null) {
			aux = (Vector) obj;
			for (Enumeration e = aux.elements(); e.hasMoreElements(); ) {
				v.addElement(e.nextElement());
			}
		}
		obj = subClasses.get(className);
		if (obj != null) {
			aux = (Vector) obj;
			for (Enumeration e = aux.elements(); e.hasMoreElements(); ) {
				String subClassName = (String) e.nextElement();
				obj = objects.get(subClassName);
				if (obj != null) {
					Vector aux2 = (Vector) obj;
					for (Enumeration e2 = aux2.elements(); e2.hasMoreElements(); ) {
						v.addElement(e2.nextElement());
					}
				}
			}
		}
		return v;
	}

	/**
	 * Inserts a new element to this table.
	 *
	 * @param value the element being inserted.
	 */
	public void insert(Object value) {
		String className = value.getClass().getName();
		Object obj = objects.get(className);
		boolean hierarchyAlreadyPresent = false;
		Vector aux;
		if (obj == null) {
			aux = new Vector();
			objects.put(className, aux);
		} else {
			aux = (Vector) obj;
			hierarchyAlreadyPresent = true;
		}
		aux.addElement(value);
		if (!hierarchyAlreadyPresent) {
			Class classObject = null;
			try {
				classObject = Class.forName(className);
			} catch (Exception e) {}
			Class classAux = classObject;
			while (classAux != null) {
				classAux = classAux.getSuperclass();
				if (classAux != null) {
					insertInheritancePair(classAux.getName(), className);
				}
			}
			while (classObject != null) {
				Class[] interfaces = classObject.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					insertImplementedInterfaces(interfaces[i], className);
				}
				classObject = classObject.getSuperclass();
			}
		}
	}

	/**
	 * Inserts a pairs <interface, implementingClass> into the subClass
	 * map for the given class, as well as the corresponding pairs for the
	 * superinterfaces of the given one.
	 *
	 * @param interfaceClass the class object representing the interface.
	 * @param className the name of the implementing class.
	 */
	private void insertImplementedInterfaces(Class interfaceClass,
													String className) {
		insertInheritancePair(interfaceClass.getName(), className);
		Class[] superInterfaces = interfaceClass.getInterfaces();
		for (int i = 0; i < superInterfaces.length; i++) {
			insertImplementedInterfaces(superInterfaces[i], className);
		}
	}

	/**
	 * Inserts a new pair <superclass, subclass>.
	 *
	 * @param superclass the name of the superclass.
	 * @param subclass the name of the subclass.
	 */
	private void insertInheritancePair(String superclass, String subclass) {
		Object obj = subClasses.get(superclass);
		if (obj != null) {
			Vector v = (Vector) obj;
			if (!v.contains(subclass)) {
				v.addElement(subclass);
			}
		} else {
			Vector v = new Vector();
			v.addElement(subclass);
			subClasses.put(superclass, v);
		}
	}

	/**
	 * Clears this table.
	 */
	public void clear() {
		subClasses = new Hashtable();
		objects = new Hashtable();
	}

	/**
	 * Removes an element of this table. If the object didn't belong to this
	 * table, nothing happens.
	 *
	 * @param value the object to be removed.
	 * @return <code>true</code> if the argument was a component of this set;
	 *          <code>false</code> otherwise.
	 */
	public boolean remove(Object value) {
		boolean result = false;
		String className = value.getClass().getName();
		Object obj = objects.get(className);
		if (obj != null) {
			Vector v = (Vector) obj;
			result = v.removeElement(value);
		}
		return result;
	}

}
