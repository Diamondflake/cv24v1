package fr.univrouen.cv24v1.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Diplome implements Competence {
    private String titre;
    private LocalDate date;
    private String institut;
    private int niveau;

    public String getTitre() {
        return titre;
    }
    public LocalDate getDate() {
        return date;
    }

    public String getInstitut() {
        return institut;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setInstitut(String institut) {
        this.institut = institut;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public PreparedStatement getStatement(Connection conn, int id, DateTimeFormatter formatter) throws SQLException {
        String query = "INSERT INTO diplomes (id_cv, titre, date, institut, niveau) VALUES (?, ?, ?, ?, ?);";

        PreparedStatement statement = conn.prepareStatement(query);

        statement.setInt(1, id);
        statement.setString(2, titre);
        statement.setString(3, date.format(formatter));
        if (institut == null) {
             statement.setNull(4, Types.VARCHAR);
        } else {
            statement.setString(4, institut);
        }

        statement.setInt(5, niveau);

        return statement;
    }

    public String toXML(DateTimeFormatter formatter) {
        return "<cv24:diplome niveau=\"" + niveau + "\">" + titre + "<cv24:date>" + date.format(formatter) + ((institut != null) ? "</cv24:date><cv24:institut>" + institut + "</cv24:institut></cv24:diplome>" : "</cv24:diplome>");
    }

    public String toXMLHeadDiplome(DateTimeFormatter formatter) {
        return "<cv24:headdiplome niveau=\"" + niveau + "\">" + titre + "<cv24:date>" + date.format(formatter) + ((institut != null) ? "</cv24:date><cv24:institut>" + institut + "</cv24:institut></cv24:diplome>" : "</cv24:headdiplome>");
    }

    public String toHTML(DateTimeFormatter formatter) {
        return "<tr><td>" + date.format(formatter) + "</td><td>" + titre + "</td><td>(" + niveau + ")</td><td>" + ((institut != null) ? institut : "") + "</td></tr>";
    }
}
