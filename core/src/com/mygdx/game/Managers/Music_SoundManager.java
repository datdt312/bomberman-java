package com.mygdx.game.Managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.audio.*;
public class Music_SoundManager implements Disposable{

    private static final Music_SoundManager instance = new Music_SoundManager();
    private final AssetManager assetManager;

    private String musicPath = "core/Music/";
    private String soundPath = "core/sound/soundchecked/";
    private String currentMusic = "";

    public Music_SoundManager()
    {
        assetManager = new AssetManager();
        String[] songs = {musicPath + "BAAM.ogg", musicPath + "BADBOY.ogg", musicPath + "DDU.ogg", musicPath + "DRA.ogg",
                musicPath + "SOY.ogg", musicPath + "WARRIORS.mp3", musicPath + "WTF.ogg", musicPath + "victory.mp3", musicPath + "KDA.mp3"};

        String[] sounds = {soundPath + "menuselect.wav", soundPath + "selectchoice.wav", soundPath + "comeportal.mp3", soundPath + "pickitem.wav"
                , soundPath + "setBomb.mp3", soundPath + "Explosion.ogg", soundPath + "EnemyDie.ogg", soundPath + "Pause.ogg"
                , soundPath + "playerdie.mp3", soundPath + "gameover.mp3"};

        //load sound
        for(int i = 0; i < sounds.length; i++)
            assetManager.load(sounds[i], Sound.class);


        //load Music
        for(int i =0; i < songs.length; i++)
            assetManager.load(songs[i], Music.class);

        assetManager.finishLoading();
    }

    public static Music_SoundManager getInstance() {return instance;}

    public AssetManager getAssetManager() {return assetManager;}

    public void playSound(String name)
    {
        playSound(name, 1f, 1f, 0f);
    }

    public void playSound(String soundName, float volume, float pitch, float pan) {
        Sound sound = assetManager.get(soundPath + soundName, Sound.class);
        sound.play(volume, pitch, pan);
    }

    public void playMusic(String name, boolean isloop) {
        Music Music  = assetManager.get(musicPath + name);
        Music.setVolume(0.4f);
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

    public void playMusic() {
        if (currentMusic.isEmpty()) {
            return;
        }
        Music music = assetManager.get(musicPath + currentMusic, Music.class);
        music.play();
    }

    public void stopMusic() {
        if (currentMusic.isEmpty()) {
            return;
        }
        Music Music = assetManager.get(musicPath + currentMusic, Music.class);
        if (Music.isPlaying()) {
            Music.stop();
        }
    }
    public void pauseMusic() {
        if (currentMusic.isEmpty()) {
            return;
        }

        Music music = assetManager.get(musicPath + currentMusic, Music.class);
        if (music.isPlaying()) {
            music.pause();
        }
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
