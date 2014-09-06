package org.hadoop.calc;

import java.util.ArrayList;
import java.util.List;

import org.hadoop.model.Market;
import org.hadoop.model.Product;
import org.hadoop.model.ProductProximity;
import org.hadoop.writers.ProductProximityWriter;

public class ComputeCloseProducts 
{
	public List<ProductProximity> execute(Market market) throws Exception
	{
		List<ProductProximity> result = new ArrayList<ProductProximity>();
		Iterable<Product> allProducts = market.getProducts();
		
		for (Product product : allProducts)
			processProduct(product, allProducts, result);

		new ProductProximityWriter().execute("saida.txt", result);
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
		
		new ProductProximityWriter().execute("saida.txt", result);
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
}