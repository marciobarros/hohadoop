package br.unirio.dsw.hadoop.ho.calc;

import br.unirio.dsw.hadoop.ho.model.Product;
import br.unirio.dsw.hadoop.ho.model.User;
import br.unirio.dsw.hadoop.ho.model.UserTag;

/**
 * Classes para cálculo de distância entre elementos
 * 
 * @author marcio.barros
 */
public class Distance
{
	/**
	 * Calcula a distância entre dois usuários
	 */
	public static double calculateUserUserProximity(User user1, User user2)
	{
		int totalRelevance = 0;
		int commonTags = 0;
		
		for (UserTag tag1 : user1.getTags())
		{
			UserTag tag2 = user2.getTag(tag1.getTag());
			
			if (tag2 != null)
			{
				commonTags++;
				int diff = tag1.getRelevance() - tag2.getRelevance();
				totalRelevance += diff * diff;
			}
		}

		return (commonTags == 0) ? 0.0 : Math.sqrt(totalRelevance / commonTags); 
	}

	/**
	 * Calcula a distância entre dois produtos pelo método Jaccard (varia de 0 a 1)
	 */
	public static double jaccardProductProductDistance(Product product1, Product product2)
	{
		int intersection = 0;
		
		for (String tag : product1.getTags())
			if (product2.containsTag(tag))
				intersection++;
		
		int tagCount1 = product1.countTags();
		int tagCount2 = product2.countTags();
		int union = tagCount1 + tagCount2 - intersection;
		
		return (union == 0) ? 1.0 : 1.0 - ((double)intersection) / union;
	}

	/**
	 * Calcula a distância entre dois produtos pelo método euclidiano (varia de 0 a 1)
	 */
	public static double euclidianProductProductDistance(Product product1, Product product2)
	{
		int intersection = 0;
		
		for (String tag : product1.getTags())
			if (product2.containsTag(tag))
				intersection++;
		
		int tagCount1 = product1.countTags();
		int uniqueTags1 = tagCount1 - intersection; 

		int tagCount2 = product2.countTags();
		int uniqueTags2 = tagCount2 - intersection;
		
		int uniqueTags = uniqueTags1 + uniqueTags2 + intersection;
		return (uniqueTags == 0) ? 1.0 : 1.0 - Math.sqrt(uniqueTags1 + uniqueTags2) / uniqueTags; 
	}
}