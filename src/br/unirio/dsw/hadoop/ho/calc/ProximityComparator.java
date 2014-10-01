package org.hadoop.calc;

import java.util.List;

import org.hadoop.model.Product;
import org.hadoop.model.ProductProximity;

/**
 * Classe que compara duas listas de produtos próximos a produtos, com e sem categorias
 * 
 * @author Marcio
 */
public class ProximityComparator 
{
	/**
	 * Compara duas listas de produtos próximos a produtos, com e sem categorias,
	 * retornando o percentual de produtos próximos idênticos nas duas listas
	 */
	public double execute(List<ProductProximity> withoutCategories, List<ProductProximity> withCategories) throws Exception
	{
		long proximitiesWithout = 0;
		long similarProximitiesWith = 0;
		
		for (ProductProximity proximity : withoutCategories)
			if (getProductProximity(proximity.getProduct(), withCategories) == null)
				proximitiesWithout += proximity.countCloseProducts();
		
		for (ProductProximity proximityWith : withCategories)
		{
			ProductProximity proximityWithout = getProductProximity(proximityWith.getProduct(), withoutCategories);
			
			if (proximityWithout == null)
				throw new Exception("Não encontrei o equivalente ao produto '" + proximityWith.getProduct().getId() + "' na classificação sem categorias");
			
			proximitiesWithout += proximityWithout.countCloseProducts();

			for (int i = 0; i < proximityWithout.countCloseProducts(); i++)
			{
				Product product = proximityWithout.getCloseProductIndex(i);
				double distanceWithout = proximityWithout.getDistanceIndex(i);
				double distanceWith = getProductDistance(proximityWith, product);
				double error = Math.abs(distanceWith - distanceWithout);
				
				if (error < 0.01)
					similarProximitiesWith++;
			}		
		}
		
		return (100.0 * similarProximitiesWith) / proximitiesWithout;
	}

	/**
	 * Retorna a lista de proximidades de um produto
	 */
	private ProductProximity getProductProximity(Product product, List<ProductProximity> result)
	{
		for (ProductProximity proximity : result)
			if (proximity.getProduct() == product)
				return proximity;
		
		return null;
	}

	/**
	 * Retorna a distância de um produto em uma lista de proximidades
	 */
	private double getProductDistance(ProductProximity proximity, Product closeProduct)
	{
		for (int i = 0; i < proximity.countCloseProducts(); i++)
			if (proximity.getCloseProductIndex(i) == closeProduct)
				return proximity.getDistanceIndex(i);
		
		return 1.0;
	}
}