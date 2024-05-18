package fr.univrouen.cv24v1.util;

public class XMLGetCVResponse implements XMLResponse {
    private int id;
    private String status;
    private String detail;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String toXML() {
        StringBuilder builder = new StringBuilder();

        builder.append("<response>");
        builder.append("<status>" + status + "</status>");
        if (id != 0) {
            builder.append("<id>" + id + "</id>");
        }
        if (detail != null) {
            builder.append("<detail>" + detail + "</detail>");
        }
        builder.append("</response>");

        return builder.toString();
    }

    public String toHTML() {
        StringBuilder builder = new StringBuilder();

        builder.append("<html><head><title>" + status + "</title></head><body>");
        builder.append("<b>Réponse</b>: " + status + "<br>");
        if (id != 0) {
            builder.append("<b>Pour</b>: Demande du CV d'ID " + id + "<br>");
        }
        if (detail != null) {
            builder.append("<b>Détail</b>: " + detail);
        }
        builder.append("</body></html>");

        return builder.toString();
    }
}
