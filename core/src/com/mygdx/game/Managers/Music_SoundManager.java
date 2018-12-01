/**
 * Manage listsongs, listsounds
 * @author HNDBP
 * @since 2018/11/20
 */
package com.mygdx.game.Managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.audio.*;
public class Music_SoundManager implements Disposable{

    private static final Music_SoundManager instance = new Music_SoundManager();
    private final AssetManager assetManager;

    private String musicPath = "core/Music/";
    private String soundPath = "core/sound/";
    private String currentMusic = "";

    /**
     * Constructor
     */
    public Music_SoundManager()
    {
        assetManager = new AssetManager();
        String[] songs = {musicPath + "BAAM.ogg", musicPath + "BADBOY.ogg", musicPath + "DDU.ogg", musicPath + "DRA.ogg",
                musicPath + "SOY.ogg", musicPath + "WARRIORS.mp3", musicPath + "WTF.ogg", musicPath + "victory.mp3", musicPath + "KDA.mp3"};

        String[] sounds = {soundPath + "menuselect.wav", soundPath + "selectchoice.wav", soundPath + "comeportal.mp3", soundPath + "pickitem.wav"
                , soundPath + "setBomb.mp3", soundPath + "Explosion.ogg", soundPath + "EnemyDie.ogg", soundPath + "Pause.ogg"
                , soundPath + "playerdie.mp3", soundPath + "gameover.mp3", soundPath + "EnemyDie.ogg"};

        //load sound
        for(int i = 0; i < sounds.length; i++)
            assetManager.load(sounds[i], Sound.class);


        //load Music
        for(int i =0; i < songs.length; i++)
            assetManager.load(songs[i], Music.class);

        assetManager.finishLoading();
    }

    /**
     * getter
     * @return param to conduct
     */
    public static Music_SoundManager getInstance() {return instance;}

    /**
     * getter
     * @return assetmaneger
     */
    public AssetManager getAssetManager() {return assetManager;}

    /**
     * Create form of sound
     * @param name name of song
     */
    public void playSound(String name)
    {
        playSound(name, 1f, 1f, 0f);
    }

    /**
     * Start another sound
     * @param soundName name sound
     * @param volume volumn
     * @param pitch speed of sound
     * @param pan repeat
     */
    public void playSound(String soundName, float volume, float pitch, float pan) {
        Sound sound = assetManager.get(soundPath + soundName, Sound.class);
        sound.play(volume, pitch, pan);
    }

    /**
     * Play a music song
     * @param name name of song
     * @param isloop looping or not
     */
    public void playMusic(String name, boolean isloop) {
        Music Music  = assetManager.get(musicPath + name);
        Music.setVolume(0.8f);
        if(currentMusic.equals(name))
        {

            Music.setLooping(isloop);
            if(!Music.isPlaying())
                Music.play();
            return;
        }
        stopMusic();
        Music.setLooping(isloop);
        Music.play();
        currentMusic = name;

    }

    /**
     * Continue playing
     */
    public void playMusic() {
        if (currentMusic.isEmpty()) {
            return;
        }
        Music music = assetManager.get(musicPath + currentMusic, Music.class);
        music.play();
    }

    /**
     * Stop current song
     */
    public void stopMusic() {
        if (currentMusic.isEmpty()) {
            return;
        }
        Music Music = assetManager.get(musicPath + currentMusic, Music.class);
        if (Music.isPlaying()) {
            Music.stop();
        }
    }

    /**
     * Pause current song
     */
    public void pauseMusic() {
        if (currentMusic.isEmpty()) {
            return;
        }

        Music music = assetManager.get(musicPath + currentMusic, Music.class);
        if (music.isPlaying()) {
            music.pause();
        }
    }

    /**
     * Close asset
     */
    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
