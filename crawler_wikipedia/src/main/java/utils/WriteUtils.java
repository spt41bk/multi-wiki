/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 
 */
public class WriteUtils {

    public static void writeList2File(List<String> list, int size, String outDir, String outFile) {

        try {
            utils.FileUtils.newDir(outDir);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, outFile)), "utf-8"));
            for (int i = 0; i < size; i++) {
                writer.write(list.get(i));
                writer.write("\n");
            }

            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
