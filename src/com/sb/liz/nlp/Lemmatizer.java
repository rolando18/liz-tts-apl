package com.sb.liz.nlp;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Lemmatizer {
    public static String [] lemmatize(String [] tokens, String [] posTags) throws IOException{
        InputStream is = new FileInputStream("models/en-lemmatizer.txt");
        DictionaryLemmatizer dLemma = new DictionaryLemmatizer(is);

        String [] lemmas = dLemma.lemmatize(tokens, posTags);
        is.close();

        return lemmas;
    }
}
