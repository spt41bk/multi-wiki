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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 
 */
public class SplitTitles {

    public static void splitFile(String titleFile, int size, String outDir) {

        try {
            utils.FileUtils.newDir(outDir);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(titleFile)), "utf-8"));
            String line;
            String fileName = new File(titleFile).getName();
            int nfile = 1;
            int count = 0;
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, fileName + "-" + nfile)), "utf-8"));

            while ((line = reader.readLine()) != null) {
                writer.append(line);
                writer.write("\n");
                count++;
                if (count == size) {
                    writer.close();
                    count = 0;
                    nfile++;
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, fileName + "-" + nfile)), "utf-8"));
                }
            }

            reader.close();
            writer.close();
            System.out.println("Split file: completed!");
            System.out.println("File: "+titleFile);
            System.out.println("Size: "+size);
            System.out.println("Files: "+nfile);
            System.out.println("");

        } catch (IOException ex) {
            Logger.getLogger(SplitTitles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
