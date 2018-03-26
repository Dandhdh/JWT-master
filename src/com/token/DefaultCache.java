package com.token;

import java.util.*;

/**
 * 系统默认的缓存
 * @author jgao
 *
 */
class DefaultCache implements ICache {

    static int FreshTimerIntervalSeconds = 1; //缓存中对象生命周期的频率(一秒)
    Map<String, SimpleCacheInfo> datas; //缓存容器
    private Timer timer; //时间任务

    /**
     * 默认构造函数
     *
     */
    public DefaultCache() {
        //实例化有防止线程同步操作的缓存容器
        datas = Collections.synchronizedMap(new HashMap<String, SimpleCacheInfo>());

        //刷新缓存
        TimerTask task = new CacheFreshTask(this);
        timer = new Timer("SimpleCache_Timer", true);
        timer.scheduleAtFixedRate(task, 1000, FreshTimerIntervalSeconds * 1000);//每格一秒刷新一次(缓存中对象的生命周期减一)
    }

    /**
     * 判断缓存中的对象是否存在
     * @param key
     * @return
     */
    public boolean contains(String key){
        return datas.containsKey(key);
    }

    /**
     * 获取缓存中的对象
     * @param key 对象名称
     * @return
     */
    public Object get(String key) {
        if (datas.containsKey(key)) {
            SimpleCacheInfo sci = (SimpleCacheInfo)datas.get(key);
            //sci.setSecondsRemain(sci.getSecondsTotal());
            return sci.getObj();
        }
        return null;
    }

    /**
     * 向缓存中插入对象
     * @param key 对象名称
     * @param obj 对象
     * @param cacheSeconds 对象在缓存中存在的时间
     */
    public void Insert(String key, Object obj, int cacheSeconds) {
        Add(key, obj, cacheSeconds);
    }

    /**
     *
     * 向缓存中添加对象,并返回该对象
     * @param key 对象名称
     * @param obj 对象
     * @param cacheSeconds 对象在缓存中存在的时间
     * @return
     */
    public Object Add(String key, Object obj, int cacheSeconds) {
        if (cacheSeconds != 0) {
            SimpleCacheInfo sci = new SimpleCacheInfo(obj, cacheSeconds);
            datas.put(key, sci);
        }
        return obj;
    }

    /**
     * 移除缓存中的对象
     * @param key 对象名称
     * @return
     */
    public Object Remove(String key) {
        SimpleCacheInfo sci = datas.remove(key);
        if (sci != null) {
            return sci.getObj();
        }
        return null;
    }

    /**
     * 缓存信息类(存储缓存中的对象和缓存时间)
     * @author jgao
     *
     */
    class SimpleCacheInfo {
        private Object obj;
        private int secondsRemain;
        private int cacheSeconds;

        public SimpleCacheInfo(Object obj, int cacheSeconds) {
            this.obj = obj;
            this.secondsRemain = cacheSeconds;
            this.cacheSeconds = cacheSeconds;
        }

        public Object getObj() {
            return obj;
        }

        int getSecondsTotal() {
            return cacheSeconds;
        }

        int getSecondsRemain() {
            return secondsRemain;
        }

        void setSecondsRemain(int value) {
            secondsRemain = value;
        }
    }

    /**
     * 管理缓存中对象的生命周期的任务类(用于定时刷新缓存中的对象)
     * @author jgao
     *
     */
    class CacheFreshTask extends TimerTask {
        private DefaultCache cache;
        public CacheFreshTask(DefaultCache cache) {
            this.cache = cache;
        }

        public void run() {
            synchronized (cache.datas) {
                Iterator<Map.Entry<String, SimpleCacheInfo>> iterator
                        = cache.datas.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, SimpleCacheInfo> entry = iterator.next();
                    SimpleCacheInfo sci = entry.getValue();
                    if (sci.getSecondsTotal() != ICache.Forever) {
                        sci.setSecondsRemain(sci.getSecondsRemain() - FreshTimerIntervalSeconds);
                        if (sci.getSecondsRemain() <= 0) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }
}
