package com.example.projet_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;
import com.example.projet_mobile.dao.UtilisateurDao;
import com.example.projet_mobile.database.AppDatabase;
import com.example.projet_mobile.model.Utilisateur;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private UtilisateurDao utilisateurDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        utilisateurDao = AppDatabase.getInstance(this).utilisateurDao();

        buttonLogin.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            Log.d("DEBUG-LOGIN", "Tentative avec : " + email + " / " + password);

            new Thread(() -> {
                // ➤ Afficher tous les utilisateurs de la base
                List<Utilisateur> liste = utilisateurDao.getTousUtilisateurs();
                for (Utilisateur u : liste) {
                    Log.d("DEBUG-DB", "Utilisateur en base : " + u.getEmail() + " / " + u.getMotDePasse());
                }

                // ➤ Authentifier
                Utilisateur utilisateur = utilisateurDao.authentifier(email, password);

                runOnUiThread(() -> {
                    if (utilisateur != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("utilisateurId", utilisateur.getId());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Utilisateur introuvable", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        buttonRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
