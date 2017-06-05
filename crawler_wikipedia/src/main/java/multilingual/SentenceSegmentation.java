/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilingual;

/**
 *
  */
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;

public class SentenceSegmentation {

    public static void main(String[] args) {
        sentenceSegmentation();
    }

    public static void sentenceSegmentation() {

//        Document doc = new Document("The text paragraph. Another sentence. Yet another sentence.");
//        List<Sentence> sentences = doc.sentences();
////        sentences.stream().forEach(System.out::println);
//        for (Sentence sentence : sentences) {
//            System.out.println(sentence.toString());
//        }
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
        List<CoreMap> sentences = pipeline.process("The text paragraph. Another sentence. Yet another sentence.")
                .get(CoreAnnotations.SentencesAnnotation.class);
        // I just gave a String constant which contains sentences.
        for (CoreMap sentence : sentences) {
            System.out.println(sentence.toString());
        }

    }
}
