package org.hadoop.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Classe que representa um produto no mercado
 * 
 * @author marcio.barros
 */
public class Product
{
	private @Getter String id;
	private @Getter String title;
	private List<String> tags;

	/**
	 * Inicializa o produto
	 */
	public Product(String id, String title)
	{
		this.id = id;
		this.title = title;
		this.tags = new ArrayList<String>();
	}

	/**
	 * Adiciona uma tag ao produto
	 */
	public void addTag(String tag)
	{
		tags.add(tag);
	}
	
	/**
	 * Retorna todas as tags do produto
	 */
	public Iterable<String> getTags()
	{
		return tags;
	}

	/**
	 * Verifica se o produto contém uma tag
	 */
	public boolean containsTag(String tag)
	{
		return tags.contains(tag);
	}

	/**
	 * Conta o número de tags do produto
	 */
	public int countTags()
	{
		return tags.size();
	}

	/**
	 * Retorna as tags como uma string
	 */
	public String getTagsAsString() 
	{
		if (tags.size() == 0)
			return "[]";
		
		String result = tags.get(0);
		
		for (int i = 1; i < tags.size(); i++)
			result += ", " + tags.get(i);
		
		return "[" + result + "]";
	}
}