package com.sb.liz.nlp;

import opennlp.tools.util.InvalidFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class LexicalAnalyzer {

    /**
     * Performs Lexical Analysis on given sentence
     * @param sentence block of text to analyze
     * @return Sentence tokens and POS tags
     */
    public static String [][] doLexicalAnalysis(String sentence){
        List<String[]> tokensPOS = new ArrayList<String[]>();
        try{
            StopWordRemover swr = new StopWordRemover();
//            String [] sentences = TokenGenerator.sentenceTokens(text);

            String noPunSentence = TokenGenerator.stripPunct(sentence);
            String [] sentenceStopWords = swr.getSentenceStopWords(noPunSentence);
            String sentenceNoStopWords = swr.removeStopWords(noPunSentence);
            String [] tokens = TokenGenerator.tokenize(noPunSentence);
            String [] normalizedTokens = TokenGenerator.normalizeTokens(tokens);
            String [] posTags = TokenGenerator.tagPos(tokens);
            String [] lemmatizedTokens = Lemmatizer.lemmatize(tokens, posTags);

            System.out.println("====== SENTENCE =======");
            System.out.println(sentence + "\n");

            System.out.println("====== STOP WORDS ======");
            System.out.println(Arrays.toString(sentenceStopWords) + "\n");

            System.out.println("====== NO STOP WORDS ======");
            System.out.println(sentenceNoStopWords + "\n");

            System.out.println("====== SENTENCE TOKENS =======");
            System.out.println(Arrays.toString(tokens) + "\n");

            System.out.println("====== NORMALIZED TOKENS =======");
            System.out.println(Arrays.toString(normalizedTokens) + "\n");

            System.out.println("====== POS TAGS =======");
            System.out.println(Arrays.toString(posTags) + "\n");

            System.out.println("====== LEMMATIZED TOKENS ======");
            System.out.println(Arrays.toString(lemmatizedTokens) + "\n");

            tokensPOS.add(tokens);
            tokensPOS.add(posTags);

        }
        catch(InvalidFormatException ex){
            System.out.println(ex.getMessage());
        }
        catch(FileNotFoundException ex){
            System.out.println("The Lexical Analysis module could not find some file.");
            System.out.println(ex.getMessage());
        }
        catch(IOException ex){
            System.out.println("There was another error loading some Model.");
            System.out.println(ex.getMessage());
        }
        catch(Exception ex){
            System.out.println("An unexpected error has occurred.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return tokensPOS.toArray(new String[0][0]);
    }


}
