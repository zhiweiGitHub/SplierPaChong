package com.zhiwei;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * 这个是下载类
 */
public class Downloads {

    private String pageLocal = "/home/zhiwei/PaChong/";
    private static Downloads downloads;
    private Downloads() {

    }

    public static Downloads getInstance() {
        if (downloads == null) {
            downloads = new Downloads();
        }
        return downloads;
    }

    private PageDao dao = new PageDao();

    /**
     * 1.从数据库中查询url，如果存在，就不下载
     * 2.如果没下载过将rul追加到下载队列
     *
     * @param url
     */
    public void download(String url) {
        //1.先下载页面
        String page = this.doDownload(url);
        //2.保存到磁盘
        String locPath = pageLocal + System.currentTimeMillis() +".html";
        savePageToDisk(locPath, page);
        //3.存到数据库中
        dao.insert(url, locPath, "111");

        //4.解析正则表达式，所有页面中的<a href="这里面的东西",得到所有的href地址
        Set<String> hrefs = RegexUtil.parsePage(url, page);
        for (String href : hrefs) {
            if (!dao.exist(url)) {
                PageQueue.getInstance().addUrl(url);
            }
        }
        //3.判断解析出来的url集合在数据库中是否收录
        Boolean b = dao.exist(url);
        //如果不存在
        if (!b) {
            //追加url到队列
            PageQueue.getInstance().addUrl(url);
        }
    }

    /**
     * 保存页面到磁盘
     * @param locPath
     */
    private void savePageToDisk(String locPath,String pageCont) {
        try {
            FileOutputStream fos = new FileOutputStream(locPath);
            fos.write(pageCont.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载
     * @param url
     * @return
     */
    private String doDownload(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection connec = (HttpURLConnection) u.openConnection();
            InputStream in = connec.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            String pageStr = new String(out.toByteArray());
            return pageStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
