package ModeleoCorte;


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
	public MPVariable[][] x;
	public double[] solucao;

	public Modelo(Data data) {
		Loader.loadNativeLibraries();
		solver = MPSolver.createSolver("SCIP");
		double infinity = Double.POSITIVE_INFINITY;

		int i = 0;
		ArrayList<int[]> padroes = new ArrayList();
		ArrayList<Integer> desperdicios = new ArrayList();

		System.out.println("sdfjrnshd");
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

		for (i = 0; i < padroes.size(); i++){
			System.out.printf("padrao: [%d, %d, %d] | desperdicio: %d\n", padroes.get(i)[0], padroes.get(i)[1], padroes.get(i)[2], desperdicios.get(i));
		}

		x = new MPVariable[padroes.size()][data.nBarras];
		for (i = 0; i < padroes.size();i++){
			int[] padrao = padroes.get(i);
			for (int j = 0; j < data.nBarras; j++){
				x[i][j] = solver.makeIntVar(0, infinity, "Padrao ["+j+"]");
			}
		}

	}
	
//	public void solve(Data data) {
//		ResultStatus status = solver.solve();
//		if (status == ResultStatus.OPTIMAL) {
//			custoSolucao = objective.value();
//			solucao = new double[data.nTarefas][data.nMaquinas];
//			for (int i = 0; i < data.nTarefas; i++) {
//				for (int j = 0; j < data.nMaquinas; j++) {
//					if (x[i][j].solutionValue() > 0.9) {
//						solucao[i][j] = 1.0;
//						System.out.println("Tarefa " + i + " na maquina " + j);
//					}
//
//				}
//			}
//		}
//
//	}
	
	public void exportModel(String output) throws IOException {
		FileWriter fw = new FileWriter(output);
		fw.write(solver.exportModelAsLpFormat());
		fw.close();
	}
	
}
