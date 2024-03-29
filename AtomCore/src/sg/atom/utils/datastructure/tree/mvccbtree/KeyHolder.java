/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package sg.atom.utils.datastructure.tree.mvccbtree;

/**
 * The data structure holding a key and the way to access it
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory
 * Project</a>
 *
 * <K> The key type
 */
public class KeyHolder<K> {

    /**
     * The deserialized key
     */
    protected K key;

    /**
     * Create a new KeyHolder instance
     *
     * @param key The key to store
     */
    public KeyHolder(K key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    public K getKey() {
        return key;
    }
}
