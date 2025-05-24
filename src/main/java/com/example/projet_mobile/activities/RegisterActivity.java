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

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextNom, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private UtilisateurDao utilisateurDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNom = findViewById(R.id.editTextNom);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        utilisateurDao = AppDatabase.getInstance(this).utilisateurDao();

        buttonRegister.setOnClickListener(view -> {
            String nom = editTextNom.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String motDePasse = editTextPassword.getText().toString().trim();

            if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Utilisateur existant = utilisateurDao.getUtilisateurByEmail(email);
                    if (existant != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "Un utilisateur avec cet email existe déjà", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    Utilisateur utilisateur = new Utilisateur(nom, email, motDePasse);
                    utilisateurDao.insererUtilisateur(utilisateur);

                    Log.d("Register", "Utilisateur inséré : " + email + " / " + motDePasse);

                    Utilisateur inserted = null;
                    int retries = 0;
                    while (inserted == null && retries < 10) {
                        inserted = utilisateurDao.getUtilisateurByEmail(email);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        retries++;
                    }

                    if (inserted != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "Échec d'enregistrement", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        });
    }
}
