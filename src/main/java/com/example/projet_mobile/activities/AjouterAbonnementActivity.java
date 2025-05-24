package com.example.projet_mobile.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;
import com.example.projet_mobile.database.AppDatabase;
import com.example.projet_mobile.model.Abonnement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AjouterAbonnementActivity extends AppCompatActivity {
    EditText editNom, editMontant, editDate;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_abonnement);

        editNom = findViewById(R.id.editNom);
        editMontant = findViewById(R.id.editMontant);
        editDate = findViewById(R.id.editDateProchaine);
        btnSave = findViewById(R.id.btnSauvegarderAbonnement);

        // ✅ Afficher le calendrier quand on clique sur le champ de date
        editDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(
                    AjouterAbonnementActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        editDate.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        // ✅ Action du bouton Sauvegarder
        btnSave.setOnClickListener(v -> {
            String nom = editNom.getText().toString().trim();
            String montantStr = editMontant.getText().toString().trim();
            String dateStr = editDate.getText().toString().trim();

            if (nom.isEmpty() || montantStr.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double montant = Double.parseDouble(montantStr);
                Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dateStr);

                Abonnement abonnement = new Abonnement();
                abonnement.setNom(nom);
                abonnement.setMontant(montant);
                abonnement.setDatePaiement(date);

                // ✅ Insertion Room dans un thread
                new Thread(() -> {
                    AppDatabase.getInstance(this).abonnementDao().insert(abonnement);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Abonnement enregistré", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                Toast.makeText(this, "Date invalide (format JJ/MM/AAAA)", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
