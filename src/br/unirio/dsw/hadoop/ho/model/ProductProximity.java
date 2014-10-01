package br.unirio.dsw.hadoop.ho.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Classe que representa uma lista de produtos próximos a outro produto
 * 
 * @author Marcio Barros
 */
public class ProductProximity
{
	private static final int MAX_CLOSE_PRODUCTS = 10; 
	
	private @Getter Product product;	
	private List<CloseProduct> closeProducts;

	/**
	 * Inicializa a lista de produtos próximos a um produto
	 */
	public ProductProximity(Product product)
	{
		this.product = product;
		this.closeProducts = new ArrayList<CloseProduct>();
	}
	
	/**
	 * Adiciona um produto próximo ao produto base da lista
	 */
	public void add(Product product, double distance)
	{
		int len = closeProducts.size();
		
		if (len == 0)
		{
			closeProducts.add(new CloseProduct(product, distance));
			return;
		}
		
		for (int i = 0; i < len; i++)
		{
			CloseProduct w = closeProducts.get(i);
			
			if (w.getProduct() == product)
			{
				if (w.getDistance() > distance)
				{
					closeProducts.remove(i);
					break;
				}
				else
					return;
			}
		}
		
		double greaterDistance = closeProducts.get(len-1).getDistance();
		
		if (distance > greaterDistance)
		{
			if (len < MAX_CLOSE_PRODUCTS)
				closeProducts.add(new CloseProduct(product, distance));

			return;
		}
		
		if (len == MAX_CLOSE_PRODUCTS)
			closeProducts.remove(--len);
		
		for (int i = 0; i < len; i++)
			if (distance < closeProducts.get(i).getDistance())
			{
				closeProducts.add(i, new CloseProduct(product, distance));
				return;
			}

		closeProducts.add(new CloseProduct(product, distance));
	}
	
	/**
	 * Conta o número de produtos próximos ao produto base da lista
	 */
	public int countCloseProducts()
	{
		return closeProducts.size();
	}
	
	/**
	 * Retorna um produto próximo ao produto base, dado seu índice
	 */
	public Product getCloseProductIndex(int index)
	{
		return closeProducts.get(index).getProduct();
	}
	
	/**
	 * Retorna a distância de um produto próximo ao produto base, dado seu índice
	 */
	public double getDistanceIndex(int index)
	{
		return closeProducts.get(index).getDistance();
	}
}

/**
 * Classe que representa um produto próximo a um produto base
 * 
 * @author Marcio Barros
 */
class CloseProduct
{
	private @Getter Product product;
	private @Getter double distance;
	
	public CloseProduct(Product product, double distance)
	{
		this.product = product;
		this.distance = distance;
	}
}