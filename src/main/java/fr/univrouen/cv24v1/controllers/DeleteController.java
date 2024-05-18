package fr.univrouen.cv24v1.controllers;

import fr.univrouen.cv24v1.util.XMLDeleteResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteController {
    @DeleteMapping(path = "/cv24/delete", produces = "application/xml")
    public String deleteCV(@RequestParam(value = "id") int id) {
        DatabaseController controller = new DatabaseController();

        XMLDeleteResponse response = new XMLDeleteResponse();

        switch (controller.delete(id)) {
            case 1:
                response.setStatus("DELETED");
                response.setDetail("Le CV a bien été supprimé.");
                break;
            case 0:
                response.setStatus("ERROR");
                response.setDetail("Le CV n'existe pas.");
                break;
            default:
                response.setStatus("ERROR");
                response.setDetail("Erreur interne.");
        }
        response.setID(id);
        return response.toXML();
    }
}
