package com.token;

/**
 * 缓存接口
 * @author jgao
 *
 */
public interface ICache {

    public static int Forever = -1; //缓存中对象生命周期的结束标志

    /**
     * 判断缓存中的对象是否存在
     * @param key
     * @return
     */
    boolean contains(String key);

    /**
     * 获取缓存中的对象
     * @param key 对象名称
     * @return
     */
    Object get(String key);

    /**
     * 向缓存中插入对象
     * @param key 对象名称
     * @param obj 对象
     * @param slidingExpiration 对象在缓存中存在的时间
     */
    void Insert(String key, Object obj, int slidingExpiration);

    /**
     *
     * 向缓存中添加对象,并返回该对象
     * @param key 对象名称
     * @param obj 对象
     * @param slidingExpiration 对象在缓存中存在的时间
     * @return
     */
    Object Add(String key, Object obj, int slidingExpiration);

    /**
     * 移除缓存中的对象
     * @param key 对象名称
     * @return
     */
    Object Remove(String key);
}