package fr.univrouen.cv24v1.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.format.DateTimeFormatter;

public class Autre implements Divers {
    private String titre;
    private String comment;

    public String getTitre() {
        return titre;
    }

    public String getComment() {
        return comment;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PreparedStatement getStatement(Connection conn, int id) throws SQLException {
        String query = "INSERT INTO autres (id_cv, titre, comment) VALUES (?, ?, ?);";

        PreparedStatement statement = conn.prepareStatement(query);

        statement.setInt(1, id);
        statement.setString(2, titre);
        if (comment == null) {
            statement.setNull(3, Types.VARCHAR);
        } else {
            statement.setString(3, comment);
        }

        return statement;
    }

    public String toXML(DateTimeFormatter formatter) {
        return "<cv24:autre titre=\"" + titre + "\"" + ((comment != null ) ? "comment=\"" + comment + "\"" : "") + "/>";
    }

    public String toHTML(DateTimeFormatter formatter) {
        return "<li>" + titre + ((comment != null ) ? " : " + comment : "") + "</li>";
    }
}
