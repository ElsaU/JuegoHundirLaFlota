package com.example.juegohundirlaflota.BD_RANKING;

public class Jugador {
    String nombre;
    int puntos;

    public Jugador(String nombre, int puntos){
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPuntos(){
        return puntos;
    }

    public void setNombre(String n){
        this.nombre = n;
    }

    public void setPuntos(int p){
        this.puntos = p;
    }
}
