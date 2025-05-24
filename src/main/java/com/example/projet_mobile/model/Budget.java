package com.example.projet_mobile.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String categorie;
    private double montant;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getDate() {
        return "";
    }
}
