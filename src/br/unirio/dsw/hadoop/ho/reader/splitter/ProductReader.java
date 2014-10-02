package br.unirio.dsw.hadoop.ho.reader.splitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;

/**
 * Classe responsável por carregar um arquivo de produtos resumido
 * 
 * @author marcio.barros
 */
public class ProductReader
{
	/**
	 * Carrega um arquivo de produtos resumido
	 */
	public void execute(String fileName, Market market) throws Exception
	{
		InputStream is = new FileInputStream(fileName);
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader reader = new BufferedReader(isr);

		String line = null;
		
		while ((line = reader.readLine()) != null)
			processProduct(reader, line, market);

		reader.close();
	}

	/**
 	 * Carrega um produto do arquivo de produtos resumidos
 	 */
	private void processProduct(BufferedReader reader, String line, Market market) throws Exception 
	{
		String[] tokens = line.split(",");
		
		if (tokens.length < 3)
			throw new Exception("Formato de linha de produto inválido");
		
		Product product = market.addProduct(tokens[0], tokens[1]);
		
		for (int i = 2; i < tokens.length; i++)
			product.addTag(tokens[i]);
	}
}