package org.hadoop.calc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
		Iterable<Product> allProducts = market.getProducts();
		
		for (Product product : allProducts)
			processProduct(product, allProducts, result);

		saveProximities(result);
		return result;
	}

	public List<ProductProximity> executeCategories(Market market) throws Exception
	{
		List<ProductProximity> result = new ArrayList<ProductProximity>();
		List<String> categories = market.getAllCategories();

		for (String category : categories)
		{
			Iterable<Product> categoryProducts = market.getProductsForCategory(category);
			
			for (Product product : categoryProducts)
				processProduct(product, categoryProducts, result);
		}
		
		saveProximities(result);
		return result;
	}
	
	private void processProduct(Product product, Iterable<Product> products, List<ProductProximity> result)
	{
		ProductProximity proximity = getProductProximity(product, result);
		
		if (proximity == null)
		{
			proximity = new ProductProximity(product);
			result.add(proximity);
		}

		for (Product relatedProduct : products)
		{
			if (relatedProduct != product)
			{
				double distance = Distance.jaccardProductProductDistance(product, relatedProduct);
				
				if (distance < 0.50)
					proximity.add(relatedProduct, distance);
			}
		}
	}
	
	private ProductProximity getProductProximity(Product product, List<ProductProximity> result)
	{
		for (ProductProximity proximity : result)
			if (proximity.getProduct() == product)
				return proximity;
		
		return null;
	}

	private void saveProximities(List<ProductProximity> result) throws IOException 
	{
		File logFile = new File("saida.txt");
		PrintWriter writer = new PrintWriter(new FileWriter(logFile));
		NumberFormat nf2 = new DecimalFormat("0.00");

		for (ProductProximity proximity : result)
		{
			if (proximity.countCloseProducts() > 0)
			{
				Product product = proximity.getProduct();
				writer.println("PROD\t" + product.getId() + "\t" + product.getTitle()/* + " " + product.getTagsAsString()*/);
				
				for (int i = 0; i < proximity.countCloseProducts(); i++)
				{
					Product closeProduct = proximity.getCloseProductIndex(i);
					writer.println(nf2.format(proximity.getDistanceIndex(i)) + "\t" + closeProduct.getId() + "\t" + closeProduct.getTitle()/* + " " + closeProduct.getTagsAsString()*/);
				}
	
				writer.println();
			}
		}
		
		writer.close();
	}
}