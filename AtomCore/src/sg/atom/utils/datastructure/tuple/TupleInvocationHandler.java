 /*   
 * Copyright (C) 2013 Manish Jain <manishjain99@gmail.com> 
 * This file is part of DynamicJavaTuples.
 * 
 * DynamicJavaTuples is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DynamicJavaTuples is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with DynamicJavaTuples.  If not, see 
 * <http://www.gnu.org/licenses/>.
 */
package sg.atom.utils.datastructure.tuple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TupleInvocationHandler implements InvocationHandler {

    private static Method methodToAccessValueOfToupleElement = null;

    static {
        try {
            Class<?> args[] = {int.class};
            methodToAccessValueOfToupleElement = TupleImpl.class.getDeclaredMethod("getElement", args);
        } catch (Throwable e) {
            throw new RuntimeException("could not access method getElement(int i) from class: Seems like some one refactored the TupleImpl.java" + TupleImpl.class, e);
        }
    }
    private Tuple testImpl;

    public TupleInvocationHandler(Tuple impl) {
        this.testImpl = impl;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] params)
            throws Throwable {
        Object toRet = null;
        TupleNumber tupleAnnotation = method.getAnnotation(TupleNumber.class);
        if (tupleAnnotation == null) {
            toRet = method.invoke(testImpl, params);
        } else {
            Object args[] = {tupleAnnotation.id()};
            toRet = methodToAccessValueOfToupleElement.invoke(testImpl, args);
        }
        return toRet;
    }
}
