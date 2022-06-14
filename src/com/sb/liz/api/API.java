package com.sb.liz.api;

import com.sb.liz.nlp.*;
import com.sb.liz.tts.*;

import static spark.Spark.*;
import com.google.gson.Gson;

import java.util.Arrays;

public class API {
    public API(){
        get("/test", (req, res) ->
            "Server is running on port 4567."
        );

        get("/stop", (req, res) -> {
            stop();
            return "Server stopped.";
        });

        path("/speak", () -> {
            post("/text",
                    (req, res) -> {
                        try{
                            String paragraph = req.body();
                            String [] parseTrees = NLPUnit.doNLP(paragraph);
                            if(NLPUnit.isSuccess){
                                String audioFileName = SpeechUnit.doTTS(paragraph);
                                res.header("Content-Type", "application/json");
                                res.status(200);

                                Gson gson = new Gson();
                                return "{\"parseTrees\":" +
                                        gson.toJson(parseTrees) +
                                        ",\"fileName\":\"" +
                                        audioFileName + "\"}";
                            }
                            else throw new Exception("Uh-oh! Looks like there may be something wrong with your text.");
                        }
                        catch(Exception ex){
                            res.status(400);
                            return "Uh-oh! Looks like there may be something wrong with your text.";
                        }
            });
            get("/audio",
                    (req, res) -> {
                        System.out.println("Sending back requested file.");
                        return "Audio wav";
            });
        });

    }
}
