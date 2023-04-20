package com.example.juegohundirlaflota.BD_RANKING;

import android.provider.BaseColumns;

public class JugadorContract {
    public static abstract class JugadorEntry implements BaseColumns {
        public static final String JUGADOR_TABLE_NAME = "JUGADOR";
        public static final String JUGADOR_ID = "id";
        public static final String JUGADOR_NOMBRE = "nombre";
        public static final String JUGADOR_PUNTOS = "puntos";
    }
}
