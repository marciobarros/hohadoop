package br.unirio.dsw.hadoop.ho.main;

import java.util.Date;

import br.unirio.dsw.hadoop.ho.calc.ComputeCloseProducts;
import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.reader.ProductReader;

/**
 * Programa principal para calcular os arquivos de produtos próximos a outros produtos
 * 
 * @author marcio.barros
 */
public class MainCalculator
{
	public static void main(String[] args) throws Exception
	{
		long startTime = new Date().getTime();
		Market market = new Market();

		System.out.println("Loading products ...");
		new ProductReader().execute(Constants.RAW_DATA_DIRECTORY + "83594614-bacf-40c9-ac0c-238699b53106_000000", market);

		new ComputeCloseProducts().executeCategories(market, Constants.RESULT_DIRECTORY + "saida.txt");
		
		long finishTime = new Date().getTime();
		System.out.println("Total time (seg): " + (finishTime - startTime) / 1000);
	}
}