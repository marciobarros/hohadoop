package org.hadoop.model;

import java.util.Comparator;

/**
 * Classe que compara dois usuários em relação a suas identidades
 * 
 * @author marcio.barros
 */
public class UserComparator implements Comparator<User>
{
	@Override
	public int compare(User user1, User user2)
	{
		return user1.getId().compareTo(user2.getId());
	}
}