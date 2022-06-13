package com.sb.liz.nlp;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.parser.ParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

public class SentenceParser {
    public static String parse(String sentence) throws IOException{
        InputStream is = new FileInputStream("models/en-parser-chunking.bin");
        ParserModel model = new ParserModel(is);

        Parser parser = ParserFactory.create(model);
        Parse [] parses = ParserTool.parseLine(sentence, parser, 1);

        is.close();
        StringBuffer parseTree = new StringBuffer();
        parses[0].show(parseTree);

        return  parseTree.toString();
    }
}
