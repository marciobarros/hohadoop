package org.hadoop.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

/**
 * Superclasse dos diferentes tipos de leitores de arquivos exportados pelo Dynamo DB
 * 
 * @author marcio.barros
 */
public class DynamoReader
{
	private BufferedReader reader;
	private @Getter int lineCounter;
	private HashMap<String, String> currentEntries;
	
	/**
	 * Inicializa o leitor
	 */
	public DynamoReader()
	{
		this.currentEntries = new HashMap<String, String>();
	}
	
	/**
	 * Abre um arquivo exportado pelo Dynamo DB
	 */
	public boolean open(String fileName)
	{
		try
		{
			InputStream is = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			this.reader = new BufferedReader(isr);
			this.lineCounter = 0;
			return true;
		}
		catch(Exception e)
		{
			this.reader = null;
			return false;
		}
	}

	/**
	 * Fecha um arquivo exportado pelo Dynamo DB
	 */
	public boolean close()
	{
		try
		{
			if (reader != null)
				reader.close();
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	/**
	 * Pega a próxima linha de um arquivo exportado pelo Dynamo DB
	 */
	public boolean next()
	{
		try
		{
			String line = reader.readLine();
			lineCounter++;
			
			if (line != null)
			{
				currentEntries.clear();
				String[] tokens = line.split("" + ((char)0x02));
				
				for (String token : tokens)
				{
					String[] parts = token.split("" + ((char)0x03));
					
					if (parts.length == 2)
						currentEntries.put(parts[0], parts[1]);
				}
			}
			
			return (line != null);
		}
		catch(IOException e)
		{
			return false;
		}
	}

	/**
	 * Retorna um campo no formato string de um arquivo exportado pelo Dynamo DB
	 */
	public String getStringEntry(String name, String _default)
	{
		String value = currentEntries.get(name);
		
		if (value == null)
			return _default;
		
		value = value.replace("\\\\\\\"", "\"");
		
		if (value.startsWith("{\"s\":\"") && value.endsWith("\"}"))
			return value.substring(6, value.length()-2);
		
		return _default;
	}

	/**
	 * Retorna um campo no formato inteiro de um arquivo exportado pelo Dynamo DB
	 */
	public int getIntegerEntry(String name, int _default)
	{
		String value = currentEntries.get(name);
		
		if (value == null)
			return _default;
		
		if (value.startsWith("{\"n\":\"") && value.endsWith("\"}"))
		{
			value = value.substring(6, value.length()-2);
			return Integer.parseInt(value);
		}
		
		return _default;
	}

	/**
	 * Retorna um campo no formato hashmap de um arquivo exportado pelo Dynamo DB
	 */
	public HashMap<String, String> getHashEntry(String name)
	{
		HashMap<String, String> entries = new HashMap<String, String>();
		
		String value = currentEntries.get(name);
		
		if (value == null)
			return entries;
		
		if (value.startsWith("{\"s\":\"{") && value.endsWith("}\"}"))
		{
			String s = value.substring(7, value.length()-3)
					.replace("\\\\\\\"", "\"");
			
			List<String> items = DynamoTokenizer.split(s, ',');
			List<String> parts = new ArrayList<String>();
			
			for (String item : items)
			{
				DynamoTokenizer.split(item, parts, ':');
				
				if (parts.size() == 2)
				{
					String key = parts.get(0);
					
					if (key.startsWith("\\\"") && key.endsWith("\\\""))
						key = key.substring(2, key.length()-2);

					String keyValue = parts.get(1);
					
					if (keyValue.startsWith("\\\"") && keyValue.endsWith("\\\""))
						keyValue = keyValue.substring(2, keyValue.length()-2);
					
					entries.put(key, keyValue);
				}
			}
		}
		
		return entries;
	}

	/**
	 * Retorna um campo no formato lista de um arquivo exportado pelo Dynamo DB
	 */
	public List<String> getListEntry(String name)
	{
		List<String> entries = new ArrayList<String>();
		
		String value = currentEntries.get(name);
		
		if (value == null)
			return entries;
		
		if (value.startsWith("{\"s\":\"[") && value.endsWith("]\"}"))
		{
			String s = value.substring(7, value.length()-3)
				.replace("\\\\\\\"", "\"");
			
			DynamoTokenizer.split(s, entries, ',');
			
			for (int i = entries.size()-1; i >= 0; i--)
			{
				String entry = entries.get(i);
				
				if (entry.startsWith("\\\"") && entry.endsWith("\\\""))
					entries.set(i, entry.substring(2, entry.length()-2));
			}
		}
		
		return entries;
	}
}