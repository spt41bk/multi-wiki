/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
  */
public class CrawlWikipedia {

    public static void crawlWikipediaText(String pageLink, String titlesIdFile, String srclang, String trglang, String crawl_lang, String outDir) {

        Map<String, String> titleMap = getTitles(titlesIdFile);

        utils.FileUtils.newDir(outDir);
        for (String id : titleMap.keySet()) {
            String title = titleMap.get(id);
            String url = pageLink + title;
//                String outFileName = String.format("%s.%s-%s.%s", id, srclang, trglang, crawl_lang);
            crawler.Crawler.crawlText(url, outDir, id);
        }

        System.out.println("Crawl wikipedia: completed!");
        System.out.println("File: " + titlesIdFile);
        System.out.println("Titles: " + titleMap.size());
        System.out.println("");

    }

    public static void crawlWikipediaText2(String pageLink, String titlesIdFile, String updateTitleFile, String srclang, String trglang, String crawl_lang, String outDir) {

        Map<String, String> titleMap = getTitles(titlesIdFile);
        Set<String> updateSet = utils.ReadUtils.read2set(updateTitleFile);

        utils.FileUtils.newDir(outDir);
        for (String id : titleMap.keySet()) {
            String title = titleMap.get(id);
            if (!updateSet.contains(title)) {
                String url = pageLink + title;
                String outFileName = String.format("%s.%s-%s.%s", id, srclang, trglang, crawl_lang);
                crawler.Crawler.crawlText(url, outDir, outFileName);
            }
        }

        System.out.println("Crawl wikipedia: completed!");
        System.out.println("File: " + titlesIdFile);
        System.out.println("Titles: " + titleMap.size());
        System.out.println("");

    }

    public static Map<String, String> getTitles(String titlesIdFile) {
        Map<String, String> titleMap = new HashMap<>();

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(titlesIdFile)), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] titles = line.split("\t");
                titleMap.put(titles[0], titles[1]);
            }

            reader.close();

        } catch (IOException ex) {
            Logger.getLogger(CrawlWikipedia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return titleMap;

    }

}
