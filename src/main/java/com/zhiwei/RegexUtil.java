package com.zhiwei;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * 解析出网页中的所有<a></a>标签的地址
     * @param page
     * @param srcUrl
     * @return
     */
    public static Set<String> parsePage(String srcUrl,String page) {
        //将匹配到的Url放到set集合中
        Set<String> urls = new HashSet<String>();
        //<a\s*href="[\u0000-\uffff&&[^"]]*"
        Pattern p = Pattern.compile("<a\\s*href=\"([\u0000-\uffff&&[^\"]]*)\"");
        Matcher matcher = p.matcher(page);
        while (matcher.find()) {
            //group陪陪到上面正则表达式的（）内的值，因为只有一个（）,所以索引是1
            String ahref = matcher.group(1);
            if (ahref.equals("//www.w3cschool.cn")) {
                continue;
            }
            if (ahref.startsWith("https://")) {
                urls.add(ahref);
            }
            //如果开头是以/开始
            else if (ahref.startsWith("/")) {
                //根据srcUrl解析出域名
                String domainame = RegexUtil.parseDomainame(srcUrl);
                urls.add(domainame + ahref);
            }
            //如果是以.开头
            //else if (ahref.startsWith())

        }
        return urls;
    }

    /**
     * 从url地址中解析出域名
     * 即 http://www.baidu.com/xxxx
     * 找到http://www.baidu.com/   这一部分
     *
     * @param srcUrl
     * @return
     */
    public static String parseDomainame(String srcUrl) {

        if (!srcUrl.contains("/")) {
            return srcUrl;
        }
        
        
        //http://[\u0000-\uffff&&[^/]]*/
        Pattern p = Pattern.compile("https://[\\u0000-\\uffff&&[^/]]*/");
        Matcher m = p.matcher(srcUrl);
        if (m.find()) {
            //System.out.println(m.group());//注意，这里最后有个/  即https://www.baidu.com/ 最后的左斜线
            String domin = m.group();
            return domin.substring(0, domin.length() - 1);
        }
        return null;
    }
}
