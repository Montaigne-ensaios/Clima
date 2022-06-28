package com.example.clima;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CidadesAdapter extends RecyclerView.Adapter<CidadesAdapter.ViewHolder> {
    private Cidade[] cidades;

    @NonNull
    @Override
    public CidadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_cidade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CidadesAdapter.ViewHolder holder, int position) {
         cidades[position].setTempView(holder.textTemp);
         // define o tempView da cidade de acordo com o holder

        holder.textNome.setText(cidades[position].getNome());

        holder.card.setOnClickListener(v -> {
            Context context = holder.card.getContext();
            Intent intent = new Intent(context, DetalhesClimaActivity.class);
            intent.putExtra("cidade", holder.textNome.getText());
            intent.putExtra("temperatura", holder.textTemp.getText());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cidades.length;
    }

    public void setCidades(Cidade[] cidades){
        this.cidades = cidades;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView textNome, textTemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            textNome = itemView.findViewById(R.id.nome);
            textTemp = itemView.findViewById(R.id.temp);
        }
    }
}
