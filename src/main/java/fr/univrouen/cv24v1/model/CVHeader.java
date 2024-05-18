package fr.univrouen.cv24v1.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CVHeader {
    private int id;

    private String genre;

    private String nom;

    private String prenom;

    private String tel;

    private String mel;

    private boolean objectifIsEmploi;
    private String objectif;

    private Diplome headDiplome;

    // Setters

    public void setID(int id) {
        this.id = id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMel(String mel) {
        this.mel = mel;
    }

    public void setObjectifType(boolean isEmploi) {
        this.objectifIsEmploi = isEmploi;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public void setHeadDiplome(Diplome diplome) {
        headDiplome = diplome;
    }

    // Getters

    public int getID() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public String getMel() {
        return mel;
    }

    public boolean isObjectifEmploi() {
        return objectifIsEmploi;
    }

    public String getObjectif() {
        return objectif;
    }

    public Diplome getHeadDiplome() {
        return headDiplome;
    }

    public String toXML() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        StringBuilder string = new StringBuilder();

        string.append("<cv24:cv24header>");

        string.append("<cv24:id>" + id + "</cv24:id>");

        string.append("<cv24:identite>");
        string.append("<cv24:genre>" + genre + "</cv24:genre>");
        string.append("<cv24:nom>" + nom + "</cv24:nom>");
        string.append("<cv24:prenom>" + prenom + "</cv24:prenom>");
        string.append("<cv24:tel>" + tel + "</cv24:tel>");
        string.append("<cv24:mel>" + mel + "</cv24:mel>");
        string.append("</cv24:identite>");

        string.append("<cv24:objectif statut=\"" + (objectifIsEmploi ? "emploi" : "stage") + "\">" + objectif + "</cv24:objectif>");

        if (headDiplome != null) {
            string.append(headDiplome.toXMLHeadDiplome(formatter));
        }

        string.append("</cv24:cv24header>");

        return string.toString();
    }

    public String toHTML() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD/MM/YYYY");
        StringBuilder string = new StringBuilder();

        string.append("<tr><td><a href=\"./html?id=" + id + "\">" + id + "</a></td>");
        string.append("<td>" + genre + " " + prenom + " " + nom + "</td>");
        string.append("<td>" + (objectifIsEmploi ? "Emploi : " : "Stage : ") + objectif + "</td>");

        if (headDiplome != null) {
            string.append("<td>" + headDiplome.toHTML(formatter) + "</td>");
        } else {
            string.append("<td>N/A</td>");
        }

        string.append("</tr>");

        return string.toString();
    }
}
