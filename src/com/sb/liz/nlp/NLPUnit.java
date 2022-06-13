package com.sb.liz.nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NLPUnit {
    public static boolean isSuccess;

    /**
     * Performs NLP functions Lexical Analysis (Tokenization, Lemmatization, POS Tagging) and Parsing on given input
     * @param text to be analysed
     * @return An array of strings containing all Parse Trees in the text.
     */
    public static String [] doNLP(String text) throws Exception{
        List<String> parseTrees = new ArrayList<String>();
        try{
            String [] sentences = TokenGenerator.sentenceTokens(text);
            for(String sentence : sentences){
                String noPunSentence = TokenGenerator.stripPunct(sentence);
                String [][] tokensPos = LexicalAnalyzer.doLexicalAnalysis(sentence);
//                System.out.println(Arrays.deepToString(tokensPos));
                String [] tokens = tokensPos[0];
                String [] pos = tokensPos[1];
                String parseTree = SentenceParser.parse(noPunSentence);
                parseTrees.add(parseTree);
            }

            // Update success
            isSuccess = true;
        }
        catch(IOException ex){
            isSuccess = false;
            System.out.println(ex.getMessage());
        }
        catch (Exception ex){
            isSuccess = false;
            System.out.println("Uh-oh! An unexpected error has occurred.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        if(parseTrees.size() == 0) throw new Exception("Could not generate parse trees from text.");
        return parseTrees.toArray(new String[0]);
    }
}
