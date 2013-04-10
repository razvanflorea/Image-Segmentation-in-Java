/**
 * Clasa ce reprezinta o muchii de la 'from' la 'to' cu capcitatea 'capacity'
 * @author Razvan
 *
 */
public class Edge {

	private int from;
	private int to;
	
	private double capacity;
	
	/**
	 * Contructorul clasei in care sunt initialiti parametrii clasei.
	 * @param from reprezinta primul nod al muchiei.
	 * @param to reprezinta al doilea nod al muchiei
	 * @param capacity reprezinta capacitatea muchiei
	 */
	public Edge(int from, int to, double capacity) {
		this.from = from;
		this.to = to;
		
		this.capacity = capacity;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	} 
}
