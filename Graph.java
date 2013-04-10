import java.util.*;

/**
 * Clasa inc are se va retine graful format din matricea imaginii. Acesta se va retine intr-un Hashtable ce va avea ca si cheie un int ce reprezinta numarul nodului si ca si
 * valoare, o lista de muchii, ce reprezinta vecinii unui anumit vecin.
 * @author Razvan
 *
 */
public class Graph {

	Hashtable<Integer, List<Edge>> edges_graph = new Hashtable<Integer, List<Edge>>();
	
	private int source;
	private int destination;
	
	/**
	 * Metoda ce contruieste graful, setand capacitatile dupa specificatiile din cerinta.
	 */
	public void buildGraph() {
		
		ReadClass read = new ReadClass();
		read.read();
		Formulas formulas = new Formulas(read.getImage(), read.getMask_fg(),read.getMask_bg(),read.getNr_lines(), read.getNr_columns());
		
		source      = read.getNr_columns() * read.getNr_lines();
		destination = read.getNr_columns() * read.getNr_lines() + 1;
		
		int[][] image = read.getImage();
		List<Edge> edges, edges_source = new ArrayList<Edge>(), edges_drene = new ArrayList<Edge>();
		Edge edge;
		double capacity;
		for(int i = 0 ;i < read.getNr_lines(); i++) {
			for (int j = 0; j < read.getNr_columns(); j++) {
				edges = new ArrayList<Edge>();
				if(i - 1 >= 0) {	
					edge = new Edge(i * read.getNr_columns() + j, (i - 1) * read.getNr_columns() + j, Math.abs(image[i][j] - image[i-1][j]) <= read.getTreshold() ? read.getLambda() : 0);
					edges.add(edge);
				}
				if(j - 1 >= 0) {
					edge = new Edge(i * read.getNr_columns() + j, i * read.getNr_columns() + j-1, Math.abs(image[i][j] - image[i][j-1]) <= read.getTreshold() ? read.getLambda() : 0);
					edges.add(edge);
				}
				if(i + 1 < read.getNr_lines()) {
					edge = new Edge(i * read.getNr_columns() + j, (i + 1) * read.getNr_columns() + j, Math.abs(image[i][j] - image[i+1][j]) <= read.getTreshold() ? read.getLambda() : 0);
					edges.add(edge);
				}
				if(j + 1 < read.getNr_columns()) {
					edge = new Edge(i * read.getNr_columns() + j, i * read.getNr_columns() + j + 1, Math.abs(image[i][j] - image[i][j+1]) <= read.getTreshold() ? read.getLambda() : 0);
					edges.add(edge);
				}
				capacity = formulas.f(1, i, j);
				edge = new Edge(i * read.getNr_columns() + j, source, capacity < 10 ? capacity : 10);
				edges.add(edge);
				edge = new Edge(source, i * read.getNr_columns() + j, capacity < 10 ? capacity : 10);
				edges_source.add(edge);
				capacity = formulas.f(0, i, j);
				edge = new Edge(i * read.getNr_columns() + j, destination, capacity < 10 ? capacity : 10);
				edges.add(edge);
				edge = new Edge(destination, i * read.getNr_columns() + j, capacity < 10 ? capacity : 10);
				edges_drene.add(edge);
				edges_graph.put(read.getNr_columns() * i + j, edges);
			}
		}
		edges_graph.put(source, edges_source);
		edges_graph.put(destination, edges_drene);
	}

	public int getSource() {
		return source;
	}

	public int getDestination() {
		return destination;
	}
}
