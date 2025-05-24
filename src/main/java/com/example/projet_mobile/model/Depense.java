package com.example.projet_mobile.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity
public class Depense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nom;
    private double montant;
    private String categorie;
    private Date date;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
