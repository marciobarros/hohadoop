package org.hadoop.readers;

import java.util.HashMap;
import java.util.List;

import org.hadoop.model.Market;
import org.hadoop.model.Product;

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
			
			if (title != null && tags.size() > 1)
			{			
				Product product = market.addProduct(id, title);
				
				for (String tag : tags)
					product.addTag(tag.toLowerCase());
			}
		}

		return close();
	}
}