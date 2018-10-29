package ejerciciosSax;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSAX4 extends DefaultHandler{

	ArrayList<String> generos = new ArrayList<>();
	
	String genero = "";
	
	int count = 0;
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("pelicula")) {
			if(!generos.contains(genero)) generos.add(genero);
			genero = "";
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("pelicula")) {
			for(int i = 0; i < attributes.getLength(); i++) {
				if(attributes.getQName(i).equals("genero")) genero = attributes.getValue(i);
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.printf("Número de géneros: %d\nGeneros:\n", generos.size());
		for(String v:generos) {
			System.out.println("- "+v);
		}
	}
	
}
