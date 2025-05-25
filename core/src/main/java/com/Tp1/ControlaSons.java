package com.Tp1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;


public class ControlaSons implements Disposable{

    private static ControlaSons instancia; //instancia para gerenciar a classe globalmente
    private Music musicaDeFundo;
    private float musicVolume = 0.1f;

    private Sound clickSound;
    private Sound quadradoSound;
    private float soundVolume = 1f;

     private ControlaSons() {
        // Carrega a música
        musicaDeFundo = Gdx.audio.newMusic(Gdx.files.internal("musicadefundo.mp3"));
        musicaDeFundo.setLooping(true);
        musicaDeFundo.setVolume(musicVolume); 

        clickSound = Gdx.audio.newSound(Gdx.files.internal("clickSound.wav"));
        quadradoSound = Gdx.audio.newSound(Gdx.files.internal("quadradoSound.wav"));
    }
    public static ControlaSons getInstance() {
        if (instancia == null) {
            instancia = new ControlaSons();
        }
        return instancia;
    }

    public void playMusic() {
        if (musicaDeFundo != null && !musicaDeFundo.isPlaying()) {
            musicaDeFundo.play();
        }
    }

    public void playClickSound() {
        if (clickSound != null) {
            clickSound.play(soundVolume); 
        }
    }
    public void playQuadradoSound() {
        if (quadradoSound != null) {
            quadradoSound.play(soundVolume); 
        }
    }

    @Override
    public void dispose() {
        // Libera a música
        if (musicaDeFundo != null) {
            musicaDeFundo.dispose();
            musicaDeFundo = null; // limpa a referencia
        }
        // Libera os sons
        if (clickSound != null) {
            clickSound.dispose();
            clickSound = null; 
        }
        if (quadradoSound!= null) {
            quadradoSound.dispose();
            quadradoSound = null; 
        }
    }

}
