package com.example.juegohundirlaflota.ACTIVITIES;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.juegohundirlaflota.BD_RANKING.BDHelper;
import com.example.juegohundirlaflota.R;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    //Variables para los barcos de los jugadores y de la máquina
    ArrayList<Integer> barco1_1;
    ArrayList<Integer> barco2_1;
    ArrayList<Integer> barco3_1;
    ArrayList<Integer> barco4_1;
    ArrayList<Integer> barco5_1;
    ArrayList<Integer> barco1_2;
    ArrayList<Integer> barco2_2;
    ArrayList<Integer> barco3_2;
    ArrayList<Integer> barco4_2;
    ArrayList<Integer> barco5_2;
    ArrayList<Integer> barco1M;
    ArrayList<Integer> barco2M;
    ArrayList<Integer> barco3M;
    ArrayList<Integer> barco4M;
    ArrayList<Integer> barco5M;
    Boolean vs2J;

    //Variables para guardar los barcos colocados, las posiciones tocadas y las posiciones de agua.
    ArrayList<ArrayList<Integer>> listaBarcosJugador1;
    ArrayList<ArrayList<Integer>> listaBarcosJugador2;
    ArrayList<ArrayList<Integer>> listaBarcosMaquina;
    ArrayList<Integer> posicionesTocadoJ1;
    ArrayList<Integer> posicionesAguaJ1;
    ArrayList<ArrayList<Integer>> posicionesHundidoJ1;
    ArrayList<Integer> posicionesTocadoJ2;
    ArrayList<Integer> posicionesAguaJ2;
    ArrayList<ArrayList<Integer>> posicionesHundidoJ2;
    ArrayList<Integer> posicionesTocadoM;
    ArrayList<Integer> posicionesAguaM;
    ArrayList<ArrayList<Integer>> posicionesHundidoM;
    ArrayList<Integer> posicionesAtacadasM;

    TextView textoTitulo;
    TextView textoInfo;
    Button botonEmpezar;
    GridLayout gridLayout;
    TextView etiqTurno;
    TextView textPuntosJ1;
    TextView textPuntosJ2;
    TextView etiqPuntos;
    ConstraintLayout layoutJ1;
    ConstraintLayout layoutJ2;

    int numFilas = 0;
    int numColumnas = 0;

    int turno = 1;
    Boolean correcto;

    //Variables para controlar si los barcos están hundidos.
    int hundido1_2 = 0;
    int hundido2_2 = 0;
    int hundido3_2 = 0;
    int hundido4_2 = 0;
    int hundido5_2 = 0;
    int hundido1_1 = 0;
    int hundido2_1 = 0;
    int hundido3_1 = 0;
    int hundido4_1 = 0;
    int hundido5_1 = 0;
    int hundido1M = 0;
    int hundido2M = 0;
    int hundido3M = 0;
    int hundido4M = 0;
    int hundido5M = 0;

    //Variables para el fin del juego y la suma de puntos.
    int barcosHundidosJ2 = 0;
    int puntosJ1 = 0;
    int aciertosSeguidosJ1 = 1;
    int barcosHundidosJ1 = 0;
    int puntosJ2 = 0;
    int aciertosSeguidosJ2 = 1;

    int aleatorio = 0;

    MediaPlayer sonidoExplosion;
    MediaPlayer sonidoSilbido;
    MediaPlayer sonidoBoton;
    MediaPlayer sonidoAgua;
    MediaPlayer sonidoHundido;

    Drawable drawableBarcoTocado = null;
    Drawable drawableAgua = null;
    Drawable drawableTurno = null;
    Drawable drawableNoTurno = null;
    Drawable drawableCuadricula = null;
    Drawable drawableHundido = null;

    ImageView b1j1;
    ImageView b2j1;
    ImageView b3j1;
    ImageView b4j1;
    ImageView b5j1;
    ImageView b1j2;
    ImageView b2j2;
    ImageView b3j2;
    ImageView b4j2;
    ImageView b5j2;

    ImageView b1j1r;
    ImageView b2j1r;
    ImageView b3j1r;
    ImageView b4j1r;
    ImageView b5j1r;
    ImageView b1j2r;
    ImageView b2j2r;
    ImageView b3j2r;
    ImageView b4j2r;
    ImageView b5j2r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sonidoExplosion = MediaPlayer.create(this, R.raw.explosion);
        sonidoSilbido = MediaPlayer.create(this, R.raw.silbido);
        sonidoBoton = MediaPlayer.create(this, R.raw.sonido_boton);
        sonidoAgua = MediaPlayer.create(this, R.raw.agua);
        sonidoHundido = MediaPlayer.create(this, R.raw.explosion_hundido);

        drawableBarcoTocado = ResourcesCompat.getDrawable(getResources(), R.drawable.color_barco_tocado, null);
        drawableAgua = ResourcesCompat.getDrawable(getResources(), R.drawable.color_agua, null);
        drawableTurno = ResourcesCompat.getDrawable(getResources(), R.drawable.color_turno, null);
        drawableNoTurno = ResourcesCompat.getDrawable(getResources(), R.drawable.borde_redondo, null);
        drawableCuadricula = ResourcesCompat.getDrawable(getResources(), R.drawable.color_cuadricula, null);
        drawableHundido = ResourcesCompat.getDrawable(getResources(), R.drawable.color_barco_hundido, null);

        b1j1 = (ImageView) findViewById(R.id.imgB1J1);
        b2j1 = (ImageView) findViewById(R.id.imgB2J1);
        b3j1 = (ImageView) findViewById(R.id.imgB3J1);
        b4j1 = (ImageView) findViewById(R.id.imgB4J1);
        b5j1 = (ImageView) findViewById(R.id.imgB5J1);
        b1j2 = (ImageView) findViewById(R.id.imgB1J2);
        b2j2 = (ImageView) findViewById(R.id.imgB2J2);
        b3j2 = (ImageView) findViewById(R.id.imgB3J2);
        b4j2 = (ImageView) findViewById(R.id.imgB4J2);
        b5j2 = (ImageView) findViewById(R.id.imgB5J2);

        b1j1r = (ImageView) findViewById(R.id.imgB1J1R);
        b2j1r = (ImageView) findViewById(R.id.imgB2J1R);
        b3j1r = (ImageView) findViewById(R.id.imgB3J1R);
        b4j1r = (ImageView) findViewById(R.id.imgB4J1R);
        b5j1r = (ImageView) findViewById(R.id.imgB5J1R);
        b1j2r = (ImageView) findViewById(R.id.imgB1J2R);
        b2j2r = (ImageView) findViewById(R.id.imgB2J2R);
        b3j2r = (ImageView) findViewById(R.id.imgB3J2R);
        b4j2r = (ImageView) findViewById(R.id.imgB4J2R);
        b5j2r = (ImageView) findViewById(R.id.imgB5J2R);

        listaBarcosJugador1 = new ArrayList<>();
        listaBarcosJugador2 = new ArrayList<>();
        listaBarcosMaquina = new ArrayList<>();
        posicionesTocadoJ1 = new ArrayList<>();
        posicionesAguaJ1 = new ArrayList<>();
        posicionesHundidoJ1 = new ArrayList<>();
        posicionesTocadoJ2 = new ArrayList<>();
        posicionesAguaJ2 = new ArrayList<>();
        posicionesHundidoJ2 = new ArrayList<>();
        posicionesTocadoM = new ArrayList<>();
        posicionesAguaM = new ArrayList<>();
        posicionesHundidoM = new ArrayList<>();
        posicionesAtacadasM = new ArrayList<>();

        vs2J = getIntent().getBooleanExtra("vs2J", false);

        //Barcos del jugador 1.
        barco1_1 = getIntent().getIntegerArrayListExtra("barco1_1");
        barco2_1 = getIntent().getIntegerArrayListExtra("barco2_1");
        barco3_1 = getIntent().getIntegerArrayListExtra("barco3_1");
        barco4_1 = getIntent().getIntegerArrayListExtra("barco4_1");
        barco5_1 = getIntent().getIntegerArrayListExtra("barco5_1");

        listaBarcosJugador1.add(barco1_1);
        listaBarcosJugador1.add(barco2_1);
        listaBarcosJugador1.add(barco3_1);
        listaBarcosJugador1.add(barco4_1);
        listaBarcosJugador1.add(barco5_1);

        if (vs2J){
            //Barcos del jugador 2.
            barco1_2 = getIntent().getIntegerArrayListExtra("barco1_2");
            barco2_2 = getIntent().getIntegerArrayListExtra("barco2_2");
            barco3_2 = getIntent().getIntegerArrayListExtra("barco3_2");
            barco4_2 = getIntent().getIntegerArrayListExtra("barco4_2");
            barco5_2 = getIntent().getIntegerArrayListExtra("barco5_2");

            listaBarcosJugador2.add(barco1_2);
            listaBarcosJugador2.add(barco2_2);
            listaBarcosJugador2.add(barco3_2);
            listaBarcosJugador2.add(barco4_2);
            listaBarcosJugador2.add(barco5_2);
        }else{
            //Barcos de la máquina.
            barco1M = getIntent().getIntegerArrayListExtra("barco1M");
            barco2M = getIntent().getIntegerArrayListExtra("barco2M");
            barco3M = getIntent().getIntegerArrayListExtra("barco3M");
            barco4M = getIntent().getIntegerArrayListExtra("barco4M");
            barco5M = getIntent().getIntegerArrayListExtra("barco5M");

            listaBarcosMaquina.add(barco1M);
            listaBarcosMaquina.add(barco2M);
            listaBarcosMaquina.add(barco3M);
            listaBarcosMaquina.add(barco4M);
            listaBarcosMaquina.add(barco5M);
        }

        botonEmpezar = (Button) findViewById(R.id.botonEmpezar);

        posicionesTocadoJ1 = new ArrayList<>();
        posicionesAguaJ1 = new ArrayList<>();
        posicionesTocadoJ2 = new ArrayList<>();
        posicionesAguaJ2 = new ArrayList<>();

        textoTitulo = (TextView) findViewById(R.id.textoTitulo);
        textoInfo = (TextView) findViewById(R.id.textoInfoPuntos);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        etiqTurno = (TextView) findViewById(R.id.textTurno);
        textPuntosJ1 = (TextView) findViewById(R.id.textPuntosJ1);
        textPuntosJ2 = (TextView) findViewById(R.id.textPuntosJ2);
        etiqPuntos = (TextView) findViewById(R.id.textP);
        layoutJ1 = (ConstraintLayout) findViewById(R.id.layoutJ1);
        layoutJ2 = (ConstraintLayout) findViewById(R.id.layoutJ2);

        numFilas = 11;
        numColumnas = 11;
        gridLayout.setRowCount(numFilas);
        gridLayout.setColumnCount(numColumnas);

        //Texto informativo
        if (vs2J){
            textoTitulo.setText(R.string.etiq_vs2);
            textoInfo.setText("  PUNTUACIONES:\n   - Acierto: +100 x número de aciertos seguidos\n   - Fallo: -15\n\n  Recuerda que los barcos pueden estar juntos.\n\n  ¡BUENA SUERTE!\n");
        }else{
            textoTitulo.setText(R.string.etiq_vsM);
            textoInfo.setText("  PUNTUACIONES:\n   - Acierto: +100 x número de aciertos seguidos.\n   - Fallo: -15\n\n  La IA no tiene puntos.\n\n  Recuerda que los barcos pueden estar juntos.\n\n  ¡BUENA SUERTE!");
        }
    }

    //onClick del botón 'empezar'.
    public void empezar(View view){
        sonidoBoton.start();

        colocarTablero(posicionesTocadoJ1, posicionesAguaJ1, posicionesHundidoJ1);
        textoTitulo.setVisibility(View.INVISIBLE);
        textoInfo.setVisibility(View.INVISIBLE);
        botonEmpezar.setVisibility(View.INVISIBLE);
        layoutJ1.setVisibility(View.VISIBLE);
        layoutJ2.setVisibility(View.VISIBLE);

        layoutJ1.setBackground(drawableTurno);

        if (!vs2J){
            etiqTurno.setText("IA");
            textPuntosJ2.setVisibility(View.INVISIBLE);
            etiqPuntos.setVisibility(View.INVISIBLE);
        }
    }

    //Coloca el tablero para cada jugador, dependiendo de las posiciones ya atacadas y los barcos que haya hundido.
    public void colocarTablero(ArrayList<Integer> posicionesTocado, ArrayList<Integer> posicionesAgua, ArrayList<ArrayList<Integer>> posicionesHundido){
        numColumnas = gridLayout.getColumnCount();
        numFilas = gridLayout.getRowCount();

        String[] letras = {"A","B","C","D","E","F","G","H","I","J"};
        Button button;

        int idBotonNoContable = -1;
        int contadorID = 1;
        for (int i=0; i<numFilas; i++){
            for(int j=0; j<numColumnas; j++){
                button = new Button(this);
                button.setLayoutParams(new ViewGroup.LayoutParams(gridLayout.getWidth() / numColumnas, gridLayout.getHeight() /numFilas));
                button.setTextSize(8);
                button.setTextColor(Color.WHITE);

                if (i==0 && j==0){
                    button.setVisibility(View.INVISIBLE);
                }else if (i==0){
                    button.setEnabled(false);
                    button.setText(letras[j-1]);
                    button.setId(idBotonNoContable);
                    button.setBackgroundColor(parseColor("#9F3c3c3c"));
                }else if (j==0){
                    button.setEnabled(false);
                    button.setText(""+i);
                    button.setId(idBotonNoContable);
                    button.setBackgroundColor(parseColor("#9F3c3c3c"));
                }else{
                    button.setId(contadorID);
                    button.setBackground(drawableCuadricula);
                    button.setOnClickListener(this);

                    for (int x=0; x<posicionesTocado.size(); x++){
                        if (posicionesTocado.get(x) == contadorID){
                            button.setBackground(drawableBarcoTocado);
                            button.setEnabled(false);
                        }
                    }
                    for (int x=0; x<posicionesAgua.size(); x++){
                        if (posicionesAgua.get(x) == contadorID){
                            button.setBackground(drawableAgua);
                            button.setEnabled(false);
                        }
                    }
                    for (int x=0; x<posicionesHundido.size(); x++){
                        for (int z=0; z<posicionesHundido.get(x).size(); z++){
                            if (posicionesHundido.get(x).get(z) == contadorID){
                                button.setBackground(drawableHundido);
                                button.setEnabled(false);
                            }
                        }
                    }
                    contadorID++;
                }
                gridLayout.addView(button);
            }
        }
    }

    //onClick de cada posición. Dependiendo del modo de juego y el turno en el que esté, llamará a un método o a otro.
    public void onClick(View view) {
        if (turno == 1){
            if (vs2J){
                turnoJ1(view);
            }else{
                turnoJ1M(view);
            }
        }else if (turno == 2){
            turnoJ2(view);
        }
    }

    //Turno del jugador 1 en el modo de 2 JUGADORES.
    public void turnoJ1(View view){
        correcto = false;
        quitarListener();
        sonidoSilbido.start();
        while (sonidoSilbido.isPlaying()){

        }

        for (int i=0; i<listaBarcosJugador2.size(); i++){
            for (int j=0; j<(listaBarcosJugador2.get(i)).size(); j++){
                //Si se acierta.
                if (view.getId() == (listaBarcosJugador2.get(i)).get(j)){
                    view.setEnabled(false);
                    correcto = true;
                    posicionesTocadoJ1.add(view.getId());

                    sonidoExplosion.start();
                    view.setBackground(drawableBarcoTocado);
                    devolverListener(posicionesTocadoJ1, posicionesAguaJ1);

                    puntosJ1 = puntosJ1 + (100 * aciertosSeguidosJ1);
                    aciertosSeguidosJ1++;
                    textPuntosJ1.setText(String.valueOf(puntosJ1));

                    //El switch controla si el barco se hunde.
                    switch (i){
                        case 0:
                            hundido1_2++;
                            if (hundido1_2 == 2){
                                barcosHundidosJ2++;
                                hundirBarco(0, listaBarcosJugador2);
                                posicionesHundidoJ1.add(barco1_2);
                                b1j1.setVisibility(View.INVISIBLE);
                                b1j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 1:
                            hundido2_2++;
                            if (hundido2_2 == 3){
                                barcosHundidosJ2++;
                                hundirBarco(1, listaBarcosJugador2);
                                posicionesHundidoJ1.add(barco2_2);
                                b2j1.setVisibility(View.INVISIBLE);
                                b2j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 2:
                            hundido3_2++;
                            if (hundido3_2 == 3){
                                barcosHundidosJ2++;
                                hundirBarco(2, listaBarcosJugador2);
                                posicionesHundidoJ1.add(barco3_2);
                                b3j1.setVisibility(View.INVISIBLE);
                                b3j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 3:
                            hundido4_2++;
                            if (hundido4_2 == 4){
                                barcosHundidosJ2++;
                                hundirBarco(3, listaBarcosJugador2);
                                posicionesHundidoJ1.add(barco4_2);
                                b4j1.setVisibility(View.INVISIBLE);
                                b4j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 4:
                            hundido5_2++;
                            if (hundido5_2 == 5){
                                barcosHundidosJ2++;
                                hundirBarco(4, listaBarcosJugador2);
                                posicionesHundidoJ1.add(barco5_2);
                                b5j1.setVisibility(View.INVISIBLE);
                                b5j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                    }
                    //Si los 5 barcos se han hundido: fin del juego.
                    if (barcosHundidosJ2 == 5){
                        final Intent intent = new Intent(this, RankingActivity.class);
                        final BDHelper bd = new BDHelper(this);

                        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        final Context context = alert.getContext();
                        final LayoutInflater inflater = LayoutInflater.from(context);
                        final View v = inflater.inflate(R.layout.finjuego_layout, null, false);
                        final EditText nombreJugador = (EditText) v.findViewById(R.id.editTextNombre);
                        final TextView ganador = (TextView) v.findViewById(R.id.textGanador);
                        ganador.setText(R.string.etiq_turnoJ1);

                        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nombre = nombreJugador.getText().toString();
                                bd.insertJugador(nombre, puntosJ1);

                                startActivity(intent);
                                finish();
                            }
                        };

                        alert
                                .setView(v)
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) listener);
                        alert.show();
                    }
                }
            }
        }
        //Si se falla
        if (!correcto){
            view.setEnabled(false);
            sonidoAgua.start();
            view.setBackground(drawableAgua);
            posicionesAguaJ1.add(view.getId());

            if (puntosJ1 != 0)
                puntosJ1 = puntosJ1 - 15;
            aciertosSeguidosJ1 = 1;
            textPuntosJ1.setText(String.valueOf(puntosJ1));


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    gridLayout.removeAllViews();
                    layoutJ2.setBackground(drawableTurno);
                    layoutJ1.setBackground(drawableNoTurno);

                    turno = 2;
                    colocarTablero(posicionesTocadoJ2, posicionesAguaJ2, posicionesHundidoJ2);
                }
            }, 1500);
        }
    }

    //Turno del jugador 2 en el modo 2 JUGADORES
    public void turnoJ2(View view){
        correcto = false;
        quitarListener();
        sonidoSilbido.start();
        while (sonidoSilbido.isPlaying()){

        }

        for (int i=0; i<listaBarcosJugador1.size(); i++){
            for (int j=0; j<(listaBarcosJugador1.get(i)).size(); j++){
                //Si se acierta.
                if (view.getId() == (listaBarcosJugador1.get(i)).get(j)){
                    view.setEnabled(false);
                    correcto = true;
                    posicionesTocadoJ2.add(view.getId());

                    sonidoExplosion.start();
                    view.setBackground(drawableBarcoTocado);
                    devolverListener(posicionesTocadoJ2, posicionesAguaJ2);

                    puntosJ2 = puntosJ2 + (100 * aciertosSeguidosJ2);
                    aciertosSeguidosJ2++;
                    textPuntosJ2.setText(String.valueOf(puntosJ2));

                    //El switch controla si el barco se hunde
                    switch (i){
                        case 0:
                            hundido1_1++;
                            if (hundido1_1 == 2){
                                barcosHundidosJ1++;
                                hundirBarco(0, listaBarcosJugador1);
                                posicionesHundidoJ2.add(barco1_1);
                                b1j2.setVisibility(View.INVISIBLE);
                                b1j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 1:
                            hundido2_1++;
                            if (hundido2_1 == 3){
                                barcosHundidosJ1++;
                                hundirBarco(1, listaBarcosJugador1);
                                posicionesHundidoJ2.add(barco2_1);
                                b2j2.setVisibility(View.INVISIBLE);
                                b2j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 2:
                            hundido3_1++;
                            if (hundido3_1 == 3){
                                barcosHundidosJ1++;
                                hundirBarco(2, listaBarcosJugador1);
                                posicionesHundidoJ2.add(barco3_1);
                                b3j2.setVisibility(View.INVISIBLE);
                                b3j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 3:
                            hundido4_1++;
                            if (hundido4_1 == 4){
                                barcosHundidosJ1++;
                                hundirBarco(3, listaBarcosJugador1);
                                posicionesHundidoJ2.add(barco4_1);
                                b4j2.setVisibility(View.INVISIBLE);
                                b4j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 4:
                            hundido5_1++;
                            if (hundido5_1 == 5){
                                barcosHundidosJ1++;
                                hundirBarco(4, listaBarcosJugador1);
                                posicionesHundidoJ2.add(barco5_1);
                                b5j2.setVisibility(View.INVISIBLE);
                                b5j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                    }
                    //Si los 5 barcos se han hundido: fin del juego.
                    if (barcosHundidosJ1 == 5){
                        final Intent intent = new Intent(this, RankingActivity.class);
                        final BDHelper bd = new BDHelper(this);

                        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        final Context context = alert.getContext();
                        final LayoutInflater inflater = LayoutInflater.from(context);
                        final View v = inflater.inflate(R.layout.finjuego_layout, null, false);

                        final EditText nombreJugador = (EditText) v.findViewById(R.id.editTextNombre);
                        final TextView ganador = (TextView) v.findViewById(R.id.textGanador);
                        ganador.setText(R.string.etiq_turnoJ2);

                        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nombre = nombreJugador.getText().toString();
                                bd.insertJugador(nombre, puntosJ2);

                                startActivity(intent);
                                finish();
                            }
                        };

                        alert
                                .setView(v)
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) listener);
                        alert.show();
                    }
                }
            }
        }
        //Si se falla.
        if (!correcto){
            view.setEnabled(false);
            sonidoAgua.start();
            view.setBackground(drawableAgua);
            posicionesAguaJ2.add(view.getId());

            if (puntosJ2 != 0)
                puntosJ2 = puntosJ2 - 15;
            aciertosSeguidosJ2 = 1;
            textPuntosJ2.setText(String.valueOf(puntosJ2));

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    gridLayout.removeAllViews();
                    layoutJ1.setBackground(drawableTurno);
                    layoutJ2.setBackground(drawableNoTurno);

                    turno = 1;
                    colocarTablero(posicionesTocadoJ1, posicionesAguaJ1, posicionesHundidoJ1);
                }
            }, 1500);
        }
    }

    //Turno del jugador 1 en el modo 1 JUGADOR.
    public void turnoJ1M(View view){
        correcto = false;
        quitarListener();
        sonidoSilbido.start();
        while (sonidoSilbido.isPlaying()){

        }

        for (int i=0; i<listaBarcosMaquina.size(); i++){
            for (int j=0; j<(listaBarcosMaquina.get(i)).size(); j++){
                //Si se acierta.
                if (view.getId() == (listaBarcosMaquina.get(i)).get(j)){
                    view.setEnabled(false);
                    correcto = true;
                    posicionesTocadoJ1.add(view.getId());

                    while (sonidoSilbido.isPlaying()){
                        quitarListener();
                    }
                    sonidoExplosion.start();
                    view.setBackground(drawableBarcoTocado);
                    devolverListener(posicionesTocadoJ1, posicionesAguaJ1);

                    puntosJ1 = puntosJ1 + (100 * aciertosSeguidosJ1);
                    aciertosSeguidosJ1++;
                    textPuntosJ1.setText(String.valueOf(puntosJ1));

                    //El switch controla si se hunde el barco.
                    switch (i){
                        case 0:
                            hundido1_2++;
                            if (hundido1_2 == 2){
                                barcosHundidosJ2++;
                                hundirBarco(0, listaBarcosMaquina);
                                posicionesHundidoJ1.add(barco1M);
                                b1j1.setVisibility(View.INVISIBLE);
                                b1j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 1:
                            hundido2_2++;
                            if (hundido2_2 == 3){
                                barcosHundidosJ2++;
                                hundirBarco(1, listaBarcosMaquina);
                                posicionesHundidoJ1.add(barco2M);
                                b2j1.setVisibility(View.INVISIBLE);
                                b2j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 2:
                            hundido3_2++;
                            if (hundido3_2 == 3){
                                barcosHundidosJ2++;
                                hundirBarco(2, listaBarcosMaquina);
                                posicionesHundidoJ1.add(barco3M);
                                b3j1.setVisibility(View.INVISIBLE);
                                b3j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 3:
                            hundido4_2++;
                            if (hundido4_2 == 4){
                                barcosHundidosJ2++;
                                hundirBarco(3, listaBarcosMaquina);
                                posicionesHundidoJ1.add(barco4M);
                                b4j1.setVisibility(View.INVISIBLE);
                                b4j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 4:
                            hundido5_2++;
                            if (hundido5_2 == 5){
                                barcosHundidosJ2++;
                                hundirBarco(4, listaBarcosMaquina);
                                posicionesHundidoJ1.add(barco5M);
                                b5j1.setVisibility(View.INVISIBLE);
                                b5j1r.setVisibility(View.VISIBLE);
                            }
                            break;
                    }
                    //Si los 5 barcos se han hundido: fin del juego.
                    if (barcosHundidosJ2 == 5){
                        final Intent intent = new Intent(this, RankingActivity.class);
                        final BDHelper bd = new BDHelper(this);

                        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        final Context context = alert.getContext();
                        final LayoutInflater inflater = LayoutInflater.from(context);
                        final View v = inflater.inflate(R.layout.finjuego_layout, null, false);
                        final EditText nombreJugador = (EditText) v.findViewById(R.id.editTextNombre);
                        final TextView ganador = (TextView) v.findViewById(R.id.textGanador);
                        ganador.setText(R.string.etiq_turnoJ1);

                        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nombre = nombreJugador.getText().toString();
                                bd.insertJugador(nombre, puntosJ1);

                                startActivity(intent);
                                finish();
                            }
                        };

                        alert
                                .setView(v)
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) listener);
                        alert.show();
                    }
                }
            }
        }
        //Si se falla.
        if (!correcto){
            view.setEnabled(false);
            sonidoAgua.start();
            view.setBackground(drawableAgua);
            posicionesAguaJ1.add(view.getId());

            if (puntosJ1 != 0)
                puntosJ1 = puntosJ1 - 15;
            aciertosSeguidosJ1 = 1;
            textPuntosJ1.setText(String.valueOf(puntosJ1));

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    gridLayout.removeAllViews();

                    layoutJ2.setBackground(drawableTurno);
                    layoutJ1.setBackground(drawableNoTurno);

                    turno = 3;
                    colocarTablero(posicionesTocadoM, posicionesAguaM, posicionesHundidoM);
                }
            }, 1500);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    turnoMaquina();
                }
            }, 2000);
        }
    }

    //Turno de la máquina en el modo 1 JUGADOR.
    public void turnoMaquina(){
        correcto = false;
        do{
            aleatorio = (int) (Math.random()*100)+1;
        }while(posicionesAtacadasM.contains(aleatorio));
        posicionesAtacadasM.add(aleatorio);
        sonidoSilbido.start();
        while (sonidoSilbido.isPlaying()){

        }

        for (int i=0; i<listaBarcosJugador1.size(); i++) {
            for (int j = 0; j < (listaBarcosJugador1.get(i)).size(); j++) {
                //Si acierta.
                if (aleatorio == listaBarcosJugador1.get(i).get(j)) {
                    correcto = true;
                    posicionesTocadoM.add(aleatorio);

                    sonidoExplosion.start();
                    colorearTableroMaquina(aleatorio, drawableBarcoTocado);

                    //El switch controla si el barco se hunde.
                    switch (i){
                        case 0:
                            hundido1M++;
                            if (hundido1M == 2){
                                barcosHundidosJ1++;
                                hundirBarco(0, listaBarcosJugador1);
                                posicionesHundidoM.add(barco1_1);
                                b1j2.setVisibility(View.INVISIBLE);
                                b1j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 1:
                            hundido2M++;
                            if (hundido2M == 3){
                                barcosHundidosJ1++;
                                hundirBarco(1, listaBarcosJugador1);
                                posicionesHundidoM.add(barco2_1);
                                b2j2.setVisibility(View.INVISIBLE);
                                b2j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 2:
                            hundido3M++;
                            if (hundido3M == 3){
                                barcosHundidosJ1++;
                                hundirBarco(2, listaBarcosJugador1);
                                posicionesHundidoM.add(barco3_1);
                                b3j2.setVisibility(View.INVISIBLE);
                                b3j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 3:
                            hundido4M++;
                            if (hundido4M == 4){
                                barcosHundidosJ1++;
                                hundirBarco(3, listaBarcosJugador1);
                                posicionesHundidoM.add(barco4_1);
                                b4j2.setVisibility(View.INVISIBLE);
                                b4j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 4:
                            hundido5M++;
                            if (hundido5M == 5){
                                barcosHundidosJ1++;
                                hundirBarco(4, listaBarcosJugador1);
                                posicionesHundidoM.add(barco5_1);
                                b5j2.setVisibility(View.INVISIBLE);
                                b5j2r.setVisibility(View.VISIBLE);
                            }
                            break;
                    }
                    //Si los 5 barcos se han hundido: fin del juego.
                    if (barcosHundidosJ1 == 5){
                        finJuegoMaquina();
                    }else{
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                turnoMaquina();
                            }
                        }, 500);
                    }
                }
            }
        }
        //Si falla.
        if (!correcto){
            sonidoAgua.start();
            colorearTableroMaquina(aleatorio, drawableAgua);
            posicionesAguaM.add(aleatorio);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    gridLayout.removeAllViews();

                    layoutJ1.setBackground(drawableTurno);
                    layoutJ2.setBackground(drawableNoTurno);

                    turno = 1;
                    colocarTablero(posicionesTocadoJ1, posicionesAguaJ1,posicionesHundidoJ1);
                }
            }, 1500);
        }
    }

    //Colorea las posiciones del tablero de la máquina de rojo o azul, dependiendo de si acierta o no.
    public void colorearTableroMaquina(int posicion, Drawable color){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                if (view.getId() == posicion){
                    view.setBackground(color);
                }
            }
        }
    }

    //Colorea el barco hundido de negro.
    public void hundirBarco(int numBarco, ArrayList<ArrayList<Integer>> listaBarcos){
        sonidoHundido.start();

        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                for (int j=0; j<listaBarcos.get(numBarco).size(); j++){
                    if (view.getId() == listaBarcos.get(numBarco).get(j)){
                        view.setBackground(drawableHundido);
                    }
                }
            }
        }
    }

    //quita el listener de los botones.
    public void quitarListener(){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                view.setEnabled(false);
            }
        }
    }

    //devuelve el listener de los botones que no se han atacado.
    public void devolverListener(ArrayList<Integer> posicionesTocado, ArrayList<Integer> posicionesAgua){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                if (!posicionesTocado.contains(view.getId()) && !posicionesAgua.contains(view.getId())){
                    view.setEnabled(true);
                }
            }
        }
    }

    //fin del juego si gana la máquina.
    public void finJuegoMaquina(){
        final Intent intent = new Intent(this, RankingActivity.class);
        final BDHelper bd = new BDHelper(this);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Context context = alert.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.finjuego_layout, null, false);

        final EditText nombreJugador = (EditText) v.findViewById(R.id.editTextNombre);
        TextView texto = (EditText) v.findViewById(R.id.editTextNombre);
        final TextView ganador = (TextView) v.findViewById(R.id.textGanador);

        nombreJugador.setVisibility(View.INVISIBLE);
        texto.setVisibility(View.INVISIBLE);
        ganador.setText("IA");

        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(intent);
                finish();
            }
        };

        alert
                .setView(v)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) listener);
        alert.show();
    }
}