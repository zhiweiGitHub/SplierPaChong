package com.zhiwei;

/**
 * 这个是爬虫类
 */
public class Splier extends Thread {
    @Override
    public void run() {
        while (true) {
            String url = PageQueue.getInstance().takeFirst();
            Downloads.getInstance().download(url);
        }
    }
}
