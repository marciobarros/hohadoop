package org.hadoop.writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.hadoop.model.Product;
import org.hadoop.model.ProductProximity;

/**
 * Salva um arquivo de produtos pr�ximos a produtos
 * 
 * @author Marcio Barros
 */
public class ProductProximityWriter
{
	/**
	 * Salva uma lista de produtos pr�ximos a outros produtos
	 */
	public void execute(String filename, List<ProductProximity> result) throws IOException 
	{
		File logFile = new File(filename);
		PrintWriter writer = new PrintWriter(new FileWriter(logFile));
		NumberFormat nf2 = new DecimalFormat("0.00");

		for (ProductProximity proximity : result)
		{
			if (proximity.countCloseProducts() > 0)
			{
				saveProduct(writer, nf2, proximity);
			}
		}
		
		writer.close();
	}

	/**
	 * Salva os produtos pr�ximos a um determinado produto
	 */
	private void saveProduct(PrintWriter writer, NumberFormat nf2, ProductProximity proximity)
	{
		Product product = proximity.getProduct();
		writer.println("PROD\t" + product.getId() + "\t" + product.getTitle());
		
		for (int i = 0; i < proximity.countCloseProducts(); i++)
		{
			Product closeProduct = proximity.getCloseProductIndex(i);
			writer.println(nf2.format(proximity.getDistanceIndex(i)) + "\t" + closeProduct.getId() + "\t" + closeProduct.getTitle());
		}

		writer.println();
	}
}