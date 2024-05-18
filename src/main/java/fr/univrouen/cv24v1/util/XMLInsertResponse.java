package fr.univrouen.cv24v1.util;

public class XMLInsertResponse implements XMLResponse {
    private String status;
    private String detail;
    private int id;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String toXML() {
        StringBuilder builder = new StringBuilder();

        builder.append("<response>");
            builder.append("<status>" + status + "</status>");
            if (detail != null) {
                builder.append("<detail>" + detail + "</detail>");
            }
            if (id != 0) {
                builder.append("<id>" + id + "</id>");
            }
        builder.append("</response>");

        return builder.toString();
    }

    public String toHTML() {
        StringBuilder builder = new StringBuilder();

        builder.append("<html><head><title>" + status + "</title></head><body>");
        builder.append("<b>Réponse</b>: " + status + "<br>");
        builder.append("<b>Pour</b>: Insertion d'un CV<br>");
        if (detail != null) {
            builder.append("<b>Raison</b>:" + detail + "<br>");
        }
        builder.append("</body></html>");

        return builder.toString();
    }
}
