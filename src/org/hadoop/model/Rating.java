package org.hadoop.model;

import lombok.Getter;

/**
 * Classe que representa um rating do usuário para um produto
 * 
 * @author marcio.barros
 */
public class Rating
{
	private @Getter Product product;
	private @Getter RatingType type;

	/**
	 * Inicializa o rating
	 */
	public Rating(Product product, RatingType type)
	{
		this.product = product;
		this.type = type;
	}
}