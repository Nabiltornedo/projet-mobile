package com.example.projet_mobile.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.projet_mobile.dao.AbonnementDao;
import com.example.projet_mobile.dao.BudgetDao;
import com.example.projet_mobile.dao.DepenseDao;
import com.example.projet_mobile.dao.UtilisateurDao;
import com.example.projet_mobile.model.Abonnement;
import com.example.projet_mobile.model.Budget;
import com.example.projet_mobile.model.Depense;
import com.example.projet_mobile.model.Utilisateur;
import com.example.projet_mobile.utils.DateConverter;

@Database(entities = {Abonnement.class, Depense.class, Budget.class, Utilisateur.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract AbonnementDao abonnementDao();
    public abstract DepenseDao depenseDao();
    public abstract BudgetDao budgetDao();

    public abstract UtilisateurDao utilisateurDao();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "gestion_financiere_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
