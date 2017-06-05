/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocess;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;

/**
 *
 
 */
public class SplitSentences {

    public static void main(String[] args) {
        String text="Another ex-Golden Stater, Paul Stankowski from Oxnard, is contending\n" +
"for a berth on the U.S. Ryder Cup team after winning his first PGA Tour\n" +
"event last year and staying within three strokes of the lead through\n" +
"three rounds of last month's U.S. Open. H.J. Heinz Company said it\n" +
"completed the sale of its Ore-Ida frozen-food business catering to the\n" +
"service industry to McCain Foods Ltd. for about $500 million.\n" +
"It's the first group action of its kind in Britain and one of\n" +
"only a handful of lawsuits against tobacco companies outside the\n" +
"U.S. A Paris lawyer last year sued France's Seita SA on behalf of\n" +
"two cancer-stricken smokers. Japan Tobacco Inc. faces a suit from\n" +
"five smokers who accuse the government-owned company of hooking\n" +
"them on an addictive product.";
        splitSentences(text);
    }
    
    
    public static void splitSentences(String text) {

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
//        String text = ""; // Add your text here!

// create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

// run all Annotators on this text
        pipeline.annotate(document);

// these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
//            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
//                // this is the text of the token
//                String word = token.get(TextAnnotation.class);
//                // this is the POS tag of the token
//                String pos = token.get(PartOfSpeechAnnotation.class);
//                // this is the NER label of the token
//                String ne = token.get(NamedEntityTagAnnotation.class);
//            }
            System.out.println(sentence);

        }

    }

}
