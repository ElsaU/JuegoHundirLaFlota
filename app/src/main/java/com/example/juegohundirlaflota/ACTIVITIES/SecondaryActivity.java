package com.example.juegohundirlaflota.ACTIVITIES;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.juegohundirlaflota.R;

//VENTANA DE OPCIONES DEL JUEGO
public class SecondaryActivity extends AppCompatActivity {
    MediaPlayer sonidoBoton;
    Button boton1J;
    Button boton2J;
    Boolean vs2J = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        sonidoBoton = MediaPlayer.create(this, R.raw.sonido_boton);

        boton1J = (Button) findViewById(R.id.boton1Jugador);
        boton2J = (Button) findViewById(R.id.boton2Jugadores);
    }

    //Botón modo de juego 1 JUGADOR.
    public void juegoVSmaquina(View view){
        sonidoBoton.start();

        vs2J = false;
        Intent i = new Intent(this, ThirdActivity.class);
        i.putExtra("vs2J", vs2J);
        startActivity(i);
    }

    //Botón modo de juego 2 JUGADORES.
    public void juegoVSjugador2(View view){
        sonidoBoton.start();

        vs2J = true;
        Intent i = new Intent(this, ThirdActivity.class);
        i.putExtra("vs2J", vs2J);
        startActivity(i);
    }

}