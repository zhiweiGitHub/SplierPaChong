package com.tupian;

import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * 这个用来存放所有主page页面
 */
public class PageQue {

    private PageQue() {
        //初始爬取页面
        pageList.add("");
    }

    private static PageQue queue;

    public static PageQue getInstance() {
        if (queue == null) {
            queue = new PageQue();
        }
        return queue;
    }


    //用来放主page页面
    private LinkedHashSet<String> pageList = new LinkedHashSet<String>();
    //用来放图片page页面
    private LinkedList<String> pageListSrc = new LinkedList<String>();
    //用来存放最大值
    private int MAX =100000;


    //添加主page页面
    public void addMainPage(String url) {
        while (pageList.size() >= MAX) {
            System.out.println("程序终止");
            return;
        }
        pageList.add(url);
        System.out.println(pageList.toString());

    }

    //用来添加图片page页面
    public synchronized void addPageSrc(String url) {
        while (pageListSrc.size() >= MAX) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pageListSrc.add(url);
        notifyAll();
    }

    public synchronized String takeUrl() {
        while (pageListSrc.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String url = pageListSrc.removeFirst();
        notifyAll();
        return url;
    }




}
