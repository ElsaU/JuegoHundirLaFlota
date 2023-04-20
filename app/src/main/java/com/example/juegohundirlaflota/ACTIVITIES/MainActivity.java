package com.example.juegohundirlaflota.ACTIVITIES;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.juegohundirlaflota.R;

public class MainActivity extends AppCompatActivity {
    MediaPlayer sonidoBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sonidoBoton = MediaPlayer.create(this, R.raw.sonido_boton);
    }

    //Botón jugar, lleva a la pantalla de configuración del juego.
    public void jugar(View view){
        sonidoBoton.start();
        Intent i = new Intent(this, SecondaryActivity.class);
        startActivity(i);
    }

    //Botón ranking. Muestra las 15 puntuaciones más altas.
    public void ranking(View view){
        sonidoBoton.start();
        Intent i = new Intent(this, RankingActivity.class);
        startActivity(i);
    }
}