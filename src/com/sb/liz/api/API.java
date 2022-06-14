package com.sb.liz.api;

import com.sb.liz.nlp.*;
import com.sb.liz.tts.*;

import static spark.Spark.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
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
                                res.header("Access-Control-Allow-Origin", "*");
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
            get("/audio/:fileName",
                    (req, res) -> {
                        res.header("Access-Control-Allow-Origin", "*");
                        try{
                            String fileName = req.params(":fileName") + ".wav";
                            File audio = new File("audio\\" + fileName);
                            if(!audio.exists()) throw new FileNotFoundException();
                            res.status(200);
                            return "File found";
                        }
                        catch(FileNotFoundException ex){
                            res.status(404);
                            return "File not found.";
                        }
                        catch(Exception ex){
                            return "An unexpected error occurred";
                        }
            });
        });

    }
}
