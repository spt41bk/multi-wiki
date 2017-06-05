/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 
 */
public class ExtractTitles {

//        public static final String PHRASE_TABLE_PATTERN = "[|]{3}";
    public static Map<String, String> readTitleID(String titleIDFile) {
        Map<String, String> titleMap = new HashMap<>();

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(titleIDFile)), "utf-8"));
            String line;
            int nlines = 0;
            while ((line = reader.readLine()) != null) {
                String[] st = line.split("\t");
                titleMap.put(st[0], st[1]);
            }

            reader.close();
            
            System.out.println("Title map: "+titleMap.size());

        } catch (IOException ex) {
            Logger.getLogger(ExtractTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

        return titleMap;

    }
    
    public static Map<String, String> readTitleID_invert(String titleIDFile) {
        Map<String, String> titleMap = new HashMap<>();

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(titleIDFile)), "utf-8"));
            String line;
            int nlines = 0;
            while ((line = reader.readLine()) != null) {
                String[] st = line.split("\t");
                titleMap.put(st[1], st[0]);
            }

            reader.close();
            
            System.out.println("Title map: "+titleMap.size());

        } catch (IOException ex) {
            Logger.getLogger(ExtractTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

        return titleMap;

    }

    public static void extractParallelTitles(String inFile, String outDir, String srcOutFile, String trgOutFile, String srcIdOutFile, String trgIdOutFile) {

        try {
            utils.FileUtils.newDir(outDir);
            BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, srcOutFile)), "utf-8"));
            BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, trgOutFile)), "utf-8"));
            BufferedWriter srcIdWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, srcIdOutFile)), "utf-8"));
            BufferedWriter trgIdWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, trgIdOutFile)), "utf-8"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), "utf-8"));
            String line;
            int nlines = 0;
            while ((line = reader.readLine()) != null) {
                String[] titles = line.split("[|]{3}");
                if (titles.length == 2) {
                    nlines++;

                    String src = titles[0].trim().replace(" ", "_");
                    String trg = titles[1].trim().replace(" ", "_");

                    srcWriter.write(src);
                    srcWriter.write("\n");
                    trgWriter.write(trg);
                    trgWriter.write("\n");

                    srcIdWriter.write(String.format("%d\t%s\n", nlines, src));
                    trgIdWriter.write(String.format("%d\t%s\n", nlines, trg));
                }
            }

            reader.close();
            srcWriter.close();
            trgWriter.close();
            srcIdWriter.close();
            trgIdWriter.close();

            System.out.println("Extract parallel titles: completed!");
            System.out.println("File: " + inFile);
            System.out.println("Output: " + outDir);
            System.out.println("Titles: " + nlines);
            System.out.println("");

        } catch (IOException ex) {
            Logger.getLogger(ExtractTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
