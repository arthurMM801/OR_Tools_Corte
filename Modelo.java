package ModeloCorte;


import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolver.ResultStatus;
import com.google.ortools.linearsolver.MPVariable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Modelo {
	
	public MPObjective objective;
	public MPSolver solver;
	public MPVariable[] x;
	public double[] solucao;

	public Modelo(Data data) {
		Loader.loadNativeLibraries();
		solver = MPSolver.createSolver("SCIP");
		double infinity = Double.POSITIVE_INFINITY;

		int i = 0;
		ArrayList<int[]> padroes = new ArrayList();
		ArrayList<Integer> desperdicios = new ArrayList();

		// Criacao dos padrões
		while( i < data.barras.size()){
			int[] padrao = new int[data.nBarras];
			Integer barra1 = data.barras.get(i);
			padrao[i] += 1;
			int soma = barra1;

			int j = i;
			while ( j < data.barras.size() ) {
				Integer barra = data.barras.get(j);
				padrao[j] += 1;
				soma += barra;
				int desperdicio = data.molde;
				if(soma > desperdicio) {
					padrao[j] -= 1;
					soma -= barra;
					j++;
				}
				desperdicio = data.molde - soma;
				if (desperdicio < data.barras.get(data.barras.size()-1)){
					int[] p = padrao.clone();
					padroes.add(p);
					desperdicios.add(desperdicio);
					padrao[j] -= 1;
					soma -= barra;
					j++;
				}
			}
			i++;
		}


		// Print dos padrões
		System.out.println();
		for (i = 0; i < padroes.size(); i++){
			System.out.print("Padrao: "+i+": [");
			for(int j = 0; j < data.nBarras; j++){
				if(j != data.nBarras-1)
					System.out.print(padroes.get(i)[j]+", ");
				else
					System.out.println(padroes.get(i)[j]+"]");
			}

		}
		System.out.println();


		// Define Variáveis
		x = new MPVariable[padroes.size()];
		for (i = 0; i < padroes.size();i++){
			x[i] = solver.makeIntVar(0, infinity, "Padrao ["+(i+1)+"]");
		}


		// Define Restrições
		for (i = 0; i < data.nBarras; i++) {
			MPConstraint ct = solver.makeConstraint(data.qBarras[i], infinity, "Barra "+(i+1));
			for (int j = 0; j < padroes.size(); j++) {
				ct.setCoefficient(x[j], padroes.get(j)[i]);
			}
		}

		// Define Objetivo
		objective = solver.objective();
		for (i = 0; i < padroes.size(); i++){
			objective.setCoefficient(x[i], desperdicios.get(i));
		}
		objective.setMinimization();

	}
	
	public void solve(Data data) {
		MPSolver.ResultStatus resultStatus = solver.solve();
		if (resultStatus == ResultStatus.OPTIMAL) {
			System.out.println("Solucao:");
			System.out.println("Custo da funcao objetivo = " + objective.value());
			for (int i = 0;i < x.length; i++){
				System.out.println("Padrao "+  (i+1) + " = " + x[i].solutionValue());
			}
			System.out.println("Tempo de resolucao = " + solver.wallTime() + " milissegundos");
			System.out.println(solver.exportModelAsLpFormat());
		}

	}
	
	public void exportModel(String output) throws IOException {
		FileWriter fw = new FileWriter(output);
		fw.write(solver.exportModelAsLpFormat());
		fw.close();
	}
	
}
