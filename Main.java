package ModeloCorte;


import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		String input = "entradaCorte.txt";
		Data data = new Data(input);
		data.mostraData();


		Modelo corte = new Modelo(data);
		corte.solve(data);
		corte.exportModel("modelo_Corte.txt");
	}
}
