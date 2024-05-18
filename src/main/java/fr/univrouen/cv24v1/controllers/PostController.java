package fr.univrouen.cv24v1.controllers;

import fr.univrouen.cv24v1.model.CV;

import fr.univrouen.cv24v1.model.CVHandler;
import fr.univrouen.cv24v1.util.XMLInsertResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;

@RestController
public class PostController {
	@RequestMapping(value = "/cv24/insert", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	public String postTest(@RequestBody String flux) throws Exception {
		XMLInsertResponse response = new XMLInsertResponse();

		/*try  {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			File schemaFile = new File("./src/main/resources/static/cv24schema.xsd");
			Schema schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(new StringReader(flux));

			validator.validate(source);
		} catch (SAXException e) {
			System.out.println("SAXException during insertion: " + e.getMessage());

			response.setStatus("ERROR");
			response.setDetail("INVALID");

			return response.toXML();
		}*/

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        CVHandler handler = new CVHandler();

        saxParser.parse(new InputSource(new StringReader(flux)), handler);

        CV cv = handler.getCV();

		DatabaseController controller = new DatabaseController();

		switch (controller.exists(cv)) {
			case 1:
				// Exists
				response.setStatus("ERROR");
				response.setDetail("Un CV pour cette personne existe déjà.");
				break;
			case 0:
				// Doesn't exist
				response.setID(controller.saveCV(cv));
				response.setStatus("INSERTED");
				break;
			default:
				response.setStatus("ERROR");
				response.setDetail("Erreur interne.");
		}
		return response.toXML();
    }
}
