package fr.univrouen.cv24v1.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.format.DateTimeFormatter;

public class LV implements Divers {
    private String lang;
    private String cert;
    private String nivs;
    private String nivi;

    public String getLang() {
        return lang;
    }

    public String getCert() {
        return cert;
    }

    public String getNivs() {
        return nivs;
    }

    public String getNivi() {
        return nivi;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public void setNivs(String nivs) {
        this.nivs = nivs;
    }

    public void setNivi(String nivi) {
        this.nivi = nivi;
    }

    public PreparedStatement getStatement(Connection conn, int id) throws SQLException {
        String query = "INSERT INTO lvs (id_cv, lang, cert, nivs, nivi) VALUES (?, ?, ?, ?, ?);";

        PreparedStatement statement = conn.prepareStatement(query);

        statement.setInt(1, id);
        statement.setString(2, lang);
        statement.setString(3, cert);
        if (nivs == null) {
            statement.setNull(4, Types.VARCHAR);
        } else {
            statement.setString(4, nivs);
        }
        if (nivi == null) {
            statement.setNull(5, Types.VARCHAR);
        } else {
            statement.setString(5, nivi);
        }

        return statement;
    }

    public String toXML(DateTimeFormatter formatter) {
        return "<cv24:lv lang=\"" + lang + "\" cert=\"" + cert + "\"" + ((nivs != null )? " nivs=\"" + nivs + "\"" : "" ) + ((nivi != null )? " nivi=\"" + nivi + "\"" : "" ) + "/>";
    }

    public String toHTML(DateTimeFormatter formatter) {
        return "<li>" + lang + " : " + cert + (((nivi != null) && (nivs != null)) ? " (" + nivi + " (NIVI), " + nivs + " (NIVS))" :
                (((nivi == null) && (nivs == null)) ? "" : " (" + ((nivs != null )? nivs + " (NIVS))" : nivi + " (NIVI))"))) + "</li>";
    }
}
