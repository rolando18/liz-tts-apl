package com.sb.liz.tts;

import java.sql.Timestamp;

import com.sun.speech.freetts.FreeTTS;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import javax.sound.sampled.AudioFileFormat.Type;

public class SpeechUnit {
    public static String doTTS(String text){
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice;
        AudioPlayer audioPlayer = null;
        String fileName = null;
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if(voice != null){
            fileName = "liz-tts-" + new Timestamp(System.currentTimeMillis());
            // replacing all special characters in Timestamp with empty
            fileName = fileName.replaceAll("[:\\-\\s]","").replace(".","");
            audioPlayer = new SingleFileAudioPlayer("audio\\" + fileName, Type.WAVE);
            voice.allocate();
            voice.setAudioPlayer(audioPlayer);
            voice.setRate(160);
            voice.setPitch(170);
            voice.setVolume(3);
            voice.speak(text);
            voice.deallocate();
            audioPlayer.close();
        }
        return fileName;
    }
}
