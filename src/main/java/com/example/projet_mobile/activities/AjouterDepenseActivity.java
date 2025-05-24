package com.example.projet_mobile.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;
import com.example.projet_mobile.database.AppDatabase;
import com.example.projet_mobile.model.Depense;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AjouterDepenseActivity extends AppCompatActivity {
    EditText editCategorie, editMontant, editDate;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_depense);

        editCategorie = findViewById(R.id.editCategorie);
        editMontant = findViewById(R.id.editMontant);
        editDate = findViewById(R.id.editDate);
        btnSave = findViewById(R.id.btnAjouterDepense);

        // ✅ Sélecteur de date
        editDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(
                    AjouterDepenseActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        editDate.setText(date);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        btnSave.setOnClickListener(v -> {
            String categorie = editCategorie.getText().toString().trim();
            String montantStr = editMontant.getText().toString().trim();
            String dateStr = editDate.getText().toString().trim();

            if (categorie.isEmpty() || montantStr.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!estDateValide(dateStr)) {
                Toast.makeText(this, "Date invalide (format JJ/MM/AAAA)", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double montant = Double.parseDouble(montantStr);
                Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dateStr);

                Depense d = new Depense();
                d.setCategorie(categorie);
                d.setMontant(montant);
                d.setDate(date);

                new Thread(() -> {
                    AppDatabase.getInstance(this).depenseDao().insert(d);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Dépense ajoutée", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                Toast.makeText(this, "Erreur de format de date", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean estDateValide(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
