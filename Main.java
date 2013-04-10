import java.io.*;
import java.util.*;

/**
 * Clasa ce contine metoda 'main', ce reprezinta entry point-ul programului.
 * @author Razvan
 *
 */
public class Main {

	
	/** Metoda main.
	 * @param args
	 */
	public static void main(String[] args) {
		
		Graph graph = new Graph();
		graph.buildGraph();
		Hashtable<Integer, List<Edge>> map = graph.edges_graph;
		
		MaxFlow maxFlow = new MaxFlow();
		List<Integer> path = maxFlow.find_foreground(map, graph.getSource(), graph.getDestination());
	
		try {
			FileWriter fstream = new FileWriter("segment.pgm");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("P2");
			out.write('\n');
			out.write(ReadClass.nr_columns + " " + ReadClass.nr_lines);
			out.write('\n');
			out.write("255");
			out.write('\n');
			for(int i = 0 ; i < ReadClass.nr_lines * ReadClass.nr_columns; i++) {
				if(path.contains(i)){
					out.write("255");
					out.write('\n');
				}
				else {
					out.write("0");
					out.write('\n');
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
