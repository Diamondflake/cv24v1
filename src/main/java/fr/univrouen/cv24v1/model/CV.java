package fr.univrouen.cv24v1.model;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "cv24")
@XmlAccessorType(XmlAccessType.NONE)
public class CV implements Serializable {

    private int id;

    private String genre;

    private String nom;

    private String prenom;

    private String tel;

    private String mel;

    private boolean objectifIsEmploi;
    private String objectif;

    private List<Detail> detailList;

    private List<Competence> competenceList;

    private List<Divers> diversList;

    // Constructor

    public CV() {
        detailList = new ArrayList<>();
        competenceList = new ArrayList<>();
        diversList = new ArrayList<>();
    }

    // Setters

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

    public void addDetail(Detail detail) {
        detailList.add(detail);
    }

    public void addCompetence(Competence competence) {
        competenceList.add(competence);
    }

    public void addDivers(Divers divers) {
        diversList.add(divers);
    }

    // Getters

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

    public List<Detail> getDetails() {
        return detailList;
    }

    public List<Competence> getCompetences() {
        return competenceList;
    }

    public List<Divers> getDivers() {
        return diversList;
    }

    public String toXML() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        StringBuilder string = new StringBuilder();

        string.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        string.append("<cv24:cv24 xmlns:cv24=\"http://univ.fr/cv24\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://univ.fr/cv24 cv24.tp1.xsd\">");

        string.append("<cv24:identite>");
            string.append("<cv24:genre>" + genre + "</cv24:genre>");
            string.append("<cv24:nom>" + nom + "</cv24:nom>");
            string.append("<cv24:prenom>" + prenom + "</cv24:prenom>");
            string.append("<cv24:tel>" + tel + "</cv24:tel>");
            string.append("<cv24:mel>" + mel + "</cv24:mel>");
        string.append("</cv24:identite>");

        string.append("<cv24:objectif statut=\"" + (objectifIsEmploi ? "emploi" : "stage") + "\">" + objectif + "</cv24:objectif>");

        string.append("<cv24:prof>");
            int max = detailList.size();
            Detail detail;
            LocalDate date;
            for(int i = 0; i < max; i++) {
                detail = detailList.get(i);
                string.append("<cv24:detail>");
                    string.append("<cv24:datedeb>" + detail.getDateDeb().format(formatter) + "</cv24:datedeb>");
                    date = detail.getDateFin();
                    if (date != null) {
                        string.append("<cv24:datefin>" + date.format(formatter) + "</cv24:datefin>");
                    }
                    string.append("<cv24:titre128>" + detail.getTitre() + "</cv24:titre128>");
                string.append("</cv24:detail>");
            }
        string.append("</cv24:prof>");

        string.append("<cv24:competences>");
            max = competenceList.size();
            for(int i = 0; i < max; i++) {
                string.append(competenceList.get(i).toXML(formatter));
            }
        string.append("</cv24:competences>");

        string.append("<cv24:divers>");
        max = diversList.size();
        for(int i = 0; i < max; i++) {
            string.append(diversList.get(i).toXML(formatter));
        }
        string.append("</cv24:divers>");

        string.append("</cv24:cv24>");

        return string.toString();
    }

    public String toHTML() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD/MM/YYYY");
        StringBuilder string = new StringBuilder();

        string.append("<html><head><title>CV de " + prenom + " " + nom + "</title></head><body>");

        string.append("<h1>" + genre + " " + prenom + " " + nom + "</h1>");

        string.append("Tel : " + tel + "<br>");
        string.append("Mel: " + mel);

        string.append("<h2>Demande " + (objectifIsEmploi ? "d'emploi" : "de stage") + " : " + objectif + "</h2>");

        string.append("<h2>Expérience professionnelle</h2><ol>");
        int max = detailList.size();
        Detail detail;
        LocalDate date;
        for(int i = 0; i < max; i++) {
            detail = detailList.get(i);
            string.append("<li>" + detail.getTitre());

            date = detail.getDateFin();
            if (date != null) {
                string.append(" (du " + detail.getDateDeb().format(formatter) + " au " + detail.getDateDeb().format(formatter) + ")</li>");
            } else {
                string.append(" (depuis le " + detail.getDateDeb().format(formatter) + ")</li>");
            }
        }
        string.append("</ol>");

        List<Diplome> diplomes = new ArrayList<Diplome>();
        List<Certif> certifs = new ArrayList<Certif>();

        max = competenceList.size();
        for(int i = 0; i < max; i++) {
            if (competenceList.get(i).getClass() == Diplome.class) {
                diplomes.add((Diplome) competenceList.get(i));
            } else {
                certifs.add((Certif) competenceList.get(i));
            }
        }

        string.append("<h2>Diplômes</h2><table>");
        max = diplomes.size();
        for(int i = 0; i < max; i++) {
            string.append(diplomes.get(i).toHTML(formatter));
        }
        string.append("</table>");

        if (!certifs.isEmpty()) {
            string.append("<h2>Certifications</h2><table>");
            max = certifs.size();
            for (int i = 0; i < max; i++) {
                string.append(certifs.get(i).toHTML(formatter));
            }
            string.append("</table>");
        }

        List<LV> LVs = new ArrayList<LV>();
        List<Autre> autres = new ArrayList<Autre>();

        max = diversList.size();
        for(int i = 0; i < max; i++) {
            if (diversList.get(i).getClass() == LV.class) {
                LVs.add((LV) diversList.get(i));
            } else {
                autres.add((Autre) diversList.get(i));
            }
        }

        string.append("<h2>Langues</h2><ol>");
        max = LVs.size();
        for(int i = 0; i < max; i++) {
            string.append(LVs.get(i).toHTML(formatter));
        }
        string.append("</ol>");

        if (!autres.isEmpty()) {
            string.append("<h2>Divers</h2><ol>");
            max = autres.size();
            for(int i = 0; i < max; i++) {
                string.append(autres.get(i).toHTML(formatter));
            }
            string.append("</ol>");
        }


        string.append("</body></html>");

        return string.toString();
    }
}
