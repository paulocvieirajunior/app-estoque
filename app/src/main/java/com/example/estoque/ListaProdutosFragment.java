package com.example.estoque;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.estoque.DatabaseHelper;
import com.example.estoque.ProdutoAdapter;
import com.example.estoque.databinding.FragmentListaProdutosBinding;

public class ListaProdutosFragment extends Fragment {
    private FragmentListaProdutosBinding binding;
    private DatabaseHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListaProdutosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(requireContext());

        Cursor cursor = db.listarProdutos();

        if (cursor != null && cursor.getCount() > 0) {
            ProdutoAdapter adapter = new ProdutoAdapter(cursor);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Nenhum produto encontrado no estoque.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}