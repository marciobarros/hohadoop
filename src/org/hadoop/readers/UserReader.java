package org.hadoop.readers;

import org.hadoop.model.Market;

/**
 * Classe responsável pela leitura do arquivo de usuários
 * 
 * @author marcio.barros
 */
public class UserReader extends DynamoReader
{
	public boolean execute(String fileName, Market market)
	{
		if (!open(fileName))
			return false;
		
		while (next())
		{
			String id = getStringEntry("UserId", "").toLowerCase();
			String userName = getStringEntry("UserName", "");
			market.addUser(id, userName);
		}
		
		return close();
	}
}