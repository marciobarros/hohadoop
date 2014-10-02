package br.unirio.dsw.hadoop.ho.calc;

import java.util.List;

import br.unirio.dsw.hadoop.ho.model.ProductProximity;

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
			if (getProductProximity(proximity.getProductId(), withCategories) == null)
				proximitiesWithout += proximity.countCloseProducts();
		
		for (ProductProximity proximityWith : withCategories)
		{
			ProductProximity proximityWithout = getProductProximity(proximityWith.getProductId(), withoutCategories);
			
			if (proximityWithout == null)
				throw new Exception("Não encontrei o equivalente ao produto '" + proximityWith.getProductId() + "' na classificação sem categorias");
			
			proximitiesWithout += proximityWithout.countCloseProducts();

			for (int i = 0; i < proximityWithout.countCloseProducts(); i++)
			{
				String productId = proximityWithout.getCloseProductIndex(i);
				double distanceWithout = proximityWithout.getDistanceIndex(i);
				double distanceWith = getProductDistance(proximityWith, productId);
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
	private ProductProximity getProductProximity(String productId, List<ProductProximity> result)
	{
		for (ProductProximity proximity : result)
			if (proximity.getProductId().compareTo(productId) == 0)
				return proximity;
		
		return null;
	}

	/**
	 * Retorna a distância de um produto em uma lista de proximidades
	 */
	private double getProductDistance(ProductProximity proximity, String closeProductId)
	{
		for (int i = 0; i < proximity.countCloseProducts(); i++)
			if (proximity.getCloseProductIndex(i).compareTo(closeProductId) == 0)
				return proximity.getDistanceIndex(i);
		
		return 1.0;
	}
}