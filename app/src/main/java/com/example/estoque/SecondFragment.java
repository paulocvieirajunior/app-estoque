package com.example.estoque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.estoque.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String perfil = getArguments() != null ? getArguments().getString("perfil") : "";
        configurarMenuPorPerfil(perfil);
        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );
    }

    private void configurarMenuPorPerfil(String perfil) {
        binding.btnCadastro.setVisibility(View.GONE);
        binding.btnEntrada.setVisibility(View.GONE);
        binding.btnVenda.setVisibility(View.GONE);
        binding.btnSeparacao.setVisibility(View.GONE);

        switch (perfil) {
            case "gerente":
                binding.btnCadastro.setVisibility(View.VISIBLE);
                binding.btnEntrada.setVisibility(View.VISIBLE);
                binding.btnVenda.setVisibility(View.VISIBLE);
                binding.btnSeparacao.setVisibility(View.VISIBLE);

                binding.btnCadastro.setOnClickListener(v ->
                        NavHostFragment.findNavController(SecondFragment.this)
                                .navigate(R.id.action_SecondFragment_to_cadastroProdutoFragment)
                );
                binding.btnEntrada.setOnClickListener(v ->
                        NavHostFragment.findNavController(this).navigate(R.id.action_SecondFragment_to_listaProdutosFragment)
                );
                break;
            case "estoquista":
                binding.btnEntrada.setVisibility(View.VISIBLE);
                binding.btnEntrada.setVisibility(View.VISIBLE);
                binding.btnEntrada.setOnClickListener(v ->
                        NavHostFragment.findNavController(this).navigate(R.id.action_SecondFragment_to_listaProdutosFragment)
                );
                break;
            case "vendedor":
                binding.btnVenda.setVisibility(View.VISIBLE);
                break;
            case "embalador":
                binding.btnSeparacao.setVisibility(View.VISIBLE);
                break;
            default:
                Toast.makeText(getContext(), "Perfil desconhecido", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}