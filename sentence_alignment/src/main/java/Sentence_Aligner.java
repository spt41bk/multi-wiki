/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *

 */
public class Sentence_Aligner {

    public static void main(String[] args) {
        String option = args[0];

        if (option.matches("length-based")) {
            length_based(args[1], args[2], args[3], Integer.parseInt(args[4]), Double.parseDouble(args[5]), args[6], args[7], args[8], args[9]);
        } else if (option.matches("ibm1")) {
            ibm1(args[1], args[2], Integer.parseInt(args[3]), args[4], args[5]);
        } 
        else if (option.matches("length-and-word-based")) {
            length_and_word_based(args[1], args[2], args[3], Integer.parseInt(args[4]), Double.parseDouble(args[5]), args[6], args[7], args[8]);
        }
    }

    public static void length_based(String inDir, String srclang, String trglang, int searchinglimit, double threshold1, String lengthBasedDir, String ibm1Dir, String mergedSrcFile, String mergedTrgFile) {
        length_based.LengthBased_Phase.length_based_dir(inDir, srclang, trglang, searchinglimit, threshold1, lengthBasedDir, ibm1Dir, mergedSrcFile, mergedTrgFile);
    }

    public static void ibm1(String mergedSrcFile, String mergedTrgFile, int loop, String ibm1Dir, String ibm1File) {
        ibm1.IBM1.build_IBM1(mergedSrcFile, mergedTrgFile, loop, ibm1Dir, ibm1File);
    }

    

    
     public static void length_and_word_based(String inDir, String srclang, String trglang, int searchinglimit, double threshold2, String lengthBasedDir, String ibm1File, String alignedDir) {
        length_and_word.LengthAndWordBased_Phase.lengthAndWordBased(inDir, srclang, trglang, searchinglimit, threshold2, lengthBasedDir, ibm1File, alignedDir);
    }

}
