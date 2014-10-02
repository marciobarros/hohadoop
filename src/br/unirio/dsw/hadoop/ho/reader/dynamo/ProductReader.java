package br.unirio.dsw.hadoop.ho.reader.dynamo;

import java.util.HashMap;
import java.util.List;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;

/**
 * Classe responsável pela leitura do arquivo de produtos
 * 
 * @author marcio.barros
 */
public class ProductReader extends DynamoReader
{
	public boolean execute(String fileName, Market market)
	{
		if (!open(fileName))
			return false;
		
		while (next())
		{
			String id = getStringEntry("Id", "").toLowerCase();
			
			HashMap<String, String> titleHash = getHashEntry("lnTitle");
			String title = titleHash.get("pt");
			
			if (title == null)
				title = titleHash.get("en");

			List<String> tags = getListEntry("Tags");
			List<String> categories = getListEntry("Categorias");
			
			if (title != null && tags.size() > 1 && categories.size() > 0)
			{			
				Product product = market.addProduct(id, title);
				
				for (String tag : tags)
					product.addTag(tag.toLowerCase());
				
				for (String category : categories)
					product.addCategory(category.toLowerCase());
			}
		}

		return close();
	}
}