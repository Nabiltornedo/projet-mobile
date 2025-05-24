package com.example.projet_mobile.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_mobile.R;
import com.example.projet_mobile.database.AppDatabase;
import com.example.projet_mobile.model.Abonnement;
import com.example.projet_mobile.utils.NotificationReceiver;

import java.util.Calendar;
import java.util.List;

public class AbonnementActivity extends AppCompatActivity {
    private int utilisateurId;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<Abonnement> abonnements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonnement);

        utilisateurId = getIntent().getIntExtra("utilisateurId", -1);
        if (utilisateurId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        Button btnAjouter = findViewById(R.id.btnAjouterAbonnement);
        listView = findViewById(R.id.listViewAbonnements);

        btnAjouter.setOnClickListener(v -> {
            Intent intent = new Intent(this, AjouterAbonnementActivity.class);
            intent.putExtra("utilisateurId", utilisateurId);
            startActivity(intent);
        });

        loadAbonnements();
    }

    private void loadAbonnements() {
        new Thread(() -> {
            abonnements = AppDatabase.getInstance(this).abonnementDao().getAll();
            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
                for (Abonnement a : abonnements) {
                    adapter.add(a.getNom() + " - " + a.getMontant() + " € - " + Abonnement.formatDate(a.getDatePaiement()));
                    scheduleNotification(a); // ⏰
                }
                listView.setAdapter(adapter);

                // 🗑️ Suppression sur clic long
                listView.setOnItemLongClickListener((parent, view, position, id) -> {
                    Abonnement toDelete = abonnements.get(position);
                    new Thread(() -> {
                        AppDatabase.getInstance(this).abonnementDao().delete(toDelete);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Abonnement supprimé", Toast.LENGTH_SHORT).show();
                            loadAbonnements(); // refresh
                        });
                    }).start();
                    return true;
                });
            });
        }).start();
    }

    // ⏰ Notification à la date de paiement
    private void scheduleNotification(Abonnement abonnement) {
        long triggerAt = abonnement.getDatePaiement().getTime();

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("nom", abonnement.getNom());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                abonnement.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null && triggerAt > System.currentTimeMillis()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
        }
    }
}
