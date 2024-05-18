package fr.univrouen.cv24v1.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public interface Competence extends CVElement {
    public PreparedStatement getStatement(Connection conn, int id, DateTimeFormatter formatter) throws SQLException;

    public String toXML(DateTimeFormatter formatter);

    public String toHTML(DateTimeFormatter formatter);
}
