package com.yao.douban.douban.task;

import com.yao.douban.douban.DoubanHttpClient;
import com.yao.douban.douban.entity.move.ListMove;
import com.yao.douban.douban.entity.move.Move;
import com.yao.douban.douban.parsers.DoubanPageParser;
import com.yao.douban.douban.parsers.DoubanParserFactory;
import com.yao.douban.douban.parsers.move.MoveParser;
import com.yao.douban.proxytool.ProxyPool;
import com.yao.douban.proxytool.entity.Page;
import com.yao.douban.proxytool.entity.Proxy;
import com.yao.douban.proxytool.http.util.HttpClientUtil;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * Created by 单耀 on 2018/1/28.
 * 电影详细信息下载任务
 */
public class DouBanDetailInfoDownLoadTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(DouBanInfoListPageTask.class);
    private ListMove move;
    private boolean isUseProxy;
    private Proxy currentProxy;
    private DoubanHttpClient doubanHttpClient = DoubanHttpClient.getInstance();

    public DouBanDetailInfoDownLoadTask(ListMove move, boolean isUseProxy) {
        this.move = move;
        this.isUseProxy = isUseProxy;
    }

    public void run() {
        if (move!= null){
            //组装请求url，通过代理获取信息
            Page page = new Page();
            HttpGet request = new HttpGet(move.getUrl());
            try {
                if (isUseProxy) {
                    currentProxy = ProxyPool.proxyQueue.take();
                    HttpHost proxy = new HttpHost(currentProxy.getIp(), currentProxy.getPort());
                    request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(proxy).build());
                    page = doubanHttpClient.getPage(request);

                } else  {
                    page = doubanHttpClient.getPage(move.getUrl());
                }

                if (page!= null && page.getStatusCode() == 200) {
                    handle(page);
                } else {
                    retry();
                }
            } catch (Exception e) {

            } finally {
                if (request != null) {
                    request.releaseConnection();
                }
                if (currentProxy != null) {
                    ProxyPool.proxyQueue.add(currentProxy);
                }
            }

        }
    }

    private void retry() {

    }

    private void handle(Page page) {
        DoubanPageParser parser = DoubanParserFactory.getDoubanParserFactory(MoveParser.class);
        List<Move> list = parser.parser(page.getHtml());
        if (list != null && list.size() > 0) {
            Move _move = list.get(0);
            _move.setName(move.getTitle());
            _move.setId(move.getId());
            _move.setRating(move.getRate());
            //保存到数据库
//            logger.info(_move.toString());
        }
    }
}
