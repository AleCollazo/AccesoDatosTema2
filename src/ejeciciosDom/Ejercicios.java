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
	
	
	public void mostrarPelisDirector(NodeList pelis) {
		NodeList hijos;
		String nodeName;
		
		String titulo = null, nombre = null, apellido = null;
		
		for(int i = 0; i < pelis.getLength(); i++) {
			hijos = pelis.item(i).getChildNodes();
			for(int j = 0; j < hijos.getLength(); j++) {
				nodeName = hijos.item(j).getNodeName();
				if(nodeName.equals("titulo")) {
					titulo = hijos.item(j).getFirstChild().getNodeValue();
				}
				if(nodeName.equals("director")) {
					hijos = hijos.item(j).getChildNodes();
					for(int k = 0; k < hijos.getLength(); k++) {
						nodeName = hijos.item(k).getNodeName();
						if(nodeName.equals("nombre")) {
							nombre = hijos.item(k).getFirstChild().getNodeValue();
						}
						if(nodeName.equals("apellido")) {
							apellido = hijos.item(k).getFirstChild().getNodeValue();
						}
					}
				}
			}
			
			System.out.printf("Título: %s\nDirector: %s %s\n\n",titulo, nombre, apellido);
			
		}
	}
	
	
	public static void main(String[] args) {
		Ejercicios ejer = new Ejercicios();
		
		String ruta = System.getProperty("user.home")+"\\Documents\\peliculas.xml";
		
		Document doc = ejer.crearArbol(ruta);
		
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		int ejercicio = 4;
		
		
		switch(ejercicio) {
			case 3:
				ejer.mostrarTitulos(pelis);
				break;
			case 4:
				ejer.mostrarPelisDirector(pelis);
				break;
		}
	}

}
