package com.example.projet_mobile.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_mobile.model.Budget;

import java.util.List;

@Dao
public interface BudgetDao {

    @Insert
    void insert(Budget budget);

    @Update
    void update(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("SELECT * FROM Budget")
    List<Budget> getAll();

    @Query("SELECT * FROM Budget WHERE categorie = :categorie LIMIT 1")
    Budget getByCategorie(String categorie);

    @Query("SELECT * FROM Budget WHERE id = :id LIMIT 1")
    Budget getById(int id);
}
