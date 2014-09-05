package org.hadoop.readers;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por tokenizar strings do Dynamo DB
 * 
 * @author marcio.barros
 */
public class DynamoTokenizer
{
	/**
	 * Tokeniza uma string, retornando uma lista de tokens
	 */
	public static List<String> split(String line, char separador)
	{
		List<String> tokens = new ArrayList<String>();
		split(line, tokens, separador);
		return tokens;
	}
	
	/**
	 * Tokeniza uma string, preenchendo uma lista de tokens
	 */
	public static void split(String line, List<String> tokens, char separador)
	{
		boolean inString = false;
		boolean lastIsBar = false;
		
		StringBuffer token = new StringBuffer();
		int len = line.length();
		tokens.clear();
		
		for (int i = 0; i < len; i++)
		{
			char c = line.charAt(i);
			
			if (c == '\\')
			{
				lastIsBar = true;
				token.append("\\");
			}
			else if (c == '"')
			{
				if (lastIsBar)
					inString = !inString;

				lastIsBar = false;
				token.append("\"");
			}
			else if (c == separador)
			{
				lastIsBar = false;

				if (inString)
					token.append(c);
				else
				{
					if (token.length() > 0)
						tokens.add(token.toString());
					
					token = new StringBuffer();
				}
			}
			else
			{
				lastIsBar = false;
				token.append(c);
			}
		}

		if (token.length() > 0)
			tokens.add(token.toString());
	}
}