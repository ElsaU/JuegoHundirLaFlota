package com.example.juegohundirlaflota.BD_RANKING;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "jugadores.db";

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(
                "create table "+JugadorContract.JugadorEntry.JUGADOR_TABLE_NAME +"("
                        + JugadorContract.JugadorEntry.JUGADOR_ID + " integer primary key,"
                        + JugadorContract.JugadorEntry.JUGADOR_NOMBRE +" text,"
                        + JugadorContract.JugadorEntry.JUGADOR_PUNTOS +" integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("DROP TABLE IF EXISTS "+ JugadorContract.JugadorEntry.JUGADOR_TABLE_NAME);
        onCreate(bd);
    }

    public boolean insertJugador (String nombre, int puntos) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JugadorContract.JugadorEntry.JUGADOR_NOMBRE, nombre);
        contentValues.put(JugadorContract.JugadorEntry.JUGADOR_PUNTOS, puntos);
        bd.insert(JugadorContract.JugadorEntry.JUGADOR_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<Jugador> listarRanking() {
        ArrayList<Jugador> lista = new ArrayList<>();
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.query(
                JugadorContract.JugadorEntry.JUGADOR_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "CAST("+ JugadorContract.JugadorEntry.JUGADOR_PUNTOS + " AS INTEGER) DESC",
                "15"
        );

        while (cursor.moveToNext()){
            String nom = cursor.getString(cursor.getColumnIndex(JugadorContract.JugadorEntry.JUGADOR_NOMBRE));
            int punt = cursor.getInt(cursor.getColumnIndex(JugadorContract.JugadorEntry.JUGADOR_PUNTOS));
            Jugador j = new Jugador(nom, punt);
            lista.add(j);
        }
        return lista;
    }

    public void borrarLista () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(JugadorContract.JugadorEntry.JUGADOR_TABLE_NAME,
                null,
                null
        );
    }
}
