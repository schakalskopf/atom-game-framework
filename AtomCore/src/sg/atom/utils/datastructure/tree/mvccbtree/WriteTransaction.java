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

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import sg.atom.utils.datastructure.tree.mvccbtree.exception.BadTransactionStateException;

/**
 * A data structure used to manage a write transaction
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public  class WriteTransaction
{
    /** The recordManager on which this transaction is applied */
    private RecordManager recordManager;

    /** A lock used to protect the write operation against concurrent access */
    protected ReentrantLock writeLock;

    public WriteTransaction( RecordManager recordManager )
    {
        System.out.println( "Creating the transaction oject" );
        this.recordManager = recordManager;
        writeLock = new ReentrantLock();
    }


    public  void start()
    {
        if ( writeLock.isLocked() )
        {
            throw new BadTransactionStateException( "Cannot start a write transaction when it's already started" );
        }

        writeLock.lock();
    }


    public  void commit()
    {
        if ( !writeLock.isLocked() )
        {
            throw new BadTransactionStateException( "Cannot commit a write transaction when it's not started" );
        }

        Map<?, ?> pendingPages = recordManager.getPendingPages();

        for ( Object object : pendingPages.keySet() )
        {
            BTree btree = (BTree)pendingPages.get( object );

            try
            {
                recordManager.writePage( btree, (Page)object, ((Page)object).getRevision() );
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /*
        recordManager.updateRecordManagerHeader();

        // Update the BTree header now
        recordManager.updateBtreeHeader( btree, ( ( AbstractPage<K, V> ) rootPage ).getOffset() );

        // Moved the free pages into the list of free pages
        recordManager.addFreePages( this, result.getCopiedPages() );

        // Store the created rootPage into the revision BTree, this will be stored in RecordManager only if revisions are set to keep
        recordManager.storeRootPage( this, rootPage );
        */

        pendingPages.clear();

        writeLock.unlock();
    }


    public  void rollback()
    {
        if ( !writeLock.isLocked() )
        {
            throw new BadTransactionStateException( "Cannot commit a write transaction when it's not started" );
        }

        writeLock.unlock();
    }


    /**
     * Tells if the transaction has started
     * @return true if the transaction has started
     */
    public  boolean isStarted()
    {
        return writeLock.isLocked();
    }
}
