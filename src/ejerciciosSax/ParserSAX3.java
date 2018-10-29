package ejerciciosSax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSAX3 extends DefaultHandler{
	int cont = 0;
	String qName = "", titulo = "";
	int n;
	boolean leer = false;
	
	public ParserSAX3(int n) {
		this.n = n;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("pelicula")) {
			if(cont >= n) System.out.println(titulo);
			cont = 0;
			titulo = "";
		}
		this.qName="";
		leer = false;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("director")) cont++;
		else if (qName.equals("titulo")) leer = true;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(leer) titulo = new String(ch, start, length);
	}
	
}
