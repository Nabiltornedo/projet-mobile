package com.example.projet_mobile.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.projet_mobile.model.Utilisateur;

import java.util.List;

@Dao
public interface UtilisateurDao {

    @Insert
    void insererUtilisateur(Utilisateur utilisateur);

    @Update
    void modifierUtilisateur(Utilisateur utilisateur);

    @Delete
    void supprimerUtilisateur(Utilisateur utilisateur);

    @Query("SELECT * FROM utilisateur WHERE email = :email AND motDePasse = :motDePasse LIMIT 1")
    Utilisateur authentifier(String email, String motDePasse);

    @Query("SELECT * FROM utilisateur WHERE email = :email LIMIT 1")
    Utilisateur getUtilisateurByEmail(String email);

    @Query("SELECT * FROM utilisateur WHERE id = :id")
    Utilisateur getUtilisateurParId(int id);

    @Query("SELECT * FROM utilisateur")
    List<Utilisateur> getTousUtilisateurs();
}
