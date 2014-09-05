package org.hadoop.model;

import lombok.Getter;

/**
 * Classe que representa uma tags de um usuário, com seu contador de importância
 * 
 *  @author marcio.barros
 */
public class UserTag
{
	private @Getter String tag;
	private @Getter short relevance;
	
	/**
	 * Inicializa a tag
	 */
	public UserTag(String tag)
	{
		this.tag = tag;
		this.relevance = 0;
	}

	/**
	 * Incrementa o contador de importância
	 */
	public void increment()
	{
		this.relevance++;
	}

	/**
	 * Decrementa o contador de importância
	 */
	public void decrement()
	{
		this.relevance--;
	}
}