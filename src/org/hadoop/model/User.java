package org.hadoop.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Classe que representa um usu�rio no mercado
 * 
 * @author marcio.barros
 */
public class User
{
	private @Getter String id;
	private @Getter String userName;
	private List<Rating> ratings;
	private List<UserTag> tags;

	/**
	 * Inicializa um usu�rio
	 */
	public User(String id, String userName)
	{
		this.id = id;
		this.userName = userName;
		this.ratings = new ArrayList<Rating>();
		this.tags = new ArrayList<UserTag>();
	}
	
	/**
	 * Adiciona um rating a um usu�rio
	 */
	public void addRating(Product product, RatingType type)
	{
		ratings.add(new Rating(product, type));
	}
	
	/**
	 * Retorna todos os ratings do usu�rio
	 */
	public Iterable<Rating> getRatings()
	{
		return ratings;
	}
	
	/**
	 * Adiciona a tag de um produto desejado para o usu�rio
	 */
	public void addLikeTag(String tag)
	{
		for (UserTag walker : tags)
		{
			if (walker.getTag().compareTo(tag) == 0)
			{
				walker.increment();
				return;
			}			
		}
		
		UserTag newElement = new UserTag(tag);
		newElement.increment();
		tags.add(newElement);
	}
	
	/**
	 * Adiciona a tag de um produto n�o desejado para o usu�rio
	 */
	public void addUnlikeTag(String tag)
	{
		for (UserTag walker : tags)
		{
			if (walker.getTag().compareTo(tag) == 0)
			{
				walker.decrement();
				return;
			}			
		}
		
		UserTag newElement = new UserTag(tag);
		newElement.decrement();
		tags.add(newElement);
	}

	/**
	 * Conta o n�mero de tags do usu�rio
	 */
	public int countTags()
	{
		return tags.size();
	}

	/**
	 * Retorna a lista de tags do usu�rio
	 */
	public Iterable<UserTag> getTags()
	{
		return tags;
	}

	/**
	 * Retorna uma tag do usu�rio
	 */
	public UserTag getTag(String tag)
	{
		for (UserTag userTag : tags)
			if (userTag.getTag().compareTo(tag) == 0)
				return userTag;
		
		return null;
	}

	/**
	 * Retorna a import�ncia de uma tag do usu�rio
	 */
	public int getTagRelevance(String tag)
	{
		for (UserTag userTag : tags)
			if (userTag.getTag().compareTo(tag) == 0)
				return userTag.getRelevance();
		
		return 0;
	}
}