package com.example.projet_mobile.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Abonnement {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nom;
    private double montant;
    private Date datePaiement;
    private String frequence; // exemple: "mensuel", "annuel"

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Date getDatePaiement() { return datePaiement; }
    public void setDatePaiement(Date datePaiement) { this.datePaiement = datePaiement; }

    public String getFrequence() { return frequence; }
    public void setFrequence(String frequence) { this.frequence = frequence; }

    // Utilisé pour afficher joliment la date dans la liste
    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
    }

    // Optionnel : utilisé si tu veux afficher un résumé texte
    public String getDate() {
        return formatDate(this.datePaiement);
    }

    public String getPrix() {
        return String.format(Locale.getDefault(), "%.2f €", this.montant);
    }
}
