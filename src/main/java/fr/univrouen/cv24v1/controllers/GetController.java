package fr.univrouen.cv24v1.controllers;

import fr.univrouen.cv24v1.model.CV;
import fr.univrouen.cv24v1.model.CVHeader;
import fr.univrouen.cv24v1.util.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class GetController {

	@GetMapping(path = "/cv24/resume/xml", produces = "application/xml") // Liste XML
	public String getCVListinXML() {
		DatabaseController controller = new DatabaseController();

		List<CVHeader> list = controller.getCVList();

		StringBuilder builder = new StringBuilder();

		int max = list.size();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		builder.append("<cv24:cv24headerlist xmlns:cv24=\"http://univ.fr/cv24\">");

		for (int i = 0; i < max; i++) {
			builder.append(list.get(i).toXML());
		}

		builder.append("</cv24:cv24headerlist>");

		return builder.toString();
	}

	@GetMapping(path = "/cv24/resume", produces = MediaType.TEXT_HTML_VALUE) // Liste HTML
	public String getCVListinHTML() {
		DatabaseController controller = new DatabaseController();

		List<CVHeader> list = controller.getCVList();

		StringBuilder builder = new StringBuilder();

		int max = list.size();
		builder.append("<html><head><title>Liste des CVs</title><style>table, th, td {border: 1px solid; border-collapse: collapse; padding: 5px;}</style></head>");

		builder.append("<body><table><tr><th>ID du CV</th><th>Identité</th><th>Objectif</th><th>Diplôme le plus élevé</th></tr>");
		for (int i = 0; i < max; i++) {
			builder.append(list.get(i).toHTML());
		}

		builder.append("</table></body></html>");

		return builder.toString();
	}

	@GetMapping(path = "/cv24/xml", produces = "application/xml")
	public String getCVinXML(@RequestParam(value = "id") int id) {
		DatabaseController controller = new DatabaseController();

		CV cv = controller.load(id);

		if (cv == null) {
			XMLGetCVResponse response = new XMLGetCVResponse();

			response.setStatus("ERROR");
			response.setDetail("Le CV n'existe pas ou n'a pas pu être chargé.");
			response.setID(id);

			return response.toXML();
		} else {
			return cv.toXML();
		}
	}

	@GetMapping(path = "/cv24/html", produces = MediaType.TEXT_HTML_VALUE)
	public String getCVinHTML(@RequestParam(value = "id") int id) {
		DatabaseController controller = new DatabaseController();

		CV cv = controller.load(id);

		if (cv == null) {
			XMLGetCVResponse response = new XMLGetCVResponse();

			response.setStatus("ERROR");
			response.setDetail("Le CV n'existe pas ou n'a pas pu être chargé.");
			response.setID(id);

			return response.toHTML();
		} else {
			return cv.toHTML();
		}
	}
}
