package be.weber.sokoban.code.game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SoundTrack {

    public final int LOOP = 999;

    private HashMap<String, AudioInputStream> dict_track;

    private Clip clip;

    private float volume_power;


    SoundTrack(float volume_power){
        try {
            this.volume_power = volume_power;

            URL url_storyTelling = getClass().getResource("/be/weber/sokoban/resource/soundtrack/StoryTelling.wav");
            URL url_sokobanOnline = getClass().getResource("/be/weber/sokoban/resource/soundtrack/SokobanOnline.wav");
            URL url_victory = getClass().getResource("/be/weber/sokoban/resource/soundtrack/Victory.wav");
            URL url_lose = getClass().getResource("/be/weber/sokoban/resource/soundtrack/Lose.wav");

            assert url_storyTelling != null;
            AudioInputStream audioIntro = AudioSystem.getAudioInputStream(url_storyTelling);
            assert url_sokobanOnline != null;
            AudioInputStream audioGame1 = AudioSystem.getAudioInputStream(url_sokobanOnline);
            assert url_victory != null;
            AudioInputStream audioVictory = AudioSystem.getAudioInputStream(url_victory);
            assert url_lose != null;
            AudioInputStream audioLose = AudioSystem.getAudioInputStream(url_lose);

            dict_track = new HashMap<String, AudioInputStream>();

            dict_track.put("intro", audioIntro);
            dict_track.put("game1", audioGame1);
            dict_track.put("victory", audioVictory);
            dict_track.put("lose", audioLose);

            clip = AudioSystem.getClip();
        }
        catch (UnsupportedAudioFileException e2){
            System.err.println("UnsupportedAudioFileException : "+ e2.getMessage());
            System.exit(1);
        }
        catch (IOException e3){
            System.err.println("IOException : "+ e3.getMessage());
            System.exit(1);
        }
        catch (LineUnavailableException e4){
            System.err.println("LineUnavailableException : "+ e4.getMessage());
            System.exit(1);
        }
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    /**
     * Set volume power between 0 and 1
     * @param volume
     */
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }


    /**
     * Start a song and how many times you want to play it (times can be LOOP)
     * 0 = intro
     * 1 = game1
     * 2 = victory
     * 3 = lose
     */
    public void startSong(String name_song, int times) throws LineUnavailableException, IOException {
        clip.open(dict_track.get(name_song));
        if(times == LOOP){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        else{
            clip.loop(times-1);
        }
        setVolume((float) volume_power);
    }

    /**
     * Change song while another is running
     * @param name_song
     * @param times
     * @throws LineUnavailableException
     * @throws IOException
     */
    public void changeSong(String name_song, int times) throws LineUnavailableException, IOException {
        closeSong();
        clip.open(dict_track.get(name_song));
        if(times == LOOP){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        else{
            clip.loop(times-1);
        }
        setVolume((float) 0.5);
    }
    /**
     * Reset a song at 0 sec
     */
    public void resetSong(){
        clip.setMicrosecondPosition(0);
    }

    /**
     * Close a song
     */
    public void closeSong(){
        clip.close();
    }
}
