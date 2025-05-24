package com.example.projet_mobile.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_mobile.model.Abonnement;

import java.util.List;

@Dao
public interface AbonnementDao {

    @Insert
    void insert(Abonnement abonnement);

    @Update
    void update(Abonnement abonnement);

    @Delete
    void delete(Abonnement abonnement);

    @Query("SELECT * FROM Abonnement ORDER BY datePaiement ASC")
    List<Abonnement> getAll();

    @Query("SELECT * FROM Abonnement WHERE id = :id LIMIT 1")
    Abonnement getById(int id);
}
