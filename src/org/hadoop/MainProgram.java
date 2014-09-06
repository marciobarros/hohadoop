package org.hadoop;

import java.util.Date;
import java.util.List;

import org.hadoop.calc.ProximityComparator;
import org.hadoop.model.Market;
import org.hadoop.model.ProductProximity;
import org.hadoop.readers.ProductProximityReader;
import org.hadoop.readers.ProductReader;

/**
 * Programa principal
 * 
 * @author marcio.barros
 */
public class MainProgram
{
	private static final String DIRECTORY = "data\\";
	
	/**
	 * Apresenta estatísticas básicas sobre os dados
	 */
	public static void showStatistics(Market market)
	{
		System.out.println(market.countUsers() + " users in the market.");
		System.out.println(market.countProducts() + " products in the market.");

		List<String> allTags = market.getAllTags();
		System.out.println(allTags.size() + " tags in the dictionary.");

		List<String> allCategories = market.getAllCategories();
		System.out.println(allCategories.size() + " categories in the product base.");
	}

	/**
	 * Compara as proximidades calculadas com e sem categorias
	 */
	private static void compareProximityAccordingCategory(Market market) throws Exception 
	{
		System.out.println("Loading proximity without categories ...");
		List<ProductProximity> withoutCategories = new ProductProximityReader().execute("results\\saida - sem categorias.txt", market);

		System.out.println("Loading proximity with categories ...");
		List<ProductProximity> withCategories = new ProductProximityReader().execute("results\\saida - com categorias.txt", market);
		
		System.out.println("Comparing proximities with and without categories ...");
		double perc = new ProximityComparator().execute(withoutCategories, withCategories);
		System.out.println(perc + "% produtos foram considerados com as categorias");
	}
	
	/**
	 * Programa principal
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Free memory (Kb): " + Runtime.getRuntime().freeMemory() / 1024);
		long startTime = new Date().getTime();
		
		Market market = new Market();

//		System.out.println("Loading users ...");
//		new UserReader().execute(DIRECTORY + "57c30a12-dde8-4858-8510-08a01ac6acf4_000000", market);

		System.out.println("Loading products ...");
		new ProductReader().execute(DIRECTORY + "83594614-bacf-40c9-ac0c-238699b53106_000000", market);

//		System.out.println("Loading ratings ...");
//		new RatingReader().execute(DIRECTORY + "f2e6b2bd-2678-4667-a87e-ac7a310d8b6e_000000", market);

//		System.out.println("Building user tags ...");
//		market.buildUserTags();

//		showStatistics(market);
//		new ComputeCloseProducts().executeCategories(market);
		compareProximityAccordingCategory(market);
		
		long finishTime = new Date().getTime();
		System.out.println("Free memory (Kb): " + Runtime.getRuntime().freeMemory() / 1024);
		System.out.println("Total time (seg): " + (finishTime - startTime) / 1000);
	}
}