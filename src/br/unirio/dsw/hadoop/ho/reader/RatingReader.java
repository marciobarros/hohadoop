package br.unirio.dsw.hadoop.ho.reader;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;
import br.unirio.dsw.hadoop.ho.model.RatingType;
import br.unirio.dsw.hadoop.ho.model.User;

/**
 * Classe responsável pela leitura do arquivo de rating de produtos por usuários
 * 
 * @author marcio.barros
 */
public class RatingReader extends DynamoReader
{
	public boolean execute(String fileName, Market market)
	{
		if (!open(fileName))
			return false;
		
		while (next())
		{
			String userId = getStringEntry("UserId", "").toLowerCase();
			String productId = getStringEntry("ProductId", "").toLowerCase();
			int ratingCode = getIntegerEntry("Rating", 0);
			
			User user = market.getUserById(userId);
			
//			if (user == null)
//				System.out.println("Linha " + getLineCounter() + ": não encontrei o usuário com ID " + userId);
			
			Product product = market.getProductById(productId);
			
			if (product == null)
				System.out.println("Linha " + getLineCounter() + ": não encontrei o produto com ID " + productId);
			
			RatingType type = RatingType.get(ratingCode);
			
			if (type == null)
				System.out.println("Linha " + getLineCounter() + ": não encontrei o rating com código " + ratingCode);

			if (user != null && product != null && type != null && type != RatingType.Neutral)
				user.addRating(product, type);			
		}

		return close();
	}
}