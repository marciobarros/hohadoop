package org.hadoop.calc;

import lombok.Getter;

/**
 * Classe que representa uma lista de pares ordenada por distância (minimizada)
 * 
 * @author marcio.barros
 */
public class RankingList<T1, T2>
{
	private RankingListItem<T1, T2>[] items;
	private int len;

	/**
	 * Inicializa a lista ordenada por distância
	 */
	@SuppressWarnings("unchecked")
	public RankingList(int size)
	{
		this.items = new RankingListItem[size];
		this.len = 0;
	}
	
	/**
	 * Retorna o primeiro elemento de uma posição da lista
	 */
	public T1 getFirstItem(int index)
	{
		return items[index].getFirstItem();
	}
	
	/**
	 * Retorna o segundo elemento de uma posição da lista
	 */
	public T2 getSecondItem(int index)
	{
		return items[index].getSecondItem();
	}
	
	/**
	 * Retorna a distância entre os elementos de uma posição da lista
	 */
	public double getDistance(int index)
	{
		return items[index].getDistance();
	}
	
	/**
	 * Adiciona um par de elementos na lista
	 */
	public void add(T1 firstItem, T2 secondItem, double distance)
	{
		if (len == 0)
		{
			items[len++] = new RankingListItem<T1, T2>(firstItem, secondItem, distance);
			return;
		}
		
		if (distance >= items[len-1].getDistance())
		{
			if (len < items.length)
				items[len++] = new RankingListItem<T1, T2>(firstItem, secondItem, distance);
			
			return;
		}

		for (int i = len-2; i >= 0; i--)
		{
			if (distance >= items[i].getDistance())
			{
				insertBefore(i+1, firstItem, secondItem, distance);
				return;
			}
		}

		insertBefore(0, firstItem, secondItem, distance);
	}

	/**
	 * Insere um elemento antes de uma posição da lista
	 */
	private void insertBefore(int position, T1 firstItem, T2 secondItem, double distance)
	{
		if (len < items.length)
			len++;
		
		for (int j = len-1; j > position; j--)
			items[j] = items[j-1];
		
		items[position] = new RankingListItem<T1, T2>(firstItem, secondItem, distance);
	}

	/**
	 * Retorna o tamanho da lista
	 */
	public int size()
	{
		return len;
	}
}

/**
 * Classe que representa um item da lista ordenada por distância
 *  
 * @author marcio.barros
 */
class RankingListItem<T1, T2>
{
	private @Getter T1 firstItem;
	private @Getter T2 secondItem;
	private @Getter double distance;
	
	public RankingListItem(T1 item1, T2 item2, double aDistance)
	{
		this.firstItem = item1;
		this.secondItem = item2;
		this.distance = aDistance;
	}
}