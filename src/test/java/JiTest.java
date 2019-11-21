/*

import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JiTest {
    @Test
    public void test() throws MalformedURLException {
        String src_html = null;
        URL urlcc = new URL("https://6128x.com/html/1703/");
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
                    if (!(src_html.contains("jpg") || src_html.contains("png"))) {
                        continue;
                    }
                    if (src_html.equals("https://image.5668x.com/p/img/logo_2019.png")) {
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

    }
}
*/
