package br.unirio.dsw.hadoop.ho.main;

import java.util.Date;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.reader.ProductReader;
import br.unirio.dsw.hadoop.ho.writer.ProductCategorySplitter;

/**
 * Programa principal para comparar o resultado da análise de produtos próximos sem categorias e com categorias
 * 
 * @author marcio.barros
 */
public class MainSplit
{
	public static void main(String[] args) throws Exception
	{
		long startTime = new Date().getTime();
		Market market = new Market();

		System.out.println("Loading products ...");
		new ProductReader().execute(Constants.RAW_DATA_DIRECTORY + "83594614-bacf-40c9-ac0c-238699b53106_000000", market);
		System.out.println(market.countProducts() + " produtos carregados");

		new ProductCategorySplitter().execute(market, "data\\split\\");
		
		long finishTime = new Date().getTime();
		System.out.println("Total time (seg): " + (finishTime - startTime) / 1000);
	}
}