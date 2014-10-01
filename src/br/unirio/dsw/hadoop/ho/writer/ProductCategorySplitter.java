package br.unirio.dsw.hadoop.ho.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;

/**
 * Classe responsável por gerar os arquivos de produtos separados por categoria
 * 
 * @author Marcio Barros
 */
public class ProductCategorySplitter
{
	/**
	 * Publica arquivos de produtos separados por categoria
	 */
	public void execute(Market market, String directory) throws IOException
	{
		List<String> categories = market.getAllCategories();
		int categoryNumber = 0;
		
		for (String category : categories)
			if (market.countProductsForCategory(category) > 20)
				publicCategoryProducts(market, directory, categoryNumber++, category);
	}

	/**
	 * Publica um arquivo com todos os produtos de uma categoria
	 */
	private void publicCategoryProducts(Market market, String directory, int categoryNumber, String category) throws IOException
	{
		NumberFormat nf5 = new DecimalFormat("00000");
		PrintWriter writer = new PrintWriter(new FileWriter(directory + "part-" + nf5.format(categoryNumber)));
		
		for (Product product : market.getProducts())
		{
			if (product.containsCategory(category))
			{
				String title = product.getTitle().replace(",", "").trim();
				String tags = "";
				
				for (String tag : product.getTags())
					tags += "," + tag;
				
				writer.println(product.getId() + "," + title + tags);
			}
		}

		writer.close();
	}
}