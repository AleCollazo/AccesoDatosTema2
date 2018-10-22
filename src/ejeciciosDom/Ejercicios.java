package ejeciciosDom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

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
	
	
	public void contarGeneros(NodeList pelis) {
		NamedNodeMap attribs;
		ArrayList<String> generos = new ArrayList<>();
		boolean existe;
		
		for(int i = 0; i < pelis.getLength(); i++) {
			attribs = pelis.item(i).getAttributes();
			for(int j = 0; j < attribs.getLength(); j++) {
				if(attribs.item(j).getNodeName().equals("genero")) {
					if(generos.isEmpty()) {
						generos.add(attribs.item(j).getNodeValue());
					}
					else {
						existe = false;
						for(int k = 0; k < generos.size(); k++) {
							if(attribs.item(j).getNodeValue().equals(generos.get(k))) {
								existe = true;
							}
						}
						if(!existe) generos.add(attribs.item(j).getNodeValue());
					}
				}
			}
		}
		
		System.out.printf("¿Cuántos géneros diferentes de películas hay?\n%d\n\n", generos.size());
		
		System.out.println("¿Cuáles son?");
		for(int i = 0; i < generos.size(); i++) {
			System.out.println(generos.get(i));
		}
	}
	
	
	
	public boolean añadirAtributo(NodeList pelis, String titulo, String atributo, String valorAtrib) {
		boolean modificado = false;
		
		try {	
			for(int i = 0; i < pelis.getLength(); i++) {
				System.out.println(pelis.item(i).getFirstChild().getNodeValue()); //TODO Coger el título de los hijos
				if(pelis.item(i).getFirstChild().getNodeValue().equals(titulo)) {
					if(!((Element)pelis.item(i)).hasAttribute(atributo)) {
						((Element)pelis.item(i)).setAttribute(atributo, valorAtrib);
						modificado = true;
					}
				}
			}
			return modificado;
		} catch(DOMException e) {
			System.err.println(e.getMessage());
			return false;
		}
		
	}
	
	public boolean eliminarPelicula(NodeList pelis, String titulo, String atributo) {
		boolean modificado = false;
		
		try {
			for(int i = 0; i < pelis.getLength(); i++) {
				System.out.println(pelis.item(i).getFirstChild().getNodeValue());
				if(pelis.item(i).getFirstChild().getNodeValue().equals(titulo)) {
					if(((Element)pelis.item(i)).hasAttribute(atributo)) {
						pelis.item(i).getParentNode().removeChild(pelis.item(i));
						modificado = true;
					}
				}
			}
			return modificado;
		} catch(DOMException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	
	public boolean añadirPeli(Document doc, String titulo, String dirNombre, String dirApellido, String año, String genero, String idioma) {
		try {
			Element nodoPelicula = doc.createElement("Pelicula");
			
			nodoPelicula.setAttribute("genero", genero);
			
			// Titulo
			Element nodoTitulo = doc.createElement("Titulo");
			Text textTitulo = doc.createTextNode(titulo);
			nodoTitulo.appendChild(textTitulo);
			nodoPelicula.appendChild(nodoTitulo);
			
			// Director
			Element nodoDirector = doc.createElement("Director");
			Element nodoNombre = doc.createElement("Nombre");
			Element nodoApellido = doc.createElement("Apellido");
			Text textNombre = doc.createTextNode(dirNombre);
			Text textApellido = doc.createTextNode(dirApellido);
			nodoNombre.appendChild(textNombre);
			nodoApellido.appendChild(textApellido);
			
			nodoDirector.appendChild(nodoNombre);
			nodoDirector.appendChild(nodoApellido);
			nodoPelicula.appendChild(nodoDirector);
			
			// Atributos
			nodoPelicula.setAttribute("año", año);
			nodoPelicula.setAttribute("genero", genero);
			nodoPelicula.setAttribute("idioma", idioma);
			
			Node raiz = doc.getFirstChild();
			raiz.appendChild(nodoPelicula);
			
			return true;
		}
		catch(DOMException e) {
			return false;
		}
	}
	
	
	public void grabarDOM(Document doc, String ficheroSalida) throws
	ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException{
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0 LS 3.0");
		
		LSOutput output = ls.createLSOutput();
		output.setEncoding("UTF-8");
		
		output.setByteStream(new FileOutputStream(ficheroSalida));
		
		LSSerializer serializer = ls.createLSSerializer();
		
		serializer.setNewLine("\r\n");
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		
		serializer.write(doc, output);
	}
	
	
	public static void main(String[] args) {
		Ejercicios ejer = new Ejercicios();
		
		String ruta = System.getProperty("user.home")+"\\Documents\\peliculas.xml";
		String rutaSalida = System.getProperty("user.home")+"\\Documents\\peliculasSalida.xml";
		
		Document doc = ejer.crearArbol(ruta);
		
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		int ejercicio = 8;
		
		
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
				break;
			case 6:
				ejer.contarGeneros(pelis);
				break;
			case 7:
				System.out.println(ejer.añadirAtributo(pelis, "Dune", "Prueba", "Valor de prueba"));
				System.out.println(ejer.eliminarPelicula(pelis, "Dune", "año"));
				break;
			case 8:
				ejer.añadirPeli(doc, "Depredador", "Jhon", "Tiernan", "1987", "acción", "en");
				
				try {
					ejer.grabarDOM(doc, rutaSalida);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				String nombre = "Larry";
				String apellido = "Wachowski";
				String nombreNuevo = "Lana";
				
				boolean flag = false;
				
				NodeList directores = doc.getElementsByTagName("director");
				
				for(int i = 0; i < directores.getLength(); i++) {
					NodeList hijos = directores.item(i).getChildNodes();
					for(int j = 0; j < hijos.getLength(); j++) {
						if(hijos.item(j).getNodeName().equals("nombre")) {
							if(hijos.item(j).getFirstChild().equals(nombre)) {
								flag = true;
							}
						}
						if(hijos.item(j).getNodeName().equals("apellido")) {
							if(hijos.item(j).getFirstChild().equals(apellido)) {
								flag = true;
							}
						}
						if(flag) {
							((Node)((Element) hijos.item(j)).getElementsByTagName("nombre")).appendChild(doc.createTextNode(nombreNuevo));
						}
					}
				}
				
				try {
					ejer.grabarDOM(doc, rutaSalida);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
		}
	}

}
