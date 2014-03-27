/**
 * Copyright (c) 2008-2010 Ardor Labs, Inc.
 *
 * This file is part of Ardor3D.
 *
 * Ardor3D is free software: you can redistribute it and/or modify it under the
 * terms of its license which may be found in the accompanying LICENSE file or
 * at <http://www.ardor3d.com/LICENSE>.
 */
package sg.atom.utils.repository.pool;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Simple Object pool for use with our Math Library to help reduce object
 * creation during calculations. This class uses a ThreadLocal pool of objects
 * to allow for fast multi-threaded use.
 *
 * @param <T> the type.
 */
public abstract class ObjectPool<T> {

    private final ThreadLocal<Deque<T>> _pool = new ThreadLocal<Deque<T>>() {
        @Override
        protected Deque<T> initialValue() {
            return new LinkedList<T>();
        }
    };
    private final int _maxSize;

    protected ObjectPool(final int maxSize) {
        _maxSize = maxSize;
    }

    protected abstract T newInstance();

    private final boolean contains(Object o) {
        final Deque<T> objects = _pool.get();
        for (T t : objects) {
            if (t == o) {
                return true;
            }
        }
        return false;
    }

    public final int size() {
        final Deque<T> objects = _pool.get();
        return objects.size();
    }

    public final T fetch() {
        final Deque<T> objects = _pool.get();
        return objects.isEmpty() ? newInstance() : objects.removeFirst();
    }

    public final void release(final T object) {
        if (object == null) {
            throw new RuntimeException("Should not release null objects into ObjectPool.");
        }

        final Deque<T> objects = _pool.get();
        assert !contains(object);
        if (objects.size() < _maxSize) {
            objects.add(object);
        }
    }

    public static <T> ObjectPool<T> create(final Class<T> clazz, final int maxSize) {
        return new ObjectPool<T>(maxSize) {
            @Override
            protected T newInstance() {
                try {
                    return clazz.newInstance();
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
