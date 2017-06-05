/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 
 */
public class Crawler_Wikipedia {

    public static void main(String[] args) {
        String option = args[0];
        if (option.matches("extract-parallel-titles")) {
            extractParallelTitles(args[1], args[2], args[3], args[4], args[5], args[6]);
        } else if (option.matches("split-file")) {
            splitFile(args[1], Integer.parseInt(args[2]), args[3]);
        } else if (option.matches("crawl-text")) {
            crawlText(args[1], args[2], args[3]);
        } //else if (option.matches("crawl-wikipedia-text")) {
        //            crawlWikipediaText(args[1], args[2], args[3], args[4], args[5], args[6]);
        //        }
        else if (option.matches("update-titles")) {
            updateTitles(args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
        } else if (option.matches("copy-crawled-files")) {
            copyCrawledFiles(args[1], args[2], args[3]);
        } else if (option.matches("retrieve-crawled-data")) {
            retrieveCrawledData(args[1], args[2], args[3], args[4], args[5]);
        } else if (option.matches("retrieve-crawled-data-list-files")) {
            retrieveCrawledData_listFile(args[1], args[2]);
        } else if (option.matches("update-titles-repository")) {
            updateTitles_repository(args[1], args[2], args[3], args[4], args[5]);
        } else if (option.matches("split-crawled-dirs")) {
            splitCrawledDirs(args[1], Integer.parseInt(args[2]), args[3], args[4], args[5]);
        }

    }

    public static void splitCrawledDirs(String inDirName, int size, String srclang, String trglang, String outDir) {
        preprocess.UpdateTitles.splitCrawledDirs(inDirName, size, srclang, trglang, outDir);
    }

    public static void updateTitles_repository(String crawledTitleFile, String inputTitleIDFile, String outDir, String overlappedTitles, String newTitles) {

        preprocess.UpdateTitles.updateTitles_repository(crawledTitleFile, inputTitleIDFile, outDir, overlappedTitles, newTitles);
    }

    public static void retrieveCrawledData_listFile(String inDir, String titleFileName) {
        preprocess.UpdateTitles.retrieveCrawledData_listFile(inDir, titleFileName);
    }

    public static void retrieveCrawledData(String inDir, String titleIDFile, String outDir, String titleFileName, String updateFile) {
        preprocess.UpdateTitles.retrieveCrawledData(inDir, titleIDFile, outDir, titleFileName, updateFile);
    }

//    public static void copyCrawledFiles(String crawledDir, String overlappedID, String inputTitleIDFile, String outDir) {
    public static void copyCrawledFiles(String repositoryDir, String inputTitleIDFile, String outDir) {

        preprocess.UpdateTitles.copyCrawledFiles(repositoryDir, inputTitleIDFile, outDir);

//        preprocess.UpdateTitles.copyCrawledFiles(crawledDir, overlappedID, inputTitleIDFile, outDir);
    }

    public static void updateTitles(String crawledDir, String inputTitleIDFile, String newInputTitle, String outDir, String overlappedTitles, String overlappedID, String newTitles) {

        preprocess.UpdateTitles.updateTitles(crawledDir, inputTitleIDFile, newInputTitle, outDir, overlappedTitles, overlappedID, newTitles);
    }

//    public static void crawlWikipediaText(String pageLink, String titlesIdFile, String srclang, String trglang, String crawl_lang, String outDir) {
//        
//        crawler.CrawlWikipedia.crawlWikipediaText(pageLink, titlesIdFile, srclang, trglang, crawl_lang, outDir);
//    }
    public static void splitFile(String titleFile, int size, String outDir) {
        preprocess.SplitTitles.splitFile(titleFile, size, outDir);
    }

    public static void crawlText(String url, String outDir, String outFileName) {
        crawler.Crawler.crawlText(url, outDir, outFileName);
    }

    public static void extractParallelTitles(String inFile, String outDir, String srcOutFile, String trgOutFile, String srcIdOutFile, String trgIdOutFile) {

        preprocess.ExtractTitles.extractParallelTitles(inFile, outDir, srcOutFile, trgOutFile, srcIdOutFile, trgIdOutFile);
    }

}
