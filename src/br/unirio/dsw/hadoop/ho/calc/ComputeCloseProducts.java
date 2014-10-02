package br.unirio.dsw.hadoop.ho.calc;

import java.util.ArrayList;
import java.util.List;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;
import br.unirio.dsw.hadoop.ho.model.ProductProximity;
import br.unirio.dsw.hadoop.ho.writer.ProductProximityWriter;

/**
 * Classe que calcula os produtos mais próximos a outros produtos
 * 
 * @author Marcio Barros
 */
public class ComputeCloseProducts 
{
	private static final double MIN_PROXIMITY_THRESHOLD = 0.50;

	/**
	 * Calcula os produtos próximos a cada produto sem considerar as categorias
	 */
	public List<ProductProximity> execute(Market market, String filename) throws Exception
	{
		List<ProductProximity> result = new ArrayList<ProductProximity>();
		Iterable<Product> allProducts = market.getProducts();
		
		for (Product product : allProducts)
			processProduct(product, allProducts, result);

		new ProductProximityWriter().execute(filename, result, market);
		return result;
	}

	/**
	 * Calcula os produtos próximos a cada produto considerando as categorias
	 */
	public List<ProductProximity> executeCategories(Market market, String filename) throws Exception
	{
		List<ProductProximity> result = new ArrayList<ProductProximity>();
		List<String> categories = market.getAllCategories();

		for (String category : categories)
		{
			Iterable<Product> categoryProducts = market.getProductsForCategory(category);
			
			for (Product product : categoryProducts)
				processProduct(product, categoryProducts, result);
		}
		
		new ProductProximityWriter().execute(filename, result, market);
		return result;
	}
	
	/**
	 * Calcula os produtos próximos a um produto
	 */
	private void processProduct(Product product, Iterable<Product> products, List<ProductProximity> result)
	{
		ProductProximity proximity = getProductProximity(product, result);
		
		if (proximity == null)
		{
			proximity = new ProductProximity(product.getId());
			result.add(proximity);
		}

		for (Product relatedProduct : products)
		{
			if (relatedProduct != product)
			{
				double distance = Distance.jaccardProductProductDistance(product, relatedProduct);
				
				if (distance < MIN_PROXIMITY_THRESHOLD)
					proximity.add(relatedProduct.getId(), distance);
			}
		}
	}
	
	/**
	 * Recupera a lista de produtos próximos a um produto
	 */
	private ProductProximity getProductProximity(Product product, List<ProductProximity> result)
	{
		for (ProductProximity proximity : result)
			if (proximity.getProductId().compareTo(product.getId()) == 0)
				return proximity;
		
		return null;
	}
}