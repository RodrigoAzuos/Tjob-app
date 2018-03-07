package com.azapps.tjob_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azapps.tjob_app.R;
import com.azapps.tjob_app.models.Comentario;

import java.util.List;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {
    List<Comentario> comentarios;
    Context mContext;


    public ComentarioAdapter(Context context, List<Comentario> comentarios) {
        this.comentarios = comentarios;
        mContext  = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtComentario;

        public ViewHolder(View itemView) {
            super(itemView);

            txtComentario = itemView.findViewById(R.id.cometario_layout);

        }

    }

    @Override
    public ComentarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_comentario_lista, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comentario comentario = comentarios.get(position);
        holder.txtComentario.setText(comentario.getDescricao());

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

}
