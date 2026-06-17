package com.example.estoque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.estoque.databinding.FragmentCadastroProdutoBinding;

public class CadastroProdutoFragment extends Fragment {

    private FragmentCadastroProdutoBinding binding;
    private DatabaseHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCadastroProdutoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHelper(requireContext());

        binding.btnSalvar.setOnClickListener(v -> salvarProduto());
    }

    private void salvarProduto() {
        binding.layoutNome.setError(null);
        binding.layoutQtd.setError(null);
        binding.layoutPreco.setError(null);

        String nome = binding.editNome.getText().toString().trim();
        String qtdStr = binding.editQtd.getText().toString().trim();
        String precoStr = binding.editPreco.getText().toString().trim();

        if (nome.isEmpty()) {
            binding.layoutNome.setError("Informe o nome do produto.");
            binding.editNome.requestFocus();
            return;
        }
        if (qtdStr.isEmpty()) {
            binding.layoutQtd.setError("Informe a quantidade.");
            binding.editQtd.requestFocus();
            return;
        }
        if (precoStr.isEmpty()) {
            binding.layoutPreco.setError("Informe o preço.");
            binding.editPreco.requestFocus();
            return;
        }

        int qtd = Integer.parseInt(qtdStr);
        double preco = Double.parseDouble(precoStr);

        long resultado = db.inserirProduto(nome, qtd, preco);

        if (resultado != -1) {
            Toast.makeText(getContext(), "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            binding.editNome.setText("");
            binding.editQtd.setText("");
            binding.editPreco.setText("");
            binding.editNome.requestFocus();
        } else {
            Toast.makeText(getContext(), "Erro ao cadastrar produto.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}