import java.util.*;

/**
 * Clasa ce implementeaza un algoritm de flux maxim pe un graf neorientat pentru pentru a afla taietura minima a acestuia.
 * @author Razvan
 *
 */
public class MaxFlow {
	
	public static double maxFlow = 0;
	
	/**
	 * Metoda ce va apela metodele necesare de aflare a taieturii minime
	 * @param graph repezinta graful asociat matricei imaginii
	 * @param source reprezinta sursa  
	 * @param destination reprezinta drena
	 * @return
	 */
	public List<Integer> find_foreground(Hashtable<Integer,List<Edge>> graph, int source, int destination) {
		
		initial_saturation(graph, source, destination);
		maximum_flow(graph, source, destination);
		return min_cut(graph, source);
	}
	
	/**
	 * MEtoda ce satureaza muchiile  de tipul sursa-nod-drena. Aceasta metoda a fost realizata pentru a mari performanta programului in timp.
	 * @param graph repezinta graful asociat matricei imaginii
	 * @param source reprezinta sursa
	 * @param destination reprezinta drena
	 */
	private void initial_saturation(Hashtable<Integer,List<Edge>> graph, int source, int destination) {
		
			List<Edge> sourceList = graph.get(source);
			Edge edge_from_source, edge_from_here;
			double max;
			Iterator<Edge> it = sourceList.iterator();
			while(it.hasNext()) {
				edge_from_source = it.next();
				List<Edge> edges = graph.get(edge_from_source.getTo());
				Iterator<Edge> it2 = edges.iterator();
				while(it2.hasNext()) {
					edge_from_here = it2.next();
					if(edge_from_here.getTo() == destination) {
						max = edge_from_source.getCapacity() < edge_from_here.getCapacity() ? edge_from_source.getCapacity() : edge_from_here.getCapacity();
						maxFlow +=max;
						edge_from_source.setCapacity(edge_from_source.getCapacity() - max);
						edge_from_here.setCapacity(edge_from_here.getCapacity()- max);
						break;
					}
				}
				
			}
	}
	
	/**
	 * Metoda ce realizeaza BFS pentru gasirea de cai de la sursa la drena.
	 * @param graph
	 * @param source
	 * @param destination
	 * @return
	 */
	private List<Integer> bfs(Hashtable<Integer,List<Edge>> graph, int source, int destination) {
		
		Edge edge;
		List<Edge> list;
		Vector<Integer> parent = new Vector<Integer>();
		for(int i = 0; i < graph.size(); i++)
			parent.add(-3);
		Queue<Integer> queue = new LinkedList<Integer>();
		
		queue.add(source);
		int curent = source;
		
		while(parent.get(destination) == -3 && !queue.isEmpty()) {
			curent = queue.poll();
			list = graph.get(curent);
			Iterator<Edge> it = list.iterator();
			while(it.hasNext()) {
				edge = it.next();
				if(edge.getCapacity() > 0 && parent.get(edge.getTo()) == -3) {
					parent.set(edge.getTo(), curent);
					queue.add(edge.getTo());
				}
			}
		}
		//daca nu s-a atins destinatia, intoarcem o lista goala
		if (parent.get(destination) == -3)
			return new ArrayList<Integer>();
		//reconstituim drumul de intoarcere
		List<Integer> returnValue = new ArrayList<Integer>();
		for(int node = destination; node != source ; node = parent.get(node)) {
			returnValue.add(node);
		}
		returnValue.add(source);
		//inversam vectorul rezultat
		Collections.reverse(returnValue);
		return returnValue;
	}
	
	/**
	 * Metoda ce va satura o cale gasita de BFS-ul anterior.
	 * @param graph
	 * @param path reprezinta calea ce trebuie saturata.
	 * @return
	 */
	private double saturate_path(Hashtable<Integer,List<Edge>> graph, List<Integer> path) {
		
		//determinam fluxul maxim prin graf
		List<Edge> edges = graph.get(path.get(0));
		Edge edge = null;
		Iterator<Edge> iter = edges.iterator();
		while(iter.hasNext()) {
			edge = iter.next();
			if(edge.getTo() == path.get(1))
				break;
		}
		double flow = edge.getCapacity();
		for(int i = 1; i < path.size() - 1; i++) {
			int u = path.get(i);
			int v = path.get(i + 1);
			edges = graph.get(u);
			Iterator<Edge> it = edges.iterator();
			while(it.hasNext()) {
				edge = it.next();
				if(edge.getTo() == v) 
					break;
			}
			flow = (flow < edge.getCapacity() ? flow : edge.getCapacity());
		}
		
		//il saturam in graf
		for(int i = 0; i < path.size() - 1; i++) {
			int u = path.get(i);
			int v = path.get(i + 1);
			edges = graph.get(u);
			Iterator<Edge> it = edges.iterator();
			while(it.hasNext()) {
				edge = it.next();
				if(edge.getTo() == v) 
					break;
			}
			edge.setCapacity(edge.getCapacity() - flow);
			edges = graph.get(v);
			Iterator<Edge> it2 = edges.iterator();
			while(it2.hasNext()) {
				edge = it2.next();
				if(edge.getTo() == u) 
					break;
			}
			edge.setCapacity(edge.getCapacity() - flow);
		}
		
		//raportam fluxul  cu care am saturat graful
		return flow;
	}
	
	/**
	 * MEtoda ce gaseste fluxul maxim intr-un graf, saturand totodata toate caile de la sursa la drena
	 * @param graph
	 * @param source
	 * @param destination
	 * @return intoarce fluxul maxim
	 */
	private double maximum_flow(Hashtable<Integer,List<Edge>> graph, int source, int destination) {
		
		double returnValue = 0;
		
		while(true) {
			List<Integer> saturation_path = bfs(graph, source, destination);
			if(saturation_path.size() == 0)
				break;
			double sat = saturate_path(graph, saturation_path);
			returnValue += sat;
			maxFlow += sat;
		}
		
		return returnValue;
	}
	
	/**
	 * MEtoda ce va afla taietura minima a grafului(toate nodurile la care se poate ajunge de la sursa)
	 * @param graph
	 * @param source
	 * @return intoarce lista cu nodurile ce vor reprezenta foregroundul imaginii
	 */
	private List<Integer> min_cut(Hashtable<Integer,List<Edge>> graph, int source) {
		
		//facem o parcurgere BFS din nodul sursa si punem nodurile care pot fi atinse in source_set
		List<Integer> source_set = new ArrayList<Integer>();
		boolean[] in_queue = new boolean[graph.size()];
		for(int i = 0; i < in_queue.length; i++)
			in_queue[i] = false;
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(source);
		List<Edge> list;
		while(queue.size() > 0) {
			int curent = queue.poll();
			list = graph.get(curent);
			Iterator<Edge> it = list.iterator();
			Edge edge;
			while(it.hasNext()) {
				edge = it.next();
				if(in_queue[edge.getTo()] == false && edge.getCapacity() > 0) {
					in_queue[edge.getTo()] = true;
					queue.add(edge.getTo());
				}
			}
		}
		for(int i = 0; i < graph.size() - 2; i++) { 
			if(!in_queue[i])
				source_set.add(i);
		}
		//se apeleaza Garbage Collectorul
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		return source_set;
	}
}
