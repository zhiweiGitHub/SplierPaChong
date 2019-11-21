package com.tupian;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这个类用来解析url页面
 */
public class ParsePage {
    String falg = "";
    /**
     * 这个方法解析主页面的url
     * 将<a href></a>解析出来
     */
    public void parseMainPage(String url) {

        try {
            URL url1 = new URL(url);
            InputStream in = url1.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            StringBuffer sb = new StringBuffer(out.toString());
            Pattern a_href = Pattern.compile("<a.*?href=\"([\\u0000-\\uffff&&[^\"]]*)\"");
            Pattern a_fenye = Pattern.compile("<a.*?href='([\\u0000-\\uffff&&[^\"]]*)'");
            Matcher m = a_href.matcher(sb);
            while (m.find()) {
                String src_zhuye = m.group(1);
                //   /html/\d*/
                Pattern a_href1 = Pattern.compile("/html/\\d*/");
                Matcher mm = a_href1.matcher(src_zhuye);
                while (mm.find()) {
                    String daixiazai_url = mm.group();
                    //System.out.println(daixiazai_url);
                    //https://6518x.com
                    daixiazai_url = "https://6518x.com"+daixiazai_url;
                    //PageQue.getInstance().addPageSrc(daixiazai_url);

                    if (falg.equals(daixiazai_url)) {
                        continue;
                    }


                    System.out.println(daixiazai_url);
                    String src_html = null;
                    URL urlcc = new URL(daixiazai_url);
                    try {
                        InputStream inputStream = urlcc.openStream();
                        ByteArrayOutputStream outcc = new ByteArrayOutputStream();
                        int lencc = 0;
                        byte[] buffer = new byte[1024];
                        while ((lencc=inputStream.read(buffer))>0) {
                            outcc.write(buffer, 0, lencc);
                        }
                        String cc = new String(outcc.toByteArray());
                        //System.out.println(cc);

                        outcc.close();
                        inputStream.close();

                        //<a\s*href="[\u0000-\uffff&&[^"]]*"
                        //<a.*?href="|'[\u0000-\uffff&&[^"]]*"|'
                        Pattern compilecc = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
                        Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                        Matcher matcherc = compilecc.matcher(cc);
                        while (matcherc.find()) {
                            String group = matcherc.group(2);
                            Matcher matcher1 = p_src.matcher(group);
                            while (matcher1.find()) {
                                src_html = matcher1.group(3);
                                System.out.println(src_html);
                                if (!(src_html.contains("jpg") || src_html.contains("png"))) {
                                    continue;
                                }
                                if (src_html.equals("https://linjijian.com/img/an.png")||src_html.equals("https://linjijian.com/img/logo.png")||src_html.equals("https://linjijian.com/img/lazylitpic.gif")||src_html.equals("https://linjijian.com/img/sj_gg.png")||src_html.equals("https://linjijian.com/img/a2.jpg")||src_html.equals("https://linjijian.com/img/fy.png")) {
                                    continue;
                                }
                                URL url1c = new URL(src_html);
                                InputStream inputStream1 = url1c.openStream();
                                String png_jpg = src_html.substring(src_html.lastIndexOf("."));
                                String filename ="/home/zhiwei/PaChong/"+ System.currentTimeMillis() + png_jpg;
                                FileOutputStream out1c = new FileOutputStream(filename);
                                int len1c = 0;
                                byte[] buffer1c = new byte[1024];
                                while ((len1c=inputStream1.read(buffer1c))>0) {
                                    out1c.write(buffer1c, 0, len1c);
                                }

                                //System.out.println(cc);

                                out1c.close();
                                inputStream1.close();
                            }
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    falg = daixiazai_url;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 这个方法用来解析url如果是主页面，添加到主页面队列,
     * 如果不是主页面，就添加到待下载页面队列
     *
     * @param url
     * @return
     */
    public void isMainPage(String url) {
        //如果有<p></p>的是待下载页面，否则是主页面
        try {
            URL url1 = new URL(url);
            InputStream in = url1.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            StringBuffer sb = new StringBuffer(out.toString());
            //<p>.*</p>
            if (!sb.toString().contains("<p>")) {
                //非待下载页面，即主页面
                PageQue.getInstance().addMainPage(url);
                return;
            }
            //如果是待下载页面，添加到待下载页面队列
            PageQue.getInstance().addPageSrc(url);

           /* Pattern p = Pattern.compile("<p>.*</p>");
            Matcher m = p.matcher(sb);
            while (m.find()) {
                String group = m.group();
                if (group==null||"".equals(group)) {
                    System.out.println("这个是空");
                }
            }*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
