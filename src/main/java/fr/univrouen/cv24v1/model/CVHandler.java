package fr.univrouen.cv24v1.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CVHandler extends DefaultHandler {
    // STATIC

    protected static final String CV = "cv24";
    protected static final String IDENTITE = "cv24:identite";
        protected static final String GENRE = "cv24:genre";
        protected static final String NOM = "cv24:nom";
        protected static final String PRENOM = "cv24:prenom";
        protected static final String TEL = "cv24:tel";
        protected static final String MEL = "cv24:mel";
    protected static final String OBJECTIF = "cv24:objectif";
    protected static final String PROF = "cv24:prof";
        protected static final String DETAIL = "cv24:detail";
            protected static final String DATEDEB = "cv24:datedeb";
            protected static final String DATEFIN = "cv24:datefin";
            protected static final String TITRE128 = "cv24:titre128";
    protected static final String COMPETENCES = "cv24:competences";
        protected static final String DIPLOME = "cv24:diplome";
            protected static final String DATE = "cv24:date";
            protected static final String INSTITUT = "cv24:institut";
        protected static final String CERTIF = "cv24:certif";
            protected static final String TITRE32 = "cv24:titre32";
    protected static final String DIVERS = "cv24:divers";
        protected static final String LV = "cv24:lv";
        protected static final String AUTRE = "cv24:autre";

    // Variables

    protected DateTimeFormatter dateFormatter;

    protected StringBuilder value;

    protected CV cv;

    protected Detail currDetail;
    protected Diplome currDiplome;
    protected Certif currCertif;

    protected LV currLV;
    protected Autre currAutre;

    protected boolean lastEncounteredDateFieldIsDetail;



    // Getter

    public CV getCV() {
        return cv;
    }

    // DefaultHandler

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (value == null) {
            value = new StringBuilder();
        } else {
            value.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        cv = new CV();
        dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case CV:
                // Nothing
                break;
            case IDENTITE:
                // Nothing
                break;
            case GENRE:
                // Nothing
                break;
            case NOM:
                // Nothing
                break;
            case PRENOM:
                // Nothing
                break;
            case TEL:
                // Nothing
                break;
            case MEL:
                // Nothing
                break;
            case OBJECTIF:
                cv.setObjectifType(attr.getValue("statut").equals("emploi"));
                break;
            case PROF:
                // Nothing
                break;
            case DETAIL:
                currDetail = new Detail();
                lastEncounteredDateFieldIsDetail = true;
                break;
            case DATEDEB:
                // Nothing
                break;
            case DATEFIN:
                // Nothing
                break;
            case TITRE128:
                // Nothing
                break;
            case COMPETENCES:
                // Nothing
                break;
            case DIPLOME:
                currDiplome = new Diplome();
                currDiplome.setNiveau(Integer.parseInt(attr.getValue("niveau")));
                break;
            case DATE:
                currDiplome.setTitre(value.toString());
                break;
            case INSTITUT:
                // Nothing
                break;
            case CERTIF:
                currCertif = new Certif();
                lastEncounteredDateFieldIsDetail = false;
                break;
            case TITRE32:
                // Nothing
                break;
            case DIVERS:
                // Nothing
                break;
            case LV:
                currLV = new LV();
                currLV.setLang(attr.getValue("lang"));
                currLV.setCert(attr.getValue("cert"));
                String nivs = attr.getValue("nivs");
                if (nivs != null) {
                    currLV.setNivs(nivs);
                }
                String nivi = attr.getValue("nivi");
                if (nivi != null) {
                    currLV.setNivi(nivi);
                }
                break;
            case AUTRE:
                currAutre = new Autre();
                currAutre.setTitre(attr.getValue("titre"));
                String comment = attr.getValue("comment");
                if (comment != null) {
                    currAutre.setComment(comment);
                }
                break;
        }
        value = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case CV:

                break;
            case IDENTITE:
                // Nothing
                break;
            case GENRE:
                cv.setGenre(value.toString());
                break;
            case NOM:
                cv.setNom(value.toString());
                break;
            case PRENOM:
                cv.setPrenom(value.toString());
                break;
            case TEL:
                cv.setTel(value.toString());
                break;
            case MEL:
                cv.setMel(value.toString());
                break;
            case OBJECTIF:
                cv.setObjectif(value.toString());
                break;
            case PROF:
                // Nothing
                break;
            case DETAIL:
                cv.addDetail(currDetail);
                break;
            case DATEDEB:
                if (!value.isEmpty()) {
                    if (lastEncounteredDateFieldIsDetail) {
                        currDetail.setDateDeb(LocalDate.parse(value.toString(), dateFormatter));
                    } else {
                        currCertif.setDateDeb(LocalDate.parse(value.toString(), dateFormatter));
                    }
                }
                break;
            case DATEFIN:
                if (!value.isEmpty()) {
                    if (lastEncounteredDateFieldIsDetail) {
                        currDetail.setDateFin(LocalDate.parse(value.toString(), dateFormatter));
                    } else {
                        currCertif.setDateFin(LocalDate.parse(value.toString(), dateFormatter));
                    }
                }
                break;
            case TITRE128:
                currDetail.setTitre(value.toString());
                break;
            case COMPETENCES:
                // Nothing
                break;
            case DIPLOME:
                cv.addCompetence(currDiplome);
                break;
            case DATE:
                if (!value.isEmpty()) {
                    currDiplome.setDate(LocalDate.parse(value.toString(), dateFormatter));
                }
                break;
            case INSTITUT:
                if (!value.isEmpty()) {
                    currDiplome.setInstitut(value.toString());
                }
                break;
            case CERTIF:
                cv.addCompetence(currCertif);
                break;
            case TITRE32:
                currCertif.setTitre(value.toString());
                break;
            case DIVERS:
                // Nothing
                break;
            case LV:
                cv.addDivers(currLV);
                break;
            case AUTRE:
                cv.addDivers(currAutre);
                break;
        }
    }
}
