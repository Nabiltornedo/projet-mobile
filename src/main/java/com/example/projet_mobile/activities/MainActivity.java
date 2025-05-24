package com.example.projet_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;

public class MainActivity extends AppCompatActivity {
    private int utilisateurId;
    private Button buttonAbonnements, buttonBudgets, buttonDepenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utilisateurId = getIntent().getIntExtra("utilisateurId", -1);
        if (utilisateurId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        buttonAbonnements = findViewById(R.id.buttonAbonnements);
        buttonBudgets = findViewById(R.id.buttonBudgets);
        buttonDepenses = findViewById(R.id.buttonDepenses);

        buttonAbonnements.setOnClickListener(view -> {
            Intent intent = new Intent(this, AbonnementActivity.class);
            intent.putExtra("utilisateurId", utilisateurId);
            startActivity(intent);
        });

        buttonBudgets.setOnClickListener(view -> {
            Intent intent = new Intent(this, BudgetActivity.class);
            intent.putExtra("utilisateurId", utilisateurId);
            startActivity(intent);
        });

        buttonDepenses.setOnClickListener(view -> {
            Intent intent = new Intent(this, PrevisionActivity.class);
            intent.putExtra("utilisateurId", utilisateurId);
            startActivity(intent);
        });
    }
}