package org.hadoop.model;

import lombok.Getter;

/**
 * Classe que representa uma tags de um usu�rio, com seu contador de import�ncia
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
	 * Incrementa o contador de import�ncia
	 */
	public void increment()
	{
		this.relevance++;
	}

	/**
	 * Decrementa o contador de import�ncia
	 */
	public void decrement()
	{
		this.relevance--;
	}
}