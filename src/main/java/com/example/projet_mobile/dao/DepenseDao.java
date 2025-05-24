package com.example.projet_mobile.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_mobile.model.Depense;

import java.util.List;

@Dao
public interface DepenseDao {

    @Insert
    void insert(Depense depense);

    @Update
    void update(Depense depense);

    @Delete
    void delete(Depense depense);

    @Query("SELECT * FROM Depense ORDER BY date DESC")
    List<Depense> getAll();

    @Query("SELECT * FROM Depense WHERE categorie = :categorie")
    List<Depense> getByCategorie(String categorie);

    @Query("SELECT SUM(montant) FROM Depense WHERE categorie = :categorie")
    Double getTotalByCategorie(String categorie);

    @Query("SELECT * FROM Depense WHERE id = :id LIMIT 1")
    Depense getById(int id);
}
