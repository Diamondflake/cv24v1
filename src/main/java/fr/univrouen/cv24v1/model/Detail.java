package fr.univrouen.cv24v1.model;

import java.time.LocalDate;

public class Detail implements CVElement {
    private LocalDate dateDeb;
    private LocalDate dateFin;

    private String titre;

    public void setDateDeb(LocalDate dateDeb) {
        this.dateDeb = dateDeb;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateDeb() {
        return dateDeb;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public String getTitre() {
        return titre;
    }
}
