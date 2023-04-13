package ModeleoCorte;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
	
	public int nBarras;
	public int molde;
	public ArrayList<Integer> barras;
	public int[] qBarras;

	
	public Data(String input) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(input));
		nBarras = scanner.nextInt();
		molde = scanner.nextInt();

		barras = new ArrayList();
		for (int i = 0; i < nBarras; i++) {
			barras.add(scanner.nextInt());
		}

		qBarras = new int[nBarras];
		for (int i = 0; i < nBarras; i++) {
			qBarras[i] = scanner.nextInt();
		}
	}

	public void mostraData() {
		System.out.println("Barras: " + nBarras);
		for (int i = 0; i < nBarras; i++) {
			System.out.println("Barra " + (i+1) + " = " + barras.get(i) +
					           "cm - " + qBarras[i]);
		}
	}



		
}
