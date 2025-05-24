package com.example.projet_mobile.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import com.example.projet_mobile.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String nom = intent.getStringExtra("nom");

        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Canal de notification
        String channelId = "abonnement_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, "Abonnement", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        // Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Paiement à venir")
                .setContentText("N'oubliez pas de payer : " + nom)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
