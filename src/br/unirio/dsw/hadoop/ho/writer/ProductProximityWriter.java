package br.unirio.dsw.hadoop.ho.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;
import br.unirio.dsw.hadoop.ho.model.ProductProximity;

/**
 * Salva um arquivo de produtos próximos a produtos
 * 
 * @author Marcio Barros
 */
public class ProductProximityWriter
{
	/**
	 * Salva uma lista de produtos próximos a outros produtos
	 */
	public void execute(String filename, List<ProductProximity> result, Market market) throws IOException 
	{
		File logFile = new File(filename);
		PrintWriter writer = new PrintWriter(new FileWriter(logFile));
		NumberFormat nf2 = new DecimalFormat("0.00");

		for (ProductProximity proximity : result)
		{
			if (proximity.countCloseProducts() > 0)
			{
				saveProduct(writer, nf2, proximity, market);
			}
		}
		
		writer.close();
	}

	/**
	 * Salva os produtos próximos a um determinado produto
	 */
	private void saveProduct(PrintWriter writer, NumberFormat nf2, ProductProximity proximity, Market market)
	{
		String productId = proximity.getProductId();
		Product product = market.getProductById(productId);
		
		if (product != null)
		{
			writer.println("PROD\t" + product.getId() + "\t" + product.getTitle());
			
			for (int i = 0; i < proximity.countCloseProducts(); i++)
			{
				String closeProductId = proximity.getCloseProductIndex(i);
				Product closeProduct = market.getProductById(closeProductId);
				
				if (closeProduct != null)
					writer.println(nf2.format(proximity.getDistanceIndex(i)) + "\t" + closeProduct.getId() + "\t" + closeProduct.getTitle());
			}

			writer.println();
		}
	}
}