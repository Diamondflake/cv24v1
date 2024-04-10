package util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.core.io.DefaultResourceLoader;

public class Fichier {
	
	public static String loadFileXML(String fileName) throws IOException {
		
		org.springframework.core.io.Resource resource;
		
		resource = new DefaultResourceLoader().getResource(fileName);
		
		return resource.getContentAsString(Charset.defaultCharset());
	}
}
