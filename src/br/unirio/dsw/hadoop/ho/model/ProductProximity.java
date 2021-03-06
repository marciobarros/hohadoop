package br.unirio.dsw.hadoop.ho.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Classe que representa uma lista de produtos pr�ximos a outro produto
 * 
 * @author Marcio Barros
 */
public class ProductProximity
{
	private static final int MAX_CLOSE_PRODUCTS = 10; 
	
	private @Getter String productId;	
	private List<CloseProduct> closeProducts;

	/**
	 * Inicializa a lista de produtos pr�ximos a um produto
	 */
	public ProductProximity(String productId)
	{
		this.productId = productId;
		this.closeProducts = new ArrayList<CloseProduct>();
	}
	
	/**
	 * Adiciona um produto pr�ximo ao produto base da lista
	 */
	public void add(String productId, double distance)
	{
		int len = closeProducts.size();
		
		if (len == 0)
		{
			closeProducts.add(new CloseProduct(productId, distance));
			return;
		}
		
		for (int i = 0; i < len; i++)
		{
			CloseProduct w = closeProducts.get(i);
			
			if (w.getProductId().compareTo(productId) == 0)
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
				closeProducts.add(new CloseProduct(productId, distance));

			return;
		}
		
		if (len == MAX_CLOSE_PRODUCTS)
			closeProducts.remove(--len);
		
		for (int i = 0; i < len; i++)
			if (distance < closeProducts.get(i).getDistance())
			{
				closeProducts.add(i, new CloseProduct(productId, distance));
				return;
			}

		closeProducts.add(new CloseProduct(productId, distance));
	}
	
	/**
	 * Conta o n�mero de produtos pr�ximos ao produto base da lista
	 */
	public int countCloseProducts()
	{
		return closeProducts.size();
	}
	
	/**
	 * Retorna um produto pr�ximo ao produto base, dado seu �ndice
	 */
	public String getCloseProductIndex(int index)
	{
		return closeProducts.get(index).getProductId();
	}
	
	/**
	 * Retorna a dist�ncia de um produto pr�ximo ao produto base, dado seu �ndice
	 */
	public double getDistanceIndex(int index)
	{
		return closeProducts.get(index).getDistance();
	}
}

/**
 * Classe que representa um produto pr�ximo a um produto base
 * 
 * @author Marcio Barros
 */
class CloseProduct
{
	private @Getter String productId;
	private @Getter double distance;
	
	public CloseProduct(String productId, double distance)
	{
		this.productId = productId;
		this.distance = distance;
	}
}