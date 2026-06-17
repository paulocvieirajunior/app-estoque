package com.example.estoque;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {
    private Cursor cursor;

    public ProdutoAdapter(Cursor cursor) { this.cursor = cursor; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtQtd, txtPreco;
        public ViewHolder(View v) {
            super(v);
            txtNome = v.findViewById(R.id.txtNome);
            txtQtd = v.findViewById(R.id.txtQtd);
            txtPreco = v.findViewById(R.id.txtPreco);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) return;
        holder.txtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        holder.txtQtd.setText("Qtd: " + cursor.getInt(cursor.getColumnIndexOrThrow("quantidade")));
        double preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"));
        holder.txtPreco.setText(String.format("R$ %.2f", preco));
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }
}