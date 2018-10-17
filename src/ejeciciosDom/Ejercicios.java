package ejeciciosDom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
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
		NodeList nietos;
		String nodeName;
		
		String titulo = null, nombre = null, apellido = null;
		
		for(int i = 0; i < pelis.getLength(); i++) {
			hijos = pelis.item(i).getChildNodes();
			for(int j = 0; j < hijos.getLength(); j++) {
				nodeName = hijos.item(j).getNodeName();
				if(nodeName.equals("titulo")) {
					titulo = hijos.item(j).getFirstChild().getNodeValue();
					System.out.printf("Título: %s\n",titulo);
				}
				if(nodeName.equals("director")) {
					nietos = hijos.item(j).getChildNodes();
					for(int k = 0; k < nietos.getLength(); k++) {
						nodeName = nietos.item(k).getNodeName();
						if(nodeName.equals("nombre")) {
							nombre = nietos.item(k).getFirstChild().getNodeValue();
							System.out.printf("Director: %s", nombre);
						}
						if(nodeName.equals("apellido")) {
							apellido = nietos.item(k).getFirstChild().getNodeValue();
							System.out.printf(" %s\n", apellido);
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	
	public void mostrarArbol(Node node, String tab) {
		System.out.printf("%s%d %s\n",tab,node.getNodeType(),node.getNodeName());
		
		NodeList hijos = node.getChildNodes();
		
		tab += "\t";
		
		for(int i = 0; i < hijos.getLength(); i++) {
			mostrarArbol(hijos.item(i),tab);
		}
	}
	
	
	public void masNDirectores(int n, NodeList pelis) {
		int count;
		String titulo="";
		NodeList hijos;
		
		for(int i = 0; i < pelis.getLength(); i++) {
			hijos  = pelis.item(i).getChildNodes();
			count = 0;
			for(int j = 0; j < hijos.getLength(); j++) {
				if(hijos.item(j).getNodeName().equals("titulo")) {
					titulo = hijos.item(j).getFirstChild().getNodeValue();
				}
				if(hijos.item(j).getNodeName().equals("director")) {
					count++;
					if(count > n) {
						System.out.println(titulo);
					}
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		Ejercicios ejer = new Ejercicios();
		
		String ruta = System.getProperty("user.home")+"\\Documents\\peliculas.xml";
		
		Document doc = ejer.crearArbol(ruta);
		
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		int ejercicio = 5;
		
		
		switch(ejercicio) {
			case 2:
				ejer.mostrarTitulos(pelis);
				break;
			case 3:
				ejer.mostrarPelisDirector(pelis);
				break;
			case 4:
				ejer.mostrarArbol(doc.getFirstChild(),"");
				break;
			case 5:
				ejer.masNDirectores(1, pelis);
		}
	}

}
