package com.example.juegohundirlaflota.BD_RANKING;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MiArrayAdapter extends ArrayAdapter<Jugador> {
    public MiArrayAdapter(Context context, Jugador[] jugadores) {
        super(context, 0, jugadores);
    }
    public MiArrayAdapter(Context context, java.util.List<Jugador> jugadores) {
        super(context, 0, jugadores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Jugador jugador = getItem(position);
        View view;
        if (convertView == null)
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
        else
            view = convertView;
        TextView tv;
        tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setTextSize(26);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setText(String.valueOf(jugador.puntos));
        tv = (TextView) view.findViewById(android.R.id.text2);
        tv.setTextSize(18);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        if (jugador.nombre != null)
            tv.setText(jugador.nombre);
        else
            tv.setText("");
        return view;
    }
}
