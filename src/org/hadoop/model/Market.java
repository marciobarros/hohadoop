package org.hadoop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que representa o mercado, contendo produtos e usuários
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
	 * Adiciona um usuário no mercado
	 */
	public User addUser(String id, String userName)
	{
		User user = new User(id, userName);
		users.add(user);
		return user;
	}

	/**
	 * Retorna o número de usuários no mercado
	 */
	public int countUsers()
	{
		return users.size();
	}

	/**
	 * Retorna um usuário, dado seu índice
	 */
	public User getUserByIndex(int index) 
	{
		return users.get(index);
	}

	/**
	 * Retorna um usuário, dado seu ID
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
	 * Retorna todos os usuários do mercado
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
	 * Retorna o número de produtos no mercado
	 */
	public int countProducts()
	{
		return products.size();
	}

	/**
	 * Retorna um produto, dado seu índice
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
	 * Constrói a lista com todas as tags dos produtos
	 */
	public List<String> getAllTags()
	{
		List<String> tags = new ArrayList<String>();
		
		for (Product product : products)
			for (String tag : product.getTags())
				if (!tags.contains(tag))
					tags.add(tag);
		
		return tags;
	}
	
	/**
	 * Constrói a lista com todas as categorias dos produtos
	 */
	public List<String> getAllCategories()
	{
		List<String> categories = new ArrayList<String>();
		
		for (Product product : products)
			for (String category : product.getCategories())
				if (!categories.contains(category))
					categories.add(category);
		
		return categories;
	}

	/**
	 * Constrói as tags de cada usuário
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
	 * Calcula o número médio de tags por usuário
	 */
	public double calculateAverageUserTags()
	{
		int count = 0;
		
		for (User user : users)
			count += user.countTags();
					
		return ((double) count) / users.size();
	}
}