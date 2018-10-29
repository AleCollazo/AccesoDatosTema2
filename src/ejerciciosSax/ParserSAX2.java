package ejerciciosSax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSAX2 extends DefaultHandler{

	String qName = "";
	String genero = "";
	boolean leer = false;
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(leer) {
			if(qName.equals("titulo")) System.out.printf("Título: %s -> ", new String(ch, start, length));
			else if(qName.equals("nombre")) System.out.printf("Nombre: %s   ", new String(ch, start, length));
			else if(qName.equals("apellido")) System.out.printf("Apellido: %s     ", new String(ch, start, length));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("pelicula")) System.out.printf("Genero: %s\n\n",genero);
		this.qName="";
		leer = false;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("pelicula")) {
			for(int i = 0; i < attributes.getLength(); i++) {
				if(attributes.getQName(i).equals("genero")) genero = attributes.getValue(i);
			}
		}
		this.qName = qName;
		
		leer = true;
	}
	
}
