import java.io.*;
import java.util.Scanner;

/**
 * Clasa in care se realizeaza citirea din cele 4 fisiere de intrare.
 * @author Razvan
 *
 */
public class ReadClass {
	
	private int[][] image;
	private int[][] mask_fg;
	private int[][] mask_bg;
	
	public static int nr_lines;
	public static int nr_columns;
	
	private double lambda;
	private double treshold;
	
	public void read() {
		
		read_image();
		read_mask_fg();
		read_mask_bg();
		read_parameters();
	}

	/**
	 * Metoda ce realizeaza citirea imaginii. Aceasta va fi depusa intr-o matrice.
	 */
	private void read_image() {
		
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("imagine.pgm");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(fileInputStream);
		
		scan.nextLine();
		nr_columns = scan.nextInt();
		nr_lines = scan.nextInt();
		scan.nextInt();
		
		image = new int[nr_lines][nr_columns];
		
		for(int i = 0; i < nr_lines; i++) {
			for(int j = 0; j < nr_columns; j++) {
				image[i][j] = scan.nextInt();
			}
		}
	}
	
	/**
	 * Metoda ce realizeaza citirea mastii pentru foreground.
	 */
	private void read_mask_fg() {
		
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("mask_fg.pgm");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(fileInputStream);
		
		scan.nextLine();
		scan.nextInt();
		scan.nextInt();
		scan.nextInt();
		
		mask_fg = new int[nr_lines][nr_columns];
		
		for(int i = 0; i < nr_lines; i++) {
			for(int j = 0; j < nr_columns; j++) {
				mask_fg[i][j] = scan.nextInt();
			}
		}
	}
	
	/**
	 * Metoda ce realizeaza citirea mastii pentru bakcground.
	 */
	private void read_mask_bg() {
		
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("mask_bg.pgm");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(fileInputStream);
		
		scan.nextLine();
		scan.nextInt();
		scan.nextInt();
		scan.nextInt();
		
		mask_bg = new int[nr_lines][nr_columns];
		
		for(int i = 0; i < nr_lines; i++) {
			for(int j = 0; j < nr_columns; j++) {
				mask_bg[i][j] = scan.nextInt();
			}
		}
	}
	
	/**
	 * Metoda ce realizeaza citirea parametrilor lambda si treshold.
	 */
	private void read_parameters() {
		
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("parametri.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(fileInputStream);
		lambda = scan.nextDouble();
		treshold = scan.nextDouble();
	}

	public int[][] getImage() {
		return image;
	}

	public int[][] getMask_fg() {
		return mask_fg;
	}

	public int[][] getMask_bg() {
		return mask_bg;
	}

	public int getNr_lines() {
		return nr_lines;
	}


	public int getNr_columns() {
		return nr_columns;
	}

	public double getLambda() {
		return lambda;
	}

	public double getTreshold() {
		return treshold;
	}
}
