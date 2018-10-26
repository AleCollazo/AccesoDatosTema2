package ejerciciosSax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSax2 extends DefaultHandler{

	String qName = "";
	boolean leer = false;
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(leer) {
			if(qName.equals("titulo")) System.out.printf("Título: %s", new String(ch, start, length));
			else if(qName.equals("nombre")) System.out.printf("Nombre: %s", new String(ch, start, length));
			else if(qName.equals("apellidos")) System.out.printf("Apellidos: ", new String(ch, start, length));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
	}
	
}
