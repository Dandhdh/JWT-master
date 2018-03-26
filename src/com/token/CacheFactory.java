package com.token;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 缓存工厂,用于获取和制造缓存
 * @author jgao
 *
 */
public class CacheFactory {

    private static ICache cache = null;

    /**
     * 获取caches指定的缓存
     * @param caches
     * @return
     */
    public static ICache getCacheInstance(Class caches){
        if(cache==null){
            try {
                cache = (ICache) caches.newInstance();
            } catch (InstantiationException e) {
                System.out.println("指定的缓存类有误,caches参数必须是ICache的实现类");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.out.println("指定的缓存类有误,caches参数必须是ICache的实现类");
                e.printStackTrace();
            }
        }
        return cache;
    }

    /**
     * 获取系统默认的缓存
     * @return
     */
    public static ICache getDefaultCache(){
        if(cache==null){
            cache = new DefaultCache();
        }else if(!(cache instanceof DefaultCache)){
            cache = new DefaultCache();
        }
        return cache;
    }

    public static void main(String[] args) {
        final ICache cache = CacheFactory.getDefaultCache();
        if(cache.contains("area")){
            System.out.println(cache.get("area"));
        }else{
            cache.Insert("area","福州",5);
        }

        System.out.println(cache.get("area"));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("=======");
                System.out.println(cache.get("area"));
                System.out.println("----");
            }
        }, 7000);// 设定指定的时间time,此处为2000毫秒
    }
}