/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 
 */
public class UpdateTitles {

//    public static void main(String[] args) {
//        
//        String s1="xyz";
//        String s2 = "abc/de";
//        String s3 = "abc-de";
//        
//        
//
//        
//        File file1=new File(s1);
//        File file2=new File(s2);
//        File file3=new File(s3);        
//        
//        try {
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "utf-8"));
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(UpdateTitles.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(UpdateTitles.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        
//        
//        boolean ok=utils.FileUtils.copyFile2(file1, file2);
//        System.out.println(s2+" "+ok);
//        
//ok=utils.FileUtils.copyFile2(file1, file3);
//        System.out.println(s3+" "+ok);        
//
//        System.out.println(isFilenameValid(s));
//        s = "abcde";
//               System.out.println(isFilenameValid(s));
//
//
//    }
//    public static boolean isFilenameValid(String file) {
//        try {
//            File f = new File(file);
//            f.getCanonicalPath();
//            return true;
//        } catch (IOException ex) {
//            return false;
//        }
//    }
    public static void splitCrawledDirs(String inDirName, int size, String srclang, String trglang, String outDir) {

        Set<String> fileSet = new HashSet<>();

        for (File file : new File(inDirName).listFiles()) {
            fileSet.add(file.getName().split("_")[0]);
        }

        int count = 0;
        int ndirs = 1;
        int ncopied = 0;

        File subDir = new File(outDir + "-" + ndirs);
        subDir.mkdirs();

        for (String fileName : fileSet) {
            String srcFileName = String.format("%s_%s.snt", fileName, srclang);
            String trgFileName = String.format("%s_%s.snt", fileName, trglang);

            File inFile = new File(inDirName, srcFileName);
            File outFile = new File(subDir, srcFileName);
            utils.FileUtils.copyFile(inFile, outFile);

            inFile = new File(inDirName, trgFileName);
            outFile = new File(subDir, trgFileName);
            utils.FileUtils.copyFile(inFile, outFile);

            count++;
            ncopied++;
            if (count == size) {
                ndirs++;
                count = 0;
                subDir = new File(outDir + "-" + ndirs);
                subDir.mkdirs();
            }
        }

        System.out.println("Input files: " + fileSet.size());
        System.out.println("Copied files: " + ncopied);

    }

    public static void retrieveCrawledData_listFile(String inDir, String titleFileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(titleFileName)), "utf-8"));

            int nfiles = 0;

            for (File file : new File(inDir).listFiles()) {
                writer.write(file.getName());
                writer.write("\n");
                nfiles++;
            }
            writer.close();

            System.out.println("Files: " + nfiles);

        } catch (IOException ex) {
            Logger.getLogger(UpdateTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void retrieveCrawledData(String inDir, String titleIDFile, String outDir, String titleFileName, String updateFile) {

        try {
            utils.FileUtils.newDir(outDir);

            Set<String> crawledTitleSet = new HashSet<>();
            if (new File(titleFileName).exists()) {
                crawledTitleSet = utils.ReadUtils.read2set(titleFileName);
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(titleFileName), true), "utf-8"));

            Map<String, String> inputTitleMap = preprocess.ExtractTitles.readTitleID(titleIDFile);

            int ncopied = 0;

            for (File dir : new File(inDir).listFiles()) {
                if (dir.isDirectory()) {
                    for (File file : dir.listFiles()) {
                        String id = file.getName();
                        String title = inputTitleMap.get(id);

                        if (!crawledTitleSet.contains(title)) {
                            boolean ok = utils.FileUtils.copyFile2(file, new File(outDir, title));
                            if (ok) {
                                ncopied++;
                                crawledTitleSet.add(title);
                                writer.write(title);
                                writer.write("\n");
                            }
                        }

                    }
                }
            }

            writer.close();

            BufferedWriter updateWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(updateFile), true), "utf-8"));
            updateWriter.write("Update:\n");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            updateWriter.write(dateFormat.format(date));
            updateWriter.write("\n");
            updateWriter.write("Input dir: " + inDir + "\n");
            updateWriter.write("Copied files: " + ncopied + "\n");
            updateWriter.write("Current files: " + crawledTitleSet.size() + "\n");
            updateWriter.write("-------------------------\n\n");
            updateWriter.close();

            System.out.println("Copy crawled files:");
            System.out.println("Dir: " + inDir);
            System.out.println("Copied files: " + ncopied);
            System.out.println("");

        } catch (IOException ex) {
            Logger.getLogger(UpdateTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void copyCrawledFiles(String repositoryDir, String inputTitleIDFile, String outDir) {

        Map<String, String> inputTitleInvertMap = preprocess.ExtractTitles.readTitleID_invert(inputTitleIDFile);
//        Map<String, String> overlappedMap = preprocess.ExtractTitles.readTitleID(overlappedID);
//        List<String> titleList = utils.ReadUtils.read2list(new File(overlappedID));

        int ncopied = 0;

        utils.FileUtils.newDir(outDir);

        File inDir = new File(repositoryDir);

        for (String title : inputTitleInvertMap.keySet()) {
            String id = inputTitleInvertMap.get(title);
            File inFile = new File(inDir, title);
            File outFile = new File(outDir, id);
            utils.FileUtils.copyFile(inFile, outFile);
            ncopied++;
        }

        System.out.println("Copy crawled files:");
        System.out.println("Crawled files: " + inputTitleInvertMap.size());
        System.out.println("Copied files: " + ncopied);
        System.out.println("");

    }

    public static void copyCrawledFiles2(String crawledDir, String overlappedID, String inputTitleIDFile, String outDir) {

        Map<String, String> inputTitleInvertMap = preprocess.ExtractTitles.readTitleID_invert(inputTitleIDFile);
        Map<String, String> overlappedMap = preprocess.ExtractTitles.readTitleID(overlappedID);

        int ncopied = 0;

        utils.FileUtils.newDir(outDir);

        for (File dir : new File(crawledDir).listFiles()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    String id = file.getName();
                    if (overlappedMap.keySet().contains(id)) {
                        String title = overlappedMap.get(id);
                        String newid = inputTitleInvertMap.get(title);
                        File outFile = new File(outDir, newid);
                        utils.FileUtils.copyFile(file, outFile);
                        ncopied++;
                    }
                }
            }
        }

        System.out.println("Copy crawled files:");
        System.out.println("Crawled files: " + overlappedMap.size());
        System.out.println("Copied files: " + ncopied);
        System.out.println("");

    }

    public static void updateTitles(String crawledDir, String inputTitleIDFile, String newInputTitle, String outDir, String overlappedTitles, String overlappedID, String newTitles) {

        try {
            utils.FileUtils.newDir(outDir);
            BufferedWriter crawledWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, overlappedTitles)), "utf-8"));
            BufferedWriter newWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, newTitles)), "utf-8"));

            List<String> crawledList = getCrawledTitles(crawledDir);
            Map<String, String> inputTitleMap = preprocess.ExtractTitles.readTitleID(inputTitleIDFile);

            Set<String> crawledTitleSet = new HashSet<>();

            int cnt = 0;
            for (String crawledID : crawledList) {
                String crawledTitle = inputTitleMap.get(crawledID);
                crawledTitleSet.add(crawledTitle);
//                cnt++;
//                if(cnt<100){
//                    System.out.println(crawledID);
//                    System.out.println(crawledTitle);
//                    System.out.println("");
//                }
            }
            System.out.println("crawled title set: " + crawledTitleSet.size());

            List<String> newInputList = utils.ReadUtils.read2list(new File(newInputTitle));
            Set<String> overlappedSet = new HashSet<>();

            //check and write here
            int nsaved = 0;
            int nnew = 0;
            for (String title : newInputList) {

                if (crawledTitleSet.contains(title)) {
                    crawledWriter.write(title);
                    crawledWriter.write("\n");
                    nsaved++;
//                    cnt++;
//                    if(cnt<10){
//                        System.out.println(title);
//                    }
                    overlappedSet.add(title);
                } else {
                    newWriter.write(title);
                    newWriter.write("\n");
                    nnew++;
                }

            }

            BufferedWriter idWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, overlappedID)), "utf-8"));

            for (String id : inputTitleMap.keySet()) {
                String title = inputTitleMap.get(id);
                if (overlappedSet.contains(title)) {
                    idWriter.write(String.format("%s\t%s\n", id, title));
                }
            }

            idWriter.close();
            crawledWriter.close();
            newWriter.close();

            System.out.println("Update titles:");
            System.out.println("Input: " + newInputList.size());
            System.out.println("Overlapped: " + nsaved);
            System.out.println("New titles: " + nnew);
            System.out.println("");

        } catch (IOException ex) {
            Logger.getLogger(UpdateTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void updateTitles_repository(String crawledTitleFile, String inputTitleIDFile, String outDir, String overlappedTitles, String newTitles) {

        try {
            utils.FileUtils.newDir(outDir);
            BufferedWriter crawledWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, overlappedTitles)), "utf-8"));
            BufferedWriter newWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, newTitles)), "utf-8"));

            Set<String> crawledTitleSet = utils.ReadUtils.read2set(crawledTitleFile);

            System.out.println("crawled title set: " + crawledTitleSet.size());

//            List<String> newInputList = utils.ReadUtils.read2list(new File(inputTitleIDFile));
//            Set<String> overlappedSet = new HashSet<>();
            Map<String, String> inputTitleMap = preprocess.ExtractTitles.readTitleID(inputTitleIDFile);

            //check and write here
            int nsaved = 0;
            int nnew = 0;
            for (String id : inputTitleMap.keySet()) {
                String title = inputTitleMap.get(id);

                if (crawledTitleSet.contains(title)) {
//                    crawledWriter.write(title);
//                    crawledWriter.write("\n");
                    nsaved++;
//                    overlappedSet.add(title);
                    crawledWriter.write(String.format("%s\t%s\n", id, title));

                } else {
                    newWriter.write(String.format("%s\t%s\n", id, title));
                    nnew++;
                }

            }

            crawledWriter.close();

            System.out.println("Update titles:");
            System.out.println("Input: " + inputTitleMap.size());
            System.out.println("Overlapped: " + nsaved);
            System.out.println("New titles: " + nnew);
            System.out.println("");

        } catch (IOException ex) {
            Logger.getLogger(UpdateTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<String> getCrawledTitles(String crawledDir) {

        List<String> titleList = new ArrayList<>();

        for (File dir : new File(crawledDir).listFiles()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    titleList.add(file.getName());
                }
            } else if (dir.isFile()) {
                titleList.add(dir.getName());
            }
        }

        System.out.println("Dir: " + crawledDir);
        System.out.println("Files: " + titleList.size());
        System.out.println("");

        return titleList;

    }

}
