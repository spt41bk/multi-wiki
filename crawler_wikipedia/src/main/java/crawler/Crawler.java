/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;

/**
 *
  */
public class Crawler {

        public static void crawlText(String url, String outDir, String outFileName) {

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.addRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36");
            InputStream is = conn.getInputStream();
//            utils.FileUtils.newDir(outDir);
            extractSentences(is, outDir, outFileName);
            System.out.println("crawled: " + url);
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void crawlRawData(String url, String outDir, String outFileName){

            try {
                HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
                conn.addRequestProperty(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36");
                InputStream is = conn.getInputStream();
                
                FileOutputStream fos = new FileOutputStream(new File(outDir, outFileName));
                
                fos.getChannel().transferFrom(Channels.newChannel(is), 0, Long.MAX_VALUE);
                
                fos.close();
                
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    public static void extractSentences(InputStream is, String outDir, String outFileName) {

        try {
            String html = readStringFromInputStream(is);
            int beginPara = 0;
            int endPara = 0;
            final String beginParaStr = "<p>";
            final String endParaStr = "</p>";
            StringBuilder para = new StringBuilder(10240);
            while (true) {

                beginPara = html.indexOf(beginParaStr, endPara);
                if (beginPara < 0) {
                    break;
                }
                endPara = html.indexOf(endParaStr, beginPara);
                String paraHtml = html.substring(beginPara + beginParaStr.length(), endPara);

//                String text = Jsoup.parse(paraHtml).text().trim();
//                if (!text.isEmpty()) {
//                    para.append(text);
//                    para.append("\n");
//                }
                para.append(Jsoup.parse(paraHtml).text());
                para.append(System.lineSeparator());
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, outFileName)), "utf-8"));
            writer.write(para.toString());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String readStringFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder(10240);
        char[] buffer = new char[1024];
        int num = 0;
        while (0 < (num = reader.read(buffer, 0, buffer.length))) {
            sb.append(buffer, 0, num);
        }
        return sb.toString();
    }

}
