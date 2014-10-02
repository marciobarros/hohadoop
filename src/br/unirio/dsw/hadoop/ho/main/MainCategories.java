package br.unirio.dsw.hadoop.ho.main;

import java.util.Date;
import java.util.List;

import br.unirio.dsw.hadoop.ho.calc.ProximityComparator;
import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.ProductProximity;
import br.unirio.dsw.hadoop.ho.reader.dynamo.ProductReader;
import br.unirio.dsw.hadoop.ho.reader.proximity.ProductProximityReader;

/**
 * Programa principal para comparar o resultado da análise de produtos próximos sem categorias e com categorias
 * 
 * @author marcio.barros
 */
public class MainCategories
{
	public static void main(String[] args) throws Exception
	{
		long startTime = new Date().getTime();
		Market market = new Market();

		System.out.println("Loading products ...");
		new ProductReader().execute(Constants.RAW_DATA_DIRECTORY + "83594614-bacf-40c9-ac0c-238699b53106_000000", market);

		System.out.println("Loading proximity without categories ...");
		List<ProductProximity> withoutCategories = new ProductProximityReader().execute(Constants.RESULT_DIRECTORY + "saida - sem categorias.txt", market);

		System.out.println("Loading proximity with categories ...");
		List<ProductProximity> withCategories = new ProductProximityReader().execute(Constants.RESULT_DIRECTORY + "saida - com categorias.txt", market);
		
		System.out.println("Comparing proximities with and without categories ...");
		double perc = new ProximityComparator().execute(withoutCategories, withCategories);
		System.out.println(perc + "% produtos foram considerados com as categorias");
		
		long finishTime = new Date().getTime();
		System.out.println("Total time (seg): " + (finishTime - startTime) / 1000);
	}
}