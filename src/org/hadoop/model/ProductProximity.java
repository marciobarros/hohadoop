package org.hadoop.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ProductProximity
{
	private @Getter Product product;	
	private List<WeightedProduct> closeProducts;
	
	public ProductProximity(Product product)
	{
		this.product = product;
		this.closeProducts = new ArrayList<WeightedProduct>();
	}
	
	public void add(Product product, double distance)
	{
		int len = closeProducts.size();
		
		if (len == 0)
		{
			closeProducts.add(new WeightedProduct(product, distance));
			return;
		}
		
		double greaterDistance = closeProducts.get(len-1).getDistance();
		
		if (distance > greaterDistance)
		{
			if (len < 10)
				closeProducts.add(new WeightedProduct(product, distance));

			return;
		}
		
		if (len == 10)
			closeProducts.remove(--len);
		
		for (int i = 0; i < len; i++)
			if (distance < closeProducts.get(i).getDistance())
			{
				closeProducts.add(i, new WeightedProduct(product, distance));
				return;
			}

		closeProducts.add(new WeightedProduct(product, distance));
	}
	
	public int countCloseProducts()
	{
		return closeProducts.size();
	}
	
	public Product getCloseProductIndex(int index)
	{
		return closeProducts.get(index).getProduct();
	}
	
	public double getDistanceIndex(int index)
	{
		return closeProducts.get(index).getDistance();
	}
}

class WeightedProduct
{
	private @Getter Product product;
	private @Getter double distance;
	
	public WeightedProduct(Product product, double distance)
	{
		this.product = product;
		this.distance = distance;
	}
}