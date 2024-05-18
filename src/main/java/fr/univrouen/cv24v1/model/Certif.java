package fr.univrouen.cv24v1.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Certif implements Competence {
    private LocalDate dateDeb;
    private LocalDate dateFin;

    private String titre;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDateDeb(LocalDate dateDeb) {
        this.dateDeb = dateDeb;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateDeb() {
        return dateDeb;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public PreparedStatement getStatement(Connection conn, int id, DateTimeFormatter formatter) throws SQLException {
        String query = "INSERT INTO certifs (id_cv, titre, datedeb, datefin) VALUES (?, ?, ?, ?);";

        PreparedStatement statement = conn.prepareStatement(query);

        statement.setInt(1, id);
        statement.setString(2, titre);
        statement.setString(3, dateDeb.format(formatter));
        if (dateFin == null) {
            statement.setNull(4, Types.DATE);
        } else {
            statement.setString(4, getDateFin().format(formatter));
        }


        return statement;
    }

    public String toXML(DateTimeFormatter formatter) {
        return "<cv24:certif><cv24:datedeb>" + dateDeb.format(formatter) + "</cv24:datedeb>" + ((dateFin != null) ? "<cv24:datefin>" + dateFin.format(formatter) + "</cv24:datefin>" : "") + "<cv24:titre32>" + titre + "</cv24:titre32></cv24:certif>";
    }

    public String toHTML(DateTimeFormatter formatter) {
        return "<tr><td>" + dateDeb.format(formatter) + (dateFin != null ? "-" + dateFin.format(formatter) : "") + "</td><td>" + titre + "</td></tr>";
    }
}
