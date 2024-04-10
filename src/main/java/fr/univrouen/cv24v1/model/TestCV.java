package fr.univrouen.cv24v1.model;

import java.io.Serializable;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "TestCV")
@XmlAccessorType(XmlAccessType.NONE)
public class TestCV implements Serializable {

	private static final long serialVersionUID = 2024L;
	
	private static int compteur = 1;
	
	@XmlAttribute
	private Integer id;
	
	@XmlElement
	private String nom;
	
	@XmlElement
	private String prenom;
	
	@XmlElement
	private String datenais;
	
	@XmlElement
	private String mel;
	
	public TestCV(String nom, String prenom, String date, String mel) {
		super();
		this.id = compteur++;
		this.nom = nom;
		this.prenom = prenom;
		this.datenais = date;
		this.mel = mel;
	}
	
	public TestCV() {
		
	}
	
	@Override
	public String toString() {
	return ("CV (" + id + ") [" + nom + " " + prenom + " ,Date nais=" + datenais + " ,mel=" + mel);
	}
}
