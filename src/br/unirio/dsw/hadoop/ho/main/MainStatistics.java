package br.unirio.dsw.hadoop.ho.main;

import java.util.Date;
import java.util.List;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.reader.dynamo.ProductReader;
import br.unirio.dsw.hadoop.ho.reader.dynamo.RatingReader;
import br.unirio.dsw.hadoop.ho.reader.dynamo.UserReader;

/**
 * Programa principal para publicar dados sobre a base de produtos e usuários
 * 
 * @author marcio.barros
 */
public class MainStatistics
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("Free memory (Kb): " + Runtime.getRuntime().freeMemory() / 1024);
		long startTime = new Date().getTime();
		
		Market market = new Market();

		System.out.println("Loading users ...");
		new UserReader().execute(Constants.RAW_DATA_DIRECTORY + "57c30a12-dde8-4858-8510-08a01ac6acf4_000000", market);
		System.out.println(market.countUsers() + " users in the market.");

		System.out.println("Loading products ...");
		new ProductReader().execute(Constants.RAW_DATA_DIRECTORY + "83594614-bacf-40c9-ac0c-238699b53106_000000", market);
		System.out.println(market.countProducts() + " products in the market.");

		System.out.println("Loading ratings ...");
		new RatingReader().execute(Constants.RAW_DATA_DIRECTORY + "f2e6b2bd-2678-4667-a87e-ac7a310d8b6e_000000", market);

		List<String> allTags = market.getAllTags();
		System.out.println(allTags.size() + " tags in the dictionary.");

		List<String> allCategories = market.getAllCategories();
		System.out.println(allCategories.size() + " categories in the product base.");
		
		long finishTime = new Date().getTime();
		System.out.println("Free memory (Kb): " + Runtime.getRuntime().freeMemory() / 1024);
		System.out.println("Total time (seg): " + (finishTime - startTime) / 1000);
	}
}