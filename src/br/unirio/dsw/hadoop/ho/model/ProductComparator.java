package br.unirio.dsw.hadoop.ho.model;

import java.util.Comparator;

/**
 * Classe que compara dois produtos em relação a suas identidades
 * 
 * @author marcio.barros
 */
public class ProductComparator implements Comparator<Product>
{
	@Override
	public int compare(Product product1, Product product2)
	{
		return product1.getId().compareTo(product2.getId());
	}
}