package org.hadoop;

import java.util.Date;
import java.util.List;

import org.hadoop.calc.ComputeCloseProducts;
import org.hadoop.model.Market;
import org.hadoop.model.Product;
import org.hadoop.readers.ProductReader;

/**
 * Tipos de análise
 * 
 * - em média temos 7.8 termos por usuário
 * 
 * - usuário x produto: lista de produtos recomendados para o usuário
 * 
 * - 38k produtos e 226k usuários
 * 
 * 
 * @author marcio.barros
 */
public class MainProgram
{
	//private static final String DIRECTORY = "\\Users\\marcio.barros\\Desktop\\hadoop\\";	
	private static final String DIRECTORY = "data\\";
	
	/**
	 * Localiza termos usados em apenas um produto - já foi rodado, mas nenhum termo foi encontrado
	 */
	public static void testSingleTerms(Market market, List<String> dictionary)
	{
		int singleTerms = 0;
		
		for (String term : dictionary)
		{
			int references = 0;
			
			for (Product product : market.getProducts())
				if (product.containsTag(term))
					references++;
			
			if (references == 0)
				singleTerms++;
		}
		
		System.out.println(singleTerms + " single terms found in the dictionary.");
	}

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

		new ComputeCloseProducts().executeCategories(market);
		
		//showStatistics(market);
		//testSingleTerms(market, dictionary);
		
		long finishTime = new Date().getTime();
		System.out.println("Free memory (Kb): " + Runtime.getRuntime().freeMemory() / 1024);
		System.out.println("Total time (seg): " + (finishTime - startTime) / 1000);
	}
}