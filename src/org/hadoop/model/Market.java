package org.hadoop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que representa o mercado, contendo produtos e usu�rios
 * 
 * @author marcio.barros
 */
public class Market
{
	private List<User> users;
	private List<Product> products;
	private boolean hasSortedUsers = false;
	private boolean hasSortedProducts = false;
	
	/**
	 * Inicializa o mercado
	 */
	public Market()
	{
		this.users = new ArrayList<User>();
		this.products = new ArrayList<Product>();
	}
	
	/**
	 * Adiciona um usu�rio no mercado
	 */
	public User addUser(String id, String userName)
	{
		User user = new User(id, userName);
		users.add(user);
		return user;
	}

	/**
	 * Retorna o n�mero de usu�rios no mercado
	 */
	public int countUsers()
	{
		return users.size();
	}

	/**
	 * Retorna um usu�rio, dado seu �ndice
	 */
	public User getUserByIndex(int index) 
	{
		return users.get(index);
	}

	/**
	 * Retorna um usu�rio, dado seu ID
	 */
	public User getUserById(String id)
	{
		if (!hasSortedUsers)
		{
			Collections.sort(users, new UserComparator());
			hasSortedUsers = true;
		}
		
		int index = Collections.binarySearch(users, new User(id, ""), new UserComparator());
		return (index >= 0) ? users.get(index) : null;
	}
	
	/**
	 * Retorna todos os usu�rios do mercado
	 */
	public Iterable<User> getUsers()
	{
		return users;
	}

	/**
	 * Adiciona um produto no mercado
	 */
	public Product addProduct(String id, String title)
	{
		Product product = new Product(id, title);
		products.add(product);
		return product;
	}

	/**
	 * Retorna o n�mero de produtos no mercado
	 */
	public int countProducts()
	{
		return products.size();
	}

	/**
	 * Retorna um produto, dado seu �ndice
	 */
	public Product getProductByIndex(int index) 
	{
		return products.get(index);
	}

	/**
	 * Retorna um produto, dado seu ID
	 */
	public Product getProductById(String id)
	{
		if (!hasSortedProducts)
		{
			Collections.sort(products, new ProductComparator());
			hasSortedProducts = true;
		}
		
		int index = Collections.binarySearch(products, new Product(id, ""), new ProductComparator());
		return (index >= 0) ? products.get(index) : null;
	}

	/**
	 * Retorna todos os produtos do mercado
	 */
	public Iterable<Product> getProducts()
	{
		return products;
	}
	
	/**
	 * Constr�i o dicion�rio dos produtos
	 */
	public List<String> buildDictionary(Market market)
	{
		List<String> dictionary = new ArrayList<String>();
		
		for (Product product : market.getProducts())
			for (String tag : product.getTags())
				if (!dictionary.contains(tag))
					dictionary.add(tag);
		
		return dictionary;
	}

	/**
	 * Constr�i as tags de cada usu�rio
	 */
	public void buildUserTags()
	{
		for (User user : users)
		{
			for (Rating rating : user.getRatings())
			{
				for (String tag : rating.getProduct().getTags())
				{
					if (rating.getType() == RatingType.Like)
						user.addLikeTag(tag);
					else
						user.addUnlikeTag(tag);
				}
			}
		}
	}
	
	/**
	 * Calcula o n�mero m�dio de tags por usu�rio
	 */
	public double calculateAverageUserTags()
	{
		int count = 0;
		
		for (User user : users)
			count += user.countTags();
					
		return ((double) count) / users.size();
	}
}