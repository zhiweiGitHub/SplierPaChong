package com.zhiwei;

import java.util.LinkedList;

/**
 * 页面队列
 */
public class PageQueue {
    private static PageQueue instance;
    private PageQueue() {
        queue.add("https://www.w3cschool.cn/");
    }

    public static PageQueue getInstance() {
        if (instance == null) {
            instance = new PageQueue();
        }
        return instance;
    }

    private LinkedList<String> queue = new LinkedList<String>();
    private int MAX = 100000;

    public synchronized void addUrl(String url) {
        while (queue.size() >= MAX) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(url);
        notifyAll();
    }


    public synchronized String takeFirst() {
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String url = queue.removeFirst();
        notifyAll();
        return url;
    }


}
