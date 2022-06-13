package com.sb.liz.nlp;

import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceDetectorME;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

public class TokenGenerator {
    /**
     * Tokenizes the given text
     * @param sentence The sentence to extract tokens from
     * @return An array of tokens from the sentence
     */
    public static String[] tokenize(String sentence) throws IOException {
        InputStream is = new FileInputStream("models/en-tokens.bin");
        TokenizerModel model = new TokenizerModel(is);

        TokenizerME tokenizer = new TokenizerME(model);

        String [] tokens = tokenizer.tokenize(sentence);

        is.close();

        return tokens;
    }

    /**
     * Detects sentences in given block of string
     * @param text block of text to break into sentences
     * @return The sentences found in the text.
     */
    public static String [] sentenceTokens(String text) throws IOException {

        // Loading pre-trained OpenNLP sentence detector model
        InputStream is = new FileInputStream("models/en-sent.bin");
        SentenceModel sentenceModel = new SentenceModel(is);

        SentenceDetectorME sdetector = new SentenceDetectorME(sentenceModel);

        // Generating Array of Sentences in text
        String [] sentences = sdetector.sentDetect(text);

        is.close();

        return sentences;
    }

    public static String stripPunct(String sentence){
        return sentence.replaceAll("[.!?,\\-]", "");
    }

    public static String [] normalizeTokens(String[] tokens){
        List<String> tokensList = Arrays.asList(tokens);
        return tokensList.stream().map(String::toLowerCase).toArray(String[]::new);
    }

    public static String [] tagPos(String [] tokens) throws IOException{
       InputStream is = new FileInputStream("models/en-pos-maxent.bin");
       POSModel model = new POSModel(is);

       POSTaggerME tagger = new POSTaggerME(model);

       String [] posTags = tagger.tag(tokens);
       is.close();
       return  posTags;
    }
}
