package com.yao.douban.proxytool.http.client;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 单耀 on 2018/1/27.
 */
public class ProxyHttpClient extends AbstractHttpClient{
    private volatile static ProxyHttpClient instance;
    //下载代理页面的线程池
    private ThreadPoolExecutor proxyDoloadThreadExector;

    private ThreadPoolExecutor proxyProxyTestExector;
    public static ProxyHttpClient getInstance() {
        if (instance == null) {
            synchronized (ProxyHttpClient.class) {
                if (instance == null) {
                    instance = new ProxyHttpClient();
                }
            }
        }
        return instance;
    }

    public ProxyHttpClient() {
        initThreadPool();
    }

    //初始化线程池
    private void initThreadPool() {
        //线程通过线程工厂创建，这样每个线程都会有名字，以便于
        proxyDoloadThreadExector = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1000),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "proxyDoloadThreadExector" + r.hashCode());
                    }
                });

        proxyProxyTestExector = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "proxyProxyTestExector" + r.hashCode());
                    }
                });
    }

    public ThreadPoolExecutor getProxyDoloadThreadExector() {
        return proxyDoloadThreadExector;
    }

    public void setProxyDoloadThreadExector(ThreadPoolExecutor proxyDoloadThreadExector) {
        this.proxyDoloadThreadExector = proxyDoloadThreadExector;
    }

    public ThreadPoolExecutor getProxyProxyTestExector() {
        return proxyProxyTestExector;
    }

    public void setProxyProxyTestExector(ThreadPoolExecutor proxyProxyTestExector) {
        this.proxyProxyTestExector = proxyProxyTestExector;
    }
}
