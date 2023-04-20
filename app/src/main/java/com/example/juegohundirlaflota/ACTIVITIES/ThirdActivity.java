package com.example.juegohundirlaflota.ACTIVITIES;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.juegohundirlaflota.R;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

//VENTANA DE COLOCACIÓN DE LOS BARCOS
public class ThirdActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener{
    MediaPlayer sonidoBoton;
    MediaPlayer sonidoSeleccionar;
    GridLayout gridLayout;
    Button botonColocarBarcos;
    TextView textoTituloColocar;
    TextView textoInfo;
    Button botonSiguiente;
    Button botonDeshacer;

    //Variables para crear el tablero.
    int numColumnas = 0;
    int numFilas = 0;
    int idBotonNoContable = -1;

    Boolean vs2J = false;

    //Variables para colocar los barcos de los jugadores
    ArrayList<Integer> provisional = new ArrayList<>();
    int num = 0;
    int b3 = 0;
    int maximo = 5;
    int diferencia = 0;
    int x = 0;
    int turno = 1;

    //Variables para colocar los barcos de la máquina
    int numBarco = 0;
    int num1 = 0;
    int orientacion = 0;
    int siguientePosicion;
    int max = 0;

    //Barcos de los jugadores.
    ArrayList<Integer> botonesPulsados;
    ArrayList<Integer> barco2Posiciones;
    ArrayList<Integer> barco3Posiciones;
    ArrayList<Integer> barco3Posiciones2;
    ArrayList<Integer> barco4Posiciones;
    ArrayList<Integer> barco5Posiciones;
    ArrayList<ArrayList<Integer>> listaBarcosJugador1;
    ArrayList<ArrayList<Integer>> listaBarcosJugador2;
    ArrayList<ArrayList<Integer>> listaBarcosMaquina;

    //Barcos de la máquina.
    ArrayList<Integer> barco1M = new ArrayList<>();
    ArrayList<Integer> barco2M = new ArrayList<>();
    ArrayList<Integer> barco3M = new ArrayList<>();
    ArrayList<Integer> barco4M = new ArrayList<>();
    ArrayList<Integer> barco5M = new ArrayList<>();
    ArrayList<Integer> posicionesUsadas = new ArrayList<>();

    Drawable drawableCuadricula = null;
    Drawable drawableBarcoColocado = null;

    //Inicialización de las variables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        sonidoBoton = MediaPlayer.create(this, R.raw.sonido_boton);
        sonidoSeleccionar = MediaPlayer.create(this, R.raw.seleccionar);

        drawableCuadricula = ResourcesCompat.getDrawable(getResources(), R.drawable.color_cuadricula, null);
        drawableBarcoColocado = ResourcesCompat.getDrawable(getResources(), R.drawable.color_barco_colocado, null);

        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        botonColocarBarcos = (Button) findViewById(R.id.botonColocarBarcos);
        textoTituloColocar = (TextView) findViewById(R.id.textoTituloColocar);
        textoInfo = (TextView) findViewById(R.id.textoInfo);
        botonSiguiente = (Button) findViewById(R.id.botonSiguiente2);
        botonDeshacer = (Button) findViewById(R.id.botonDeshacer);

        numFilas = 11;
        numColumnas = 11;
        vs2J = getIntent().getBooleanExtra("vs2J", false);

        gridLayout.setRowCount(numFilas);
        gridLayout.setColumnCount(numColumnas);

        listaBarcosJugador1 = new ArrayList<>();
        listaBarcosJugador2 = new ArrayList<>();
        listaBarcosMaquina = new ArrayList<>();

        //Texto informativo
        if (vs2J){
            textoTituloColocar.setText(R.string.etiq_vs2);
            textoInfo.setText("  INSTRUCCIONES:\n  5 barcos de 2, 3, 3, 4 y 5 posiciones.\n\n  Coloca los barcos de forma vertical y horizontal, nunca en diagonal. Los barcos se pueden tocar.\n");
        }else{
            textoTituloColocar.setText(R.string.etiq_vsM);
            textoInfo.setText("  INSTRUCCIONES:\n  5 barcos de 2, 3, 3, 4 y 5 posiciones.\n\n  Coloca los barcos de forma vertical y horizontal, nunca en diagonal. Los barcos se pueden tocar.\n\n  La máquina ya ha colocado sus barcos.");
            colocarBarcosMaquina();
        }
    }

    //onClick del botón 'colocar barcos'
    public void colocarBarcos(View view){
        sonidoBoton.start();

        textoInfo.setVisibility(View.INVISIBLE);
        botonColocarBarcos.setVisibility(View.INVISIBLE);
        botonSiguiente.setVisibility(View.VISIBLE);
        botonDeshacer.setVisibility(View.VISIBLE);

        colocarTabla();
    }

    //Crea el tablero e inserta los botones con sus propiedades
    public void colocarTabla(){
        botonesPulsados = new ArrayList<>();
        barco2Posiciones = new ArrayList<>();
        barco3Posiciones = new ArrayList<>();
        barco3Posiciones2 = new ArrayList<>();
        barco4Posiciones = new ArrayList<>();
        barco5Posiciones = new ArrayList<>();

        numColumnas = gridLayout.getColumnCount();
        numFilas = gridLayout.getRowCount();

        String[] letras = {"A","B","C","D","E","F","G","H","I","J"};
        Button button;

        int contadorID = 1;
        for (int i=0; i<numFilas; i++){
            for(int j=0; j<numColumnas; j++){
                button = new Button(this);
                button.setLayoutParams(new ViewGroup.LayoutParams(gridLayout.getWidth() / numColumnas, gridLayout.getHeight() /numFilas));
                button.setTextSize(8);
                button.setTextColor(Color.WHITE);
                button.setBackground(drawableCuadricula);

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
                    contadorID++;
                }
                button.setOnTouchListener(this);
                button.setOnDragListener(this);

                gridLayout.addView(button);
            }
        }
        if (turno == 1) {
            textoTituloColocar.setText(R.string.etiq_turnoJ1);
        }else{
            textoTituloColocar.setText(R.string.etiq_turnoJ2);
        }
    }

    //onTouch y onDrag usados para seleccionar la posición de los barcos arrastrando
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        if (barco5Posiciones.size() == 0){
            maximo = 5;
        }else if (barco4Posiciones.size() == 0){
            maximo = 4;
        }else if (barco3Posiciones2.size() == 0){
            maximo = 3;
        }else if (barco2Posiciones.size() == 0){
            maximo = 2;
        }else{
            quitarListenerPosNoGuardadas();
        }

        switch(event.getAction()) {
            //Momento de empezar a arrastrar
            case DragEvent.ACTION_DRAG_STARTED:
                provisional = new ArrayList<>();
                num = 0;
                diferencia = 0;
                x = 0;

                if (barco3Posiciones .size() != 0){
                    b3++;
                }
                break;
            //Momento en el que el arrastre entra en un view
            case DragEvent.ACTION_DRAG_ENTERED:
                sonidoSeleccionar.start();
                if (num < maximo){
                    view.setBackground(drawableBarcoColocado);
                    botonesPulsados.add(view.getId());
                    provisional.add(view.getId());
                    view.setEnabled(false);
                    if (num == 0){
                        asegurarPosicion(0, view.getId());
                    }else if (num == 1) {
                        diferencia = view.getId() - provisional.get(0);
                    }
                    asegurarPosicion(diferencia, view.getId());
                    num++;
                }
                break;
            //Momento en el que se deja de tocar la pantalla
            case DragEvent.ACTION_DRAG_ENDED:
                if (x == 0) {
                    devolverListenerPosNoGuardadas();

                    if (provisional.size() == 1) {
                        borrar(provisional);
                    } else if (provisional.size() == 2) {
                        if (barco2Posiciones.size() == 0) {
                            barco2Posiciones = provisional;
                        } else {
                            borrar(provisional);
                        }
                    } else if (provisional.size() == 3) {
                        if (b3 == 0) {
                            if (barco3Posiciones.size() == 0) {
                                barco3Posiciones = provisional;
                            } else {
                                borrar(provisional);
                            }
                        } else {
                            if (barco3Posiciones2.size() == 0) {
                                barco3Posiciones2 = provisional;
                            } else {
                                borrar(provisional);
                            }
                        }
                    } else if (provisional.size() == 4) {
                        if (barco4Posiciones.size() == 0) {
                            barco4Posiciones = provisional;
                        } else {
                            borrar(provisional);
                        }
                    } else if (provisional.size() == 5) {
                        if (barco5Posiciones.size() == 0) {
                            barco5Posiciones = provisional;
                        } else {
                            borrar(provisional);
                        }
                    }
                    x++;
                }
                break;
        }
        return true;
    }

    //Asegurar que la posición de los barcos es la correcta. Solo pueden estar en horizontal o vertical.
    public void asegurarPosicion(int diferencia, int barcoId){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                if (diferencia == 0){
                    if (view.getId() != (barcoId+1) &&  view.getId() != (barcoId-1) &&  view.getId() != (barcoId+10) &&  view.getId() != (barcoId-10)){
                        view.setEnabled(false);
                    }
                }else if (diferencia == 1){
                    view.setEnabled(false);
                    if (view.getId() == (barcoId+1)){
                        view.setEnabled(true);
                    }
                }else if (diferencia == -1){
                    view.setEnabled(false);
                    if (view.getId() == (barcoId-1)){
                        view.setEnabled(true);
                    }
                }else if (diferencia == 10){
                    view.setEnabled(false);
                    if (view.getId() == (barcoId+10)){
                        view.setEnabled(true);
                    }
                }else if (diferencia == -10){
                    view.setEnabled(false);
                    if (view.getId() == (barcoId-10)){
                        view.setEnabled(true);
                    }
                }
            }
        }
    }

    //Botón siguiente. Pasa el turno al siguiente jugador (si lo hay) o comienza el juego.
    public void siguiente(View view){
        sonidoBoton.start();

        switch (turno){
            case 1:
                if (barco2Posiciones.size() != 0 && barco3Posiciones.size() != 0 && barco3Posiciones2.size() != 0 && barco4Posiciones.size() != 0 && barco5Posiciones.size() != 0){
                    listaBarcosJugador1.add(barco2Posiciones);
                    listaBarcosJugador1.add(barco3Posiciones);
                    listaBarcosJugador1.add(barco3Posiciones2);
                    listaBarcosJugador1.add(barco4Posiciones);
                    listaBarcosJugador1.add(barco5Posiciones);

                    gridLayout.removeAllViews();

                    if (vs2J){
                        AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        alert.setTitle("TURNO DEL JUGADOR 2");
                        alert.setMessage("Jugador 2 te toca colocar los barcos.");
                        alert.setCancelable(false);
                        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                devolverListenerPosGuardadas();
                                devolverListenerPosNoGuardadas();
                                deseleccionarCasillas();
                                b3 = 0;

                                turno++;
                                colocarTabla();
                            }
                        });
                        alert.show();
                    }else{
                        Intent i = new Intent(this, GameActivity.class);
                        i.putIntegerArrayListExtra("barco1_1", barco2Posiciones);
                        i.putIntegerArrayListExtra("barco2_1", barco3Posiciones);
                        i.putIntegerArrayListExtra("barco3_1", barco3Posiciones2);
                        i.putIntegerArrayListExtra("barco4_1", barco4Posiciones);
                        i.putIntegerArrayListExtra("barco5_1", barco5Posiciones);

                        i.putIntegerArrayListExtra("barco1M", barco1M);
                        i.putIntegerArrayListExtra("barco2M", barco2M);
                        i.putIntegerArrayListExtra("barco3M", barco3M);
                        i.putIntegerArrayListExtra("barco4M", barco4M);
                        i.putIntegerArrayListExtra("barco5M", barco5M);

                        i.putExtra("vs2J", vs2J);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Toast.makeText(this, "No has colocado todos los barcos", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (barco2Posiciones.size() != 0 && barco3Posiciones.size() != 0 && barco3Posiciones2.size() != 0 && barco4Posiciones.size() != 0 && barco5Posiciones.size() != 0) {

                    Intent i = new Intent(this, GameActivity.class);
                    i.putIntegerArrayListExtra("barco1_2", barco2Posiciones);
                    i.putIntegerArrayListExtra("barco2_2", barco3Posiciones);
                    i.putIntegerArrayListExtra("barco3_2", barco3Posiciones2);
                    i.putIntegerArrayListExtra("barco4_2", barco4Posiciones);
                    i.putIntegerArrayListExtra("barco5_2", barco5Posiciones);

                    barco2Posiciones = listaBarcosJugador1.get(0);
                    barco3Posiciones = listaBarcosJugador1.get(1);
                    barco3Posiciones2 = listaBarcosJugador1.get(2);
                    barco4Posiciones = listaBarcosJugador1.get(3);
                    barco5Posiciones = listaBarcosJugador1.get(4);

                    i.putIntegerArrayListExtra("barco1_1", barco2Posiciones);
                    i.putIntegerArrayListExtra("barco2_1", barco3Posiciones);
                    i.putIntegerArrayListExtra("barco3_1", barco3Posiciones2);
                    i.putIntegerArrayListExtra("barco4_1", barco4Posiciones);
                    i.putIntegerArrayListExtra("barco5_1", barco5Posiciones);

                    i.putExtra("vs2J", vs2J);
                    startActivity(i);
                    finish();

                }else{
                    Toast.makeText(this, "No has colocado todos los barcos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //La máquina coloca sus barcos aleatoriamente controlando que no se repitan las posiciones.
    public void colocarBarcosMaquina(){
        do {
            num1 = (int) (Math.random()*100)+1;
        }while(posicionesUsadas.contains(num1));

        if (num1 <= 10){
            max = 10;
        }else if (num1 <= 20){
            max = 20;
        }else if (num1 <= 30) {
            max = 30;
        }else if (num1 <= 40) {
            max = 40;
        }else if (num1 <= 50) {
            max = 50;
        }else if (num1 <= 60) {
            max = 60;
        }else if (num1 <= 70) {
            max = 70;
        }else if (num1 <= 80) {
            max = 80;
        }else if (num1 <= 90) {
            max = 90;
        }else{
            max = 100;
        }

        switch (numBarco){
            case 0:
                barco5M.removeAll(barco5M);
                posicionesUsadas.add(num1);
                barco5M.add(num1);

                orientacion = (int) (Math.random()*2);                                                      // 0 horizontal | 1 vertical
                if (orientacion == 0){
                    siguientePosicion = num1+1;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max){
                        barco5M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+2;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max) {
                            barco5M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                            siguientePosicion = num1 + 3;
                            if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max) {
                                barco5M.add(siguientePosicion);
                                posicionesUsadas.add(siguientePosicion);
                                siguientePosicion = num1 + 4;
                                if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max) {
                                    barco5M.add(siguientePosicion);
                                    posicionesUsadas.add(siguientePosicion);
                                }else{
                                    colocarBarcosMaquina();
                                }
                            }else{
                                colocarBarcosMaquina();
                            }
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }else{
                    siguientePosicion = num1+10;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100){
                        barco5M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+20;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100) {
                            barco5M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                            siguientePosicion = num1 + 30;
                            if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100) {
                                barco5M.add(siguientePosicion);
                                posicionesUsadas.add(siguientePosicion);
                                siguientePosicion = num1 + 40;
                                if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100) {
                                    barco5M.add(siguientePosicion);
                                    posicionesUsadas.add(siguientePosicion);
                                }else{
                                    colocarBarcosMaquina();
                                }
                            }else{
                                colocarBarcosMaquina();
                            }
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }

                numBarco++;
                colocarBarcosMaquina();
                break;
            case 1:
                barco4M.removeAll(barco4M);

                posicionesUsadas.add(num1);
                barco4M.add(num1);

                orientacion = (int) (Math.random()*2);                                                      // 0 horizontal | 1 vertical
                if (orientacion == 0){
                    siguientePosicion = num1+1;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max){
                        barco4M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+2;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max) {
                            barco4M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                            siguientePosicion = num1 + 3;
                            if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max) {
                                barco4M.add(siguientePosicion);
                                posicionesUsadas.add(siguientePosicion);
                            }else{
                                colocarBarcosMaquina();
                            }
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }else{
                    siguientePosicion = num1+10;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100){
                        barco4M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+20;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100) {
                            barco4M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                            siguientePosicion = num1 + 30;
                            if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100) {
                                barco4M.add(siguientePosicion);
                                posicionesUsadas.add(siguientePosicion);
                            }else{
                                colocarBarcosMaquina();
                            }
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }

                numBarco++;
                colocarBarcosMaquina();
                break;
            case 2:
                barco3M.removeAll(barco3M);

                posicionesUsadas.add(num1);
                barco3M.add(num1);

                orientacion = (int) (Math.random()*2);                                                      // 0 horizontal | 1 vertical
                if (orientacion == 0){
                    siguientePosicion = num1+1;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max){
                        barco3M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+2;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max) {
                            barco3M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }else{
                    siguientePosicion = num1+10;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100){
                        barco3M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+20;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100) {
                            barco3M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }

                numBarco++;
                colocarBarcosMaquina();
                break;
            case 3:
                barco2M.removeAll(barco2M);
                posicionesUsadas.add(num1);
                barco2M.add(num1);

                orientacion = (int) (Math.random()*2);                                                      // 0 horizontal | 1 vertical
                if (orientacion == 0){
                    siguientePosicion = num1+1;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max){
                        barco2M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+2;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max) {
                            barco2M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }else{
                    siguientePosicion = num1+10;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100){
                        barco2M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                        siguientePosicion = num1+20;
                        if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100) {
                            barco2M.add(siguientePosicion);
                            posicionesUsadas.add(siguientePosicion);
                        }else{
                            colocarBarcosMaquina();
                        }
                    }else{
                        colocarBarcosMaquina();
                    }
                }

                numBarco++;
                colocarBarcosMaquina();
                break;
            case 4:
                barco1M.removeAll(barco1M);

                posicionesUsadas.add(num1);
                barco1M.add(num1);

                orientacion = (int) (Math.random()*2);                                                      // 0 horizontal | 1 vertical
                if (orientacion == 0){
                    siguientePosicion = num1+1;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= max){
                        barco1M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                    }else{
                        colocarBarcosMaquina();
                    }
                }else{
                    siguientePosicion = num1+10;
                    if (!posicionesUsadas.contains(siguientePosicion) && siguientePosicion <= 100){
                        barco1M.add(siguientePosicion);
                        posicionesUsadas.add(siguientePosicion);
                    }else{
                        colocarBarcosMaquina();
                    }
                }
                break;
        }
    }

    //Botón deshacer. Elimina los barcos seleccionados para volverlos a colocar.
    public void deshacer(View view){
        b3 = 0;
        devolverListenerPosGuardadas();
        devolverListenerPosNoGuardadas();
        deseleccionarCasillas();
        botonesPulsados.removeAll(botonesPulsados);
        barco2Posiciones.removeAll(barco2Posiciones);
        barco3Posiciones.removeAll(barco3Posiciones);
        barco3Posiciones2.removeAll(barco3Posiciones2);
        barco4Posiciones.removeAll(barco4Posiciones);
        barco5Posiciones.removeAll(barco5Posiciones);
    }

    //El método se ejecuta cuando se intenta colocar un barco no válido.
    public void borrar(ArrayList<Integer> provisional){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                for (int j=0; j<provisional.size(); j++){
                    if (view.getId() == provisional.get(j)){
                        view.setBackground(drawableCuadricula);
                    }

                    for (int q=0; q<botonesPulsados.size(); q++){
                        if (botonesPulsados.get(q) == provisional.get(j)){
                            botonesPulsados.remove(q);
                        }
                    }
                }
            }
        }
        devolverListenerPosNoGuardadas();
    }

    //Deselecciona las casillas de los barcos seleccionados al darle a deshacer
    public void deseleccionarCasillas(){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                if (botonesPulsados.contains(view.getId())) {
                    view.setBackground(drawableCuadricula);
                }
            }
        }
    }

    //Quitar el Listener de los botones de las posiciones no guardadas
    public void quitarListenerPosNoGuardadas(){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                if (!botonesPulsados.contains(view.getId())) {
                    view.setEnabled(false);
                }
            }
        }
    }

    //Poner el Listener de los botones de las posiciones no guardadas (saltando las letras y números del tablero)
    public void devolverListenerPosNoGuardadas(){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                if (!botonesPulsados.contains(view.getId()) && view.getId() != idBotonNoContable) {
                    view.setEnabled(true);
                }
            }
        }
    }

    //Poner el Listener de los botones de las posiciones guardadas
    public void devolverListenerPosGuardadas(){
        View view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                if (botonesPulsados.contains(view.getId())) {
                    view.setEnabled(true);
                }
            }
        }
    }
}