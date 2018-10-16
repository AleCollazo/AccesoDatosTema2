package ejeciciosDom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
	
	public void mostrarTitulos(NodeList pelis) {
		NodeList hijos;
		
		for(int i = 0; i < pelis.getLength(); i++) {
			hijos = pelis.item(i).getChildNodes();
			for(int j = 0; j < hijos.getLength(); j++) {
				if(hijos.item(j).getNodeName().equals("titulo")) {
					System.out.println(hijos.item(j).getNodeName());
					System.out.println(hijos.item(j).getFirstChild().getNodeValue());
					System.out.println();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Ejercicios ejer = new Ejercicios();
		
		String ruta = System.getProperty("user.home")+"\\Documents\\peliculas.xml";
		
		Document doc = ejer.crearArbol(ruta);
		
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		ejer.mostrarTitulos(pelis);
		
		
	}

}
