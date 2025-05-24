package com.example.projet_mobile.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;
import com.example.projet_mobile.database.AppDatabase;
import com.example.projet_mobile.model.Budget;
import com.example.projet_mobile.model.Depense;

import java.util.List;

public class PrevisionActivity extends AppCompatActivity {
    TextView txtBudgetTotal, txtDepensesTotales, txtReste;
    TextView textViewProgression, textViewCharges, textViewPrevisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevision);

        txtBudgetTotal = findViewById(R.id.txtBudgetTotal);
        txtDepensesTotales = findViewById(R.id.txtDepensesTotales);
        txtReste = findViewById(R.id.txtReste);

        textViewProgression = findViewById(R.id.textViewProgression);
        textViewCharges = findViewById(R.id.textViewCharges);
        textViewPrevisions = findViewById(R.id.textViewPrevisions);

        // ✅ Traitement dans un thread
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);

            List<Budget> budgets = db.budgetDao().getAll();
            List<Depense> depenses = db.depenseDao().getAll();

            final double[] totalBudget = {0};
            final double[] totalDepenses = {0};

            for (Budget b : budgets) {
                totalBudget[0] += b.getMontant();
            }

            for (Depense d : depenses) {
                totalDepenses[0] += d.getMontant();
            }

            double reste = totalBudget[0] - totalDepenses[0];
            double progression = (totalBudget[0] != 0) ? (totalDepenses[0] / totalBudget[0] * 100) : 0;

            // ✅ Mise à jour de l'interface UI
            runOnUiThread(() -> {
                txtBudgetTotal.setText("Budget Total: " + totalBudget[0] + " €");
                txtDepensesTotales.setText("Dépenses: " + totalDepenses[0] + " €");
                txtReste.setText("Reste: " + reste + " €");

                textViewProgression.setText("Progression du budget: " + String.format("%.2f", progression) + " %");
                textViewCharges.setText("Charges à venir: à définir");
                textViewPrevisions.setText("Prévisions des dépenses: à définir");
            });
        }).start();
    }
}
