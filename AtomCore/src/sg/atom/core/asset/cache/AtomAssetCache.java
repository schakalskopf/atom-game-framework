/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.asset.cache;

import com.google.common.cache.CacheBuilder;
import com.jme3.asset.AssetKey;
import com.jme3.asset.cache.AssetCache;

/**
 * Evict with remove policy Cache based in GuavaCache for chained/ linked
 * elements.
 *
 * <p>JME3 Cache is based in oncurrentHashMap<AssetKey, Object> or
 * ConcurrentHashMap<AssetKey, AssetRef>. It not very well suited with the flows
 * / channels / tasks architecture of Atom. That's why a fork! This Cache
 * implements JME AssetCache so it can also be used in normal AssetManager. It
 * also a LoadingCache and can be use as Normal GuavaCache.
 *
 * <p>http://code.google.com/p/guava-libraries/wiki/CachesExplained . GuavaCache
 * provide some feature like Size/Timed/Reference Evict, with Removal Listener.
 *
 * <p>AtomAssetCache can wrap around any Asset to make it semi-persistent.
 *
 * <p>Take a look at AtomCacheUtils also provide some functions just related to
 * normal Java object instead of Asset.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomAssetCache implements AssetCache {

    public CacheBuilder getCacheBuilder() {
        return CacheBuilder.newBuilder();
    }

    @Override
    public <T> void addToCache(AssetKey<T> key, T obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> void registerAssetClone(AssetKey<T> key, T clone) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyNoAssetClone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T getFromCache(AssetKey<T> key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteFromCache(AssetKey key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clearCache() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
