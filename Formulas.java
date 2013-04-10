/**
 * Clasa in care sunt implementate formulele de calcul pentru capacitatile grafului.
 * @author Razvan
 *
 */
public class Formulas {

	private int[][] image;
	private int[][] mask_fg;
	private int[][] mask_bg;
	
	private int nr_lines;
	private int nr_columns;
	
	private double sigmaF;
	private double niuF;
	private double sigmaB;
	private double niuB;
	
	/**
	 * Constructorul acestei clase, in care vor fi initializate structurile de date si vor fi calculate niuF, sigmaF, niuB, sigmaB.
	 * @param image reprezinta matricea ce retine imaginea.
	 * @param mask_fg reprezinta matricea ce retine masca pentru foreground
	 * @param mask_bg reprezinta matricea ce retine masca pentru background
	 * @param nr_lines reprezinta numarul de linii alea imaginii
	 * @param nr_columns reprezinta numarul de coloane ale imaginii
	 */
	public Formulas(int[][] image, int[][] mask_fg, int[][] mask_bg, int nr_lines, int nr_columns) {
		
		this.image = image;
		this.mask_bg = mask_bg;
		this.mask_fg = mask_fg;
		
		this.nr_columns = nr_columns;
		this.nr_lines = nr_lines;
		
		niuF = niuF();
		sigmaF = sigmaF();
		niuB = niuB();
		sigmaB = sigmaB();
	}
	
	/**
	 * Metoda ce implementeaza formula de calcul a capacitatii muchiilor de la sursa si drena catre noduri.
	 * @param x
	 * @param i
	 * @param j
	 * @return
	 */
	public double f(int x, int i, int j) {

		double first = (1./2) * (image[i][j] - niuF) * (image[i][j] - niuF) / (sigmaF * sigmaF);
		double l1 = Math.log(Math.sqrt(2*Math.PI*sigmaF*sigmaF));
		double second = (1./2) * (image[i][j] - niuB) * (image[i][j] - niuB) / (sigmaB * sigmaB);
		double l2 = Math.log(Math.sqrt(2*Math.PI*sigmaB*sigmaB));
		return x * (first + l1) + (1 - x) * (second + l2);
	}
	
	/**
	 * Metoda ce implementeaza formula de calcul a lui 'niu' pentru foreground
	 * @return intoarce expresia lui 'niu'
	 */
	private double niuF() {
		
		double sum = 0;
		double nr = 0;
		for(int i = 0; i < nr_lines; i++) {
			for(int j = 0; j < nr_columns; j++) {
				if(mask_fg[i][j] != 0) {
					nr++;
					sum += image[i][j];
				}
			}
		}
		return sum / nr;
	}
	
	/**
	 * Metoda ce implementeaza formula de calcul a lui 'sigma' pentru foreground
	 * @return intoarce expresia lui sigma
	 */
	private double sigmaF() {
		
		double sum = 0;
		double nr = 0;
		for(int i = 0; i < nr_lines; i++) {
			for(int j = 0; j < nr_columns; j++) {
				if(mask_fg[i][j] != 0) {
					sum += (niuF - image[i][j]) * (niuF - image[i][j]);
					nr++;
				}
			}
		}
		return Math.sqrt(sum / nr);
	}
	
	/**
	 *Metoda ce implementeaza formula de calcul a lui 'niu' pentru background 
	 * @return intoarce expresia lui niu
	 */
	private double niuB() {
		
		double sum = 0;
		double nr = 0;
		for(int i = 0; i < nr_lines; i++) {
			for(int j = 0; j < nr_columns; j++) {
				if(mask_bg[i][j] != 0) {
					nr++;
					sum += image[i][j];
				}
			}
		}
		return sum / nr;
	}
	
	/**
	 * Metoda ce implementeaza formula de calcul a lui 'sigma' pentru background
	 * @return intoarce expresia lui 'sigma'
	 */
	private double sigmaB() {
		
		double sum = 0;
		double nr = 0;
		for(int i = 0; i < nr_lines; i++) {
			for(int j = 0; j < nr_columns; j++) {
				if(mask_bg[i][j] != 0) {
					nr++;
					sum += (niuB - image[i][j]) * (niuB - image[i][j]);
				}
			}
		}
		return Math.sqrt(sum / nr);
	}
}
