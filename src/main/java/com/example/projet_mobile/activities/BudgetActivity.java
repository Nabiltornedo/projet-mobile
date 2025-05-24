package com.example.projet_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;
import com.example.projet_mobile.adapters.BudgetAdapter;
import com.example.projet_mobile.database.AppDatabase;
import com.example.projet_mobile.model.Budget;

import java.util.List;

public class BudgetActivity extends AppCompatActivity {
    Button btnAddBudget, btnAddDepense;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        btnAddBudget = findViewById(R.id.btnAjouterBudget);
        btnAddDepense = findViewById(R.id.btnAjouterDepense);
        listView = findViewById(R.id.listViewBudgets);

        btnAddBudget.setOnClickListener(v -> startActivity(new Intent(this, AjouterBudgetActivity.class)));
        btnAddDepense.setOnClickListener(v -> startActivity(new Intent(this, AjouterDepenseActivity.class)));

        // ✅ Exécute la requête Room dans un thread séparé
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Budget> budgets = db.budgetDao().getAll();

            runOnUiThread(() -> {
                // ✅ Mise à jour de la vue dans le thread principal
                BudgetAdapter adapter = new BudgetAdapter(this, budgets);
                listView.setAdapter(adapter);
            });
        }).start();
    }
}
