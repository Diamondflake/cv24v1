package fr.univrouen.cv24v1.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import fr.univrouen.cv24v1.model.TestCV;
import util.Fichier;

@RestController
public class GetController {
	
	@GetMapping("/resume")
	public String getListCVinXML() {
		return "Envoi de la liste des CVs";
	}
	
	@GetMapping("/cvid")
	public String getCVinXML(@RequestParam(value = "texte") String texte) {
		return "Détail du contenu du CV N°" + texte;
	}
	
	@GetMapping("/test")
	public String test(@RequestParam(value = "id") Integer id, @RequestParam(value = "titre") String titre) {
		return "Test:<br>"
				+ "id = " + id + "<br>"
				+ "titre = " + titre;
	}
	
	@GetMapping("/testfic")
	public String testFic() throws IOException {
		return Fichier.loadFileXML("smallCV.xml");
	}
	
	@RequestMapping(value="/testxml", produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody TestCV getXML() {
		TestCV cv = new TestCV("HAMILTON","John","1969/07/21", "Appollo11@nasa.us");
		return cv;
	}
}
