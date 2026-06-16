package com.example.estoque;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.estoque.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private DatabaseHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHelper(requireContext());

        binding.btnLogin.setOnClickListener(v -> tentarLogin());
    }

    private void tentarLogin() {
        // Limpa erros anteriores
        binding.layoutUsuario.setError(null);
        binding.layoutSenha.setError(null);

        String usuario = binding.editUsuario.getText().toString().trim();
        String senha   = binding.editSenha.getText().toString().trim();

        // Validação — erro aparece embaixo do campo correspondente
        if (usuario.isEmpty()) {
            binding.layoutUsuario.setError("Informe o nome de usuário.");
            binding.editUsuario.requestFocus();
            return;
        }
        if (senha.isEmpty()) {
            binding.layoutSenha.setError("Informe a senha.");
            binding.editSenha.requestFocus();
            return;
        }

        // Consulta o banco
        String perfil = db.autenticar(usuario, senha);

        if (perfil == null) {
            // Erro geral: marca o campo de senha
            binding.layoutSenha.setError("Usuário ou senha incorretos.");
            binding.editSenha.setText("");
            return;
        }

        // Salva sessão
        SharedPreferences prefs = requireContext()
                .getSharedPreferences("sessao", requireContext().MODE_PRIVATE);
        prefs.edit()
                .putString("usuario", usuario)
                .putString("perfil",  perfil)
                .apply();

        // Navega para o próximo fragment passando o perfil
        Bundle args = new Bundle();
        args.putString("perfil", perfil);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}