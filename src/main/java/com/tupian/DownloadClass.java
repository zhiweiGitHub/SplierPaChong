package com.tupian;

import java.sql.Connection;

/**
 * 这个类用来下载图片
 */
public class DownloadClass {
    private Dao dao = new Dao();
    /**
     * 首先从数据库中查询数据是否存在
     * 如果存在，就不下载
     * 如果不存在，就追加到序列
     * @param url
     */
    public void download(String url) {
        if (dao.selectUrl(url)) {
            //如果url在数据库中存在
            return;
        }
    }
}
