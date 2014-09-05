package org.hadoop.calc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.hadoop.model.Market;
import org.hadoop.model.Product;
import org.hadoop.model.ProductProximity;

public class ComputeCloseProducts 
{
	public List<ProductProximity> execute(Market market) throws Exception
	{
		List<ProductProximity> result = new ArrayList<ProductProximity>();
		int productsCount = market.countProducts();
		
		for (int i = 0; i < productsCount; i++)
		{
			Product product1 = market.getProductByIndex(i);
			
			ProductProximity proximity = new ProductProximity(product1);
			
			for (int j = 0; j < productsCount; j++)
			{
				if (i != j)
				{
					Product product2 = market.getProductByIndex(j);
					double distance = Distance.jaccardProductProductDistance(product1, product2);
					
					if (distance < 0.50)
						proximity.add(product2, distance);
				}
			}
			
			if (proximity.countCloseProducts() > 0)
				result.add(proximity);
		}

		saveProximities(result);
		return result;
	}

	private void saveProximities(List<ProductProximity> result) throws IOException 
	{
		File logFile = new File("saida.txt");
		PrintWriter writer = new PrintWriter(new FileWriter(logFile));

		for (ProductProximity proximity : result)
		{
			writer.println("Product: " + proximity.getProduct().getTitle() + " " + proximity.getProduct().getTagsAsString());
			
			for (int i = 0; i < proximity.countCloseProducts(); i++)
				writer.println("      " + i + ": " + proximity.getDistanceIndex(i) + " " + proximity.getCloseProductIndex(i).getTitle() + " " + proximity.getCloseProductIndex(i).getTagsAsString());

			writer.println();
		}
		
		writer.close();
	}
}