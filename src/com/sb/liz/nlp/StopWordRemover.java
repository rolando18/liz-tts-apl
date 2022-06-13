package com.sb.liz.nlp;

import java.io.IOException;
//import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
//import java.util.Scanner;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class StopWordRemover {
    // the dictionary containing the stopwords is rather large
    // declaring it as an attribute, so it only loads once
    private final String [] stopWordsDict;

    StopWordRemover() throws IOException{
        // the default constructor loads the stopwords dictionary into memory
        this.stopWordsDict = loadStopWordsFromDict();
    }
    public String removeStopWords(String sentence) throws IOException{
        String s = sentence;
        for(String sw : getSentenceStopWords(sentence)){
            s = s.replaceAll(sw, "");
        }
        return s;
    }

    public String [] getSentenceStopWords(String sentence) throws IOException {
        List <String> stopWords = new ArrayList<String>();
//        String [] stopWordsDict = loadStopWordsFromDict();
        String stopWordsPattern = String.join("|", this.stopWordsDict);
        Pattern p = Pattern.compile("\\b(?:" + stopWordsPattern + ")\\b\\s*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(sentence);

        while (matcher.find()){
            stopWords.add(matcher.group());
        }

        return stopWords.toArray(new String[0]);
    }

    private static String [] loadStopWordsFromDict() throws IOException {
//        Scanner reader = new Scanner(new File("resources/en-stop-words.txt"));
//        Replacing Scanner with BufferedReader for faster load
        List<String> stopWords;
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/en-stop-words.txt"))) {
/*
            while (reader.hasNextLine()) {
                stopWords.add(reader.nextLine());
            }
*/
            stopWords = reader.lines().collect(Collectors.toList());
        }
        return stopWords.toArray(new String[0]);
    }
}
