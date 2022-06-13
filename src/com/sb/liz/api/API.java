package com.sb.liz.api;

import com.sb.liz.nlp.*;
import com.sb.liz.tts.*;

import static spark.Spark.*;
import com.google.gson.Gson;

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
                        System.out.println(req.body());
                        res.status(200);
                        res.header("Content-Type", "text/json");
                        return "Request received.";
            });
            get("/audio",
                    (req, res) -> {
                        System.out.println("Sending back requested file.");
                        return "Audio wav";
            });
        });

    }
}
