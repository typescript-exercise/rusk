package rusk.persistence.framework;

import net.sf.persist.Persist;

/**
 * {@link Persist} のインスタンスを提供するプロバイダー。
 */
public interface PersistProvider {
    
    /**
     * {@link Persist} インスタンスを取得する。
     * 
     * @return {@link Persist} インスタンス
     */
    public Persist getPersist();
}
