package com.example.projet_mobile.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;
import com.example.projet_mobile.database.AppDatabase;
import com.example.projet_mobile.model.Budget;

public class AjouterBudgetActivity extends AppCompatActivity {
    EditText editCategorie, editMontant;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_budget);

        editCategorie = findViewById(R.id.editCategorie);
        editMontant = findViewById(R.id.editMontant);
        btnSave = findViewById(R.id.btnEnregistrerBudget);

        btnSave.setOnClickListener(v -> {
            String categorie = editCategorie.getText().toString().trim();
            String montantStr = editMontant.getText().toString().trim();

            if (categorie.isEmpty() || montantStr.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double montant = Double.parseDouble(montantStr);
                Budget b = new Budget();
                b.setCategorie(categorie);
                b.setMontant(montant);

                // ✅ Exécution en thread séparé
                new Thread(() -> {
                    AppDatabase.getInstance(this).budgetDao().insert(b);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Budget ajouté", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
