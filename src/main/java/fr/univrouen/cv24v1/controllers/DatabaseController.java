package fr.univrouen.cv24v1.controllers;

import fr.univrouen.cv24v1.model.*;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

    String connectionUrl;

    String error;
    boolean errored;

    public DatabaseController() {
        error = "";
        errored = false;
        connectionUrl = "jdbc:mysql://localhost:3306/test_XML";
    }

    private Connection getConn() throws SQLException {
        return DriverManager.getConnection(connectionUrl, "root", "password");
    }

    public int exists(CV cv) {
        try {
            Connection conn = getConn();

            //String query = "SELECT id FROM cvs WHERE (genre = \'" + cv.getGenre() + "\' AND nom = \'" + cv.getNom()
                    //+ "\' AND prenom = \'" + cv.getPrenom() + "\' AND tel = \'" + cv.getTel() + "\');";

            String query = "SELECT id FROM cvs WHERE (genre = ?) AND (nom = ?) AND (prenom = ?) AND (tel = ?);";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, cv.getGenre());
            statement.setString(2, cv.getNom());
            statement.setString(3, cv.getPrenom());
            statement.setString(4, cv.getTel());

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return 1;
            } else {
                return 0;
            }
        } catch(SQLException e) {
            System.out.println("SQL exception during existence check : " + e.getMessage());
            return -1;
        }
    }

    public int saveCV(CV cv) {
        int id = -1;

        try {
            Connection conn = getConn();
            conn.setAutoCommit(false);

            //String query = "INSERT INTO cvs (genre, nom, prenom, tel, mail, statutObjectif, contenuObjectif) VALUES (\'"
                    //+ cv.getGenre() + "\', \'" + cv.getNom() + "\', \'" + cv.getPrenom() + "\', \'" + cv.getTel() + "\', \'" + cv.getMel() + "\', " + (cv.isObjectifEmploi() ? "true" : "false") + ", \'" + cv.getObjectif() + "\');";

            String query = "INSERT INTO cvs (genre, nom, prenom, tel, mail, statutObjectif, contenuObjectif) VALUES (?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, cv.getGenre());
            ps.setString(2, cv.getNom());
            ps.setString(3, cv.getPrenom());
            ps.setString(4, cv.getTel());
            ps.setString(5, cv.getMel());
            ps.setBoolean(6, cv.isObjectifEmploi());
            ps.setString(7, cv.getObjectif());

            ps.execute();

            query = "SELECT LAST_INSERT_ID() ;";

            ps = conn.prepareStatement(query);

            ResultSet result = ps.executeQuery();

            result.next();
            id = result.getInt(1);

            List<Detail> detailList = cv.getDetails();
            int max = detailList.size();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");

            for(int i = 0; i < max; i++) {
                Detail detail = detailList.get(i);
                //query = "INSERT INTO details (id_cv, datedeb, datefin, titre) VALUES ("
                        //+ id + ", \'" + detail.getDateDeb().format(formatter) + "\', " + (detail.getDateFin() == null ? "NULL" : "\'" + detail.getDateFin().format(formatter) + "\'") + ", \'" + detail.getTitre() + "\');";

                query = "INSERT INTO details (id_cv, datedeb, datefin, titre) VALUES (?, ?, ?, ?);";
                ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                ps.setString(2, detail.getDateDeb().format(formatter));
                if (detail.getDateFin() == null) {
                    ps.setNull(3, Types.DATE);
                } else {
                    ps.setString(3, detail.getDateFin().format(formatter));
                }

                ps.setString(4, detail.getTitre());

                ps.execute();
            }



            List<Competence> competenceList = cv.getCompetences();
            max = competenceList.size();

            for(int i = 0; i < max; i++) {
                Competence competence = competenceList.get(i);

                ps = competence.getStatement(conn, id, formatter);

                ps.execute();
            }



            List<Divers> diversList = cv.getDivers();
            max = diversList.size();

            for(int i = 0; i < max; i++) {
                Divers divers = diversList.get(i);

                ps = divers.getStatement(conn, id);

                ps.execute();
            }

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;

        }

        return id;
    }

    public CV load(int id) {
        CV cv = new CV();
        String query = "";

        try {
            Connection conn = getConn();

            query = "SELECT genre, nom, prenom, tel, mail, statutObjectif, contenuObjectif FROM cvs WHERE id = " + id + ";";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                return null;
            }

            cv.setGenre(result.getString(1));
            cv.setNom(result.getString(2));
            cv.setPrenom(result.getString(3));
            cv.setTel(result.getString(4));
            cv.setMel(result.getString(5));

            cv.setObjectifType(result.getBoolean(6));

            cv.setObjectif(result.getString(7));



            query = "SELECT datedeb, datefin, titre FROM details WHERE id_cv = " + id + ";";

            statement = conn.prepareStatement(query);

            result = statement.executeQuery();

            Detail detail;
            while (result.next()) {
                detail = new Detail();

                detail.setDateDeb(result.getDate(1).toLocalDate());
                Date dateFin = result.getDate(2);
                if (dateFin != null) {
                    detail.setDateFin(dateFin.toLocalDate());
                }
                detail.setTitre(result.getString(3));

                cv.addDetail(detail);
            }



            query = "SELECT titre, date, institut, niveau FROM diplomes WHERE id_cv = " + id + ";";

            statement = conn.prepareStatement(query);

            result = statement.executeQuery();

            Diplome diplome;
            while (result.next()) {
                diplome = new Diplome();

                diplome.setTitre(result.getString(1));
                diplome.setDate(result.getDate(2).toLocalDate());
                diplome.setInstitut(result.getString(3));
                diplome.setNiveau(result.getInt(4));

                cv.addCompetence(diplome);
            }



            query = "SELECT titre, datedeb, datefin FROM certifs WHERE id_cv = " + id + ";";

            statement = conn.prepareStatement(query);

            result = statement.executeQuery();

            Certif certif;
            while (result.next()) {
                certif = new Certif();

                certif.setTitre(result.getString(1));
                certif.setDateDeb(result.getDate(2).toLocalDate());
                Date dateFin = result.getDate(3);
                if (dateFin != null) {
                    certif.setDateFin(dateFin.toLocalDate());
                }

                cv.addCompetence(certif);
            }



            query = "SELECT lang, cert, nivs, nivi FROM lvs WHERE id_cv = " + id + ";";

            statement = conn.prepareStatement(query);

            result = statement.executeQuery();

            LV lv;
            while (result.next()) {
                lv = new LV();

                lv.setLang(result.getString(1));
                lv.setCert(result.getString(2));
                String nivs = result.getString(3);
                if (nivs != null) {
                    lv.setNivs(nivs);
                }
                String nivi = result.getString(4);
                if (nivi != null) {
                    lv.setNivi(nivi);
                }

                cv.addDivers(lv);
            }



            query = "SELECT titre, comment FROM autres WHERE id_cv = " + id + ";";

            statement = conn.prepareStatement(query);

            result = statement.executeQuery();

            Autre autre;
            while (result.next()) {
                autre = new Autre();

                autre.setTitre(result.getString(1));
                String comment = result.getString(2);
                if (comment != null) {
                    autre.setComment(comment);
                }

                cv.addDivers(autre);
            }

            conn.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println("for query: " + query);
            return null;
        }

        return cv;
    }

    public List<CVHeader> getCVList() {
        List<CVHeader> list = new ArrayList<CVHeader>();

        String query = "";

        CVHeader currHeader;
        int id;

        try {
            Connection conn = getConn();

            query = "SELECT id, genre, nom, prenom, tel, mail, statutObjectif, contenuObjectif FROM cvs;";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            ResultSet subresult;
            Diplome diplome;

            while (result.next()) {
                currHeader = new CVHeader();

                id = result.getInt(1);

                currHeader.setID(id);
                currHeader.setGenre(result.getString(2));
                currHeader.setNom(result.getString(3));
                currHeader.setPrenom(result.getString(4));
                currHeader.setTel(result.getString(5));
                currHeader.setMel(result.getString(6));

                currHeader.setObjectifType(result.getBoolean(7));

                currHeader.setObjectif(result.getString(8));

                query = "SELECT titre, date, institut, niveau FROM diplomes WHERE id_cv = " + id + " ORDER BY niveau DESC LIMIT 1;";

                statement = conn.prepareStatement(query);

                subresult = statement.executeQuery();

                if (subresult.next()) {
                    diplome = new Diplome();

                    diplome.setTitre(subresult.getString(1));
                    diplome.setDate(subresult.getDate(2).toLocalDate());
                    diplome.setInstitut(subresult.getString(3));
                    diplome.setNiveau(subresult.getInt(4));

                    currHeader.setHeadDiplome(diplome);
                }

                list.add(currHeader);
            }

            conn.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println("for query: " + query);
            return null;
        }

        return list;
    }

    public int delete(int id) {
        String query = "";

        try {
            Connection conn = getConn();

            query = "SELECT COUNT(*) FROM cvs where id = " + id + ";";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            result.next();

            if ((result.getInt(1)) == 0) {
                return 0;
            }

            query = "DELETE FROM cvs WHERE id = " + id + ";";

            statement = conn.prepareStatement(query);

            statement.execute();

            conn.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println("for query: " + query);
            return -1;
        }

        return 1;
    }
}
