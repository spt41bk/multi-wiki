/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 
 */
public class FileUtils {

    public static void newDir(String dirName) {
        if (!new File(dirName).exists()) {
            new File(dirName).mkdirs();
        }
    }

    public static void readwriter(String inFile, String outDir, String outFile) {

        try {
            utils.FileUtils.newDir(outDir);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, outFile)), "utf-8"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
            }

            reader.close();
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void fileStatistics(String inFile, String outDir, String outFile) {

        try {

            utils.FileUtils.newDir(outDir);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, outFile)), "utf-8"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), "utf-8"));

            int nlines = 0;
            int nwords = 0;
            Set<String> vocabSet = new HashSet<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    nlines++;
                    String[] words = line.split(" ");
                    nwords += words.length;
                    for (String word : words) {
                        vocabSet.add(word);
                    }
                }
            }

            reader.close();

            writer.write("File Statistics:");
            writer.write("\n");
            writer.write("File: " + inFile);
            writer.write("\n");
            writer.write("Sentences: " + nlines);
            writer.write("\n");
            writer.write("Words: " + nwords);
            writer.write("\n");
            writer.write("Vocabulary: " + vocabSet.size());
            writer.write("\n");
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void copyFile(File inFile, File outFile) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "utf-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));

            String line;
            int nline = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    writer.write(line);
                    writer.write("\n");
                    nline++;
                }
            }
            reader.close();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static boolean copyFile2(File inFile, File outFile) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "utf-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));

            String line;
            int nline = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    writer.write(line);
                    writer.write("\n");
                    nline++;
                }
            }
            reader.close();
            writer.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

}
