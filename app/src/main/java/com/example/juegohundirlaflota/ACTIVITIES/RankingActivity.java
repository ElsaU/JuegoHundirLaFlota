package com.example.juegohundirlaflota.ACTIVITIES;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.juegohundirlaflota.BD_RANKING.BDHelper;
import com.example.juegohundirlaflota.BD_RANKING.Jugador;
import com.example.juegohundirlaflota.BD_RANKING.MiArrayAdapter;
import com.example.juegohundirlaflota.R;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {
    BDHelper bdhelper;
    MiArrayAdapter arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listView = (ListView) findViewById(R.id.listView);
        bdhelper = new BDHelper(this);

        listar();
    }

    public void volverAtras(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void borrarBD(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Estás seguro?");
        alert.setMessage("¿Seguro que quieres eliminar los datos?");
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bdhelper.borrarLista();
                listar();
            }
        });
        alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    public void listar(){
        //Lista de las 15 puntuaciones más altas de la base de datos.
        ArrayList<Jugador> listaPuntuaciones = bdhelper.listarRanking();

        //Muestra en forma de lista el nombre y los puntos.
        arrayAdapter = new MiArrayAdapter(this, listaPuntuaciones);
        listView.setAdapter(arrayAdapter);
        listView.setEmptyView(findViewById(R.id.emptyListView));
    }
}