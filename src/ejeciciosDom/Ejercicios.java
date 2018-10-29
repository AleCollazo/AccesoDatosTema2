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
			factoria.setIgnoringComments(false);
			DocumentBuilder builder = factoria.newDocumentBuilder();
			doc = builder.parse(ruta);
		} catch(Exception e) {
			System.out.println("Error generando el árbol DOM: "+e.getMessage());
		}
		return doc;
	}
	
	public Document crearArbol() {
		Document doc = null;
		try {
			DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
			factoria.setIgnoringComments(false);
			DocumentBuilder builder = factoria.newDocumentBuilder();
			doc = builder.newDocument();
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
			 titulo="";
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
	
	
	public ArrayList<String> contarGeneros(NodeList pelis) {
		NamedNodeMap attribs;
		ArrayList<String> generos = new ArrayList<>();
		
		for(int i = 0; i < pelis.getLength(); i++) {
			attribs = pelis.item(i).getAttributes();
			for(int j = 0; j < attribs.getLength(); j++) {
				if(attribs.item(j).getNodeName().equals("genero")) {
					if(!generos.contains(attribs.item(j).getNodeValue())) {
						generos.add(attribs.item(j).getNodeValue());
					}
				}
			}
		}
		
		return generos;
	}
	
	
	
	public Document añadirAtributo(String titulo, String atributo, String valorAtrib, Document doc) {
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		NodeList hijosPelis;
		
		try {	
			for(int i = 0; i < pelis.getLength(); i++) {
				hijosPelis = pelis.item(i).getChildNodes();
				for(int j = 0; j < hijosPelis.getLength(); j++) {
					if(hijosPelis.item(j).getNodeName().equals("titulo")) {
						if(hijosPelis.item(j).getFirstChild().getNodeValue().equals(titulo)) {
							if(!((Element)pelis.item(i)).hasAttribute(atributo)) {
								((Element)pelis.item(i)).setAttribute(atributo, valorAtrib);
							}
						}
					}
				}
			}
		} catch(DOMException e) {
			System.err.println(e.getMessage());
		}
		return doc;
	}
	
	public Document eliminarPelicula(String titulo, String atributo, Document doc) {
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		NodeList hijosPelis;
		
		try {
			for(int i = 0; i < pelis.getLength(); i++) {
				hijosPelis = pelis.item(i).getChildNodes();
				for(int j = 0; j < hijosPelis.getLength(); j++) {
					if(hijosPelis.item(j).getNodeName().equals("titulo")) {
						if(hijosPelis.item(j).getFirstChild().getNodeValue().equals(titulo)) {
							if(((Element)pelis.item(i)).hasAttribute(atributo)) {
								pelis.item(i).getParentNode().removeChild(pelis.item(i));
							}
						}
					}
				}
			}
		} catch(DOMException e) {
			System.err.println(e.getMessage());
		}
		
		return doc;
	}
	
	public Document eliminarPelicula(String titulo, Document doc) {
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		NodeList hijosPelis;
		
		try {
			for(int i = 0; i < pelis.getLength(); i++) {
				hijosPelis = pelis.item(i).getChildNodes();
				for(int j = 0; j < hijosPelis.getLength(); j++) {
					if(hijosPelis.item(j).getNodeName().equals("titulo")) {
						if(hijosPelis.item(j).getFirstChild().getNodeValue().equals(titulo)) {
							pelis.item(i).getParentNode().removeChild(pelis.item(i));
						}
					}
				}
			}
		} catch(DOMException e) {
			System.err.println(e.getMessage());
		}
		
		return doc;
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
	
	
	public Document cambiarNombreDirector(String nombre, String apellido, String nombreNuevo, Document doc) {
		NodeList directores = doc.getElementsByTagName("director");
		
		boolean flagNom = false;
		boolean flagApe = false;
		
		for(int i = 0; i < directores.getLength(); i++) {
			NodeList hijos = directores.item(i).getChildNodes();
			for(int j = 0; j < hijos.getLength(); j++) {
				if(hijos.item(j).getNodeName().equals("nombre")) {
					//System.out.println("Nombre: "+hijos.item(j).getFirstChild().getNodeValue());
					if(hijos.item(j).getFirstChild().getNodeValue().equals(nombre)) {
						flagNom = true;
					}
					else flagNom = false;
				}
				if(hijos.item(j).getNodeName().equals("apellido")) {
					//System.out.println("Apellido: "+hijos.item(j).getFirstChild().getNodeValue());
					if(hijos.item(j).getFirstChild().getNodeValue().equals(apellido)) {
						flagApe = true;
					}
					else flagApe = false;
				}
				
				
				//System.out.printf("FlagNom: %b - FlagApe: %b\n %b\n\n",flagNom, flagApe, flagNom && flagApe);
				if(flagNom && flagApe) {
					NodeList nodeListNombres = ((Element) directores.item(i)).getElementsByTagName("nombre");
					
					//System.out.println(nodeListNombres.getLength());
					
					nodeListNombres.item(0).replaceChild(doc.createTextNode(nombreNuevo),nodeListNombres.item(0).getFirstChild());
					//System.out.println("Se sustituyó");
				}
				
			}
			flagNom = false; flagApe = false;
		}
		
		return doc;
	}
	
	public Document añadirDirector(String nomDirector, String apeDirector, String tituloPeli, Document doc) {
		
		NodeList pelis = doc.getElementsByTagName("pelicula");
		NodeList hijos;
		
		for(int i = 0; i < pelis.getLength(); i++) {
			hijos = pelis.item(i).getChildNodes();
			for(int j = 0; j < hijos.getLength(); j++) {
				if(hijos.item(j).getNodeName().equals("titulo")) {
					if(hijos.item(j).getFirstChild().getNodeValue().equals(tituloPeli)) {
						Node pelicula = pelis.item(i);
						
						Node director = doc.createElement("director");
						
						Node nomNodo = doc.createElement("nombre");
						Node apeNodo = doc.createElement("apellido");
						
						Node txtNom = doc.createTextNode(nomDirector);
						Node txtApe = doc.createTextNode(apeDirector);
						
						director.appendChild(nomNodo);
						director.appendChild(apeNodo);
						
						nomNodo.appendChild(txtNom);
						apeNodo.appendChild(txtApe);
						
						pelicula.appendChild(director);
						
						return doc;
					}

				}
			}
		}
		

		
		return doc;
	}
	
	public Document nuevoDocument(Document doc) {
		Node compañia = doc.createElement("compañia");
		
		Node empleado = doc.createElement("empleado");
		((Element) empleado).setAttribute("id", "1");
		
		Node nombre = doc.createElement("nombre");
		Node apellidos = doc.createElement("apellidos");
		Node apodo = doc.createElement("apodo");
		Node salario = doc.createElement("salario");
		
		Node txtNombre = doc.createTextNode("Juan");
		Node txtApellidos = doc.createTextNode("López Pérez");
		Node txtApodo = doc.createTextNode("Juanín");
		Node txtSalario = doc.createTextNode("1000");
		
		nombre.appendChild(txtNombre);
		apellidos.appendChild(txtApellidos);
		apodo.appendChild(txtApodo);
		salario.appendChild(txtSalario);
		
		empleado.appendChild(nombre);
		empleado.appendChild(apellidos);
		empleado.appendChild(apodo);
		empleado.appendChild(salario);
		
		compañia.appendChild(empleado);
		
		doc.appendChild(compañia);
		
		return doc;
	}
	
	
	public static void main(String[] args) {
		Ejercicios ejer = new Ejercicios();
		
		String ruta = System.getProperty("user.home")+"\\Documents\\peliculas.xml";
		String rutaSalida = System.getProperty("user.home")+"\\Documents\\peliculasSalida.xml";
		String rutaNuevo = System.getProperty("user.home")+"\\Documents\\docuNuevo.xml";
		
		Document doc = ejer.crearArbol(ruta);
		
		NodeList pelis = doc.getElementsByTagName("pelicula");
		
		int ejercicio = 11;
		
		
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
				ArrayList<String> generos = ejer.contarGeneros(pelis);
				
				System.out.printf("¿Cuántos géneros diferentes de películas hay?\n%d\n\n", generos.size());
				
				System.out.println("¿Cuáles son?");
				for(int i = 0; i < generos.size(); i++) {
					System.out.println(generos.get(i));
				}
				break;
			case 7:
				doc = ejer.añadirAtributo("Dune", "Prueba", "Valor de prueba", doc);
				doc = ejer.eliminarPelicula("Alien", "año", doc);
				
				try {
					ejer.grabarDOM(doc, rutaSalida);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 8:
				ejer.añadirPeli(doc, "Depredador", "Jhon", "Tiernan", "1987", "acción", "en");
				
				try {
					ejer.grabarDOM(doc, rutaSalida);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					
					e.printStackTrace();
				}
				break;
			case 9:
				String nombre = "Larry";
				String apellido = "Wachowski";
				String nombreNuevo = "Lana";
				
				doc = ejer.cambiarNombreDirector(nombre, apellido, nombreNuevo, doc);
				
				
				try {
					ejer.grabarDOM(doc, rutaSalida);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					e.printStackTrace();
				}
				
				break;
			case 10:
				String nomDirector = "Alfredo";
				String apeDirector = "Landa";
				String tituloPeli = "Dune";
				
				doc = ejer.añadirDirector(nomDirector, apeDirector, tituloPeli, doc);
				
				try {
					ejer.grabarDOM(doc, rutaSalida);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 11:
				
				doc = ejer.eliminarPelicula("Blade Runner", doc);
				
				try {
					ejer.grabarDOM(doc, rutaSalida);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					e.printStackTrace();
				}
				
				break;
			case 12:
				Document docuNuevo = ejer.crearArbol();
				
				
				try {
					ejer.grabarDOM(ejer.nuevoDocument(docuNuevo), rutaNuevo);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
		}
	}

}
