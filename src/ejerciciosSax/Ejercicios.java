package ejerciciosSax;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Ejercicios {
	
	public void getSax(String entradaXML) throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		ParserSAX4 parserSax = new ParserSAX4();
		parser.parse(entradaXML, parserSax);
	}
	
	
	
	public static void main(String[] args) {
		Ejercicios ejer = new Ejercicios();
		
		String entradaXML = System.getProperty("user.home")+"\\Documents\\peliculas.xml";
		
		try {
			ejer.getSax(entradaXML);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}
