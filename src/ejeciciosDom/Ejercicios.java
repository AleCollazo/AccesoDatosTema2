package ejeciciosDom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Ejercicios {
	public Document crearArbol(String ruta) {
		Document doc = null;
		try {
			DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
			factoria.setIgnoringComments(true);
			DocumentBuilder builder = factoria.newDocumentBuilder();
			doc = builder.parse(ruta);
		} catch(Exception e) {
			System.out.println("Error generando el árbol DOM: "+e.getMessage());
		}
		return doc;
	}
	
	
	public static void main(String[] args) {
		Ejercicios ejer = new Ejercicios();
		
		String ruta = System.getProperty("User.home")+"\\Documents\\peliculas.xml";
		
		Document doc = ejer.crearArbol(ruta);
		
		
	}

}
