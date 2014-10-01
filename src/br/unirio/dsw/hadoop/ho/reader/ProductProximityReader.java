package br.unirio.dsw.hadoop.ho.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;
import br.unirio.dsw.hadoop.ho.model.ProductProximity;

/**
 * Classe responsável por carregar um arquivo de arquivos próximos
 * 
 * @author marcio.barros
 */
public class ProductProximityReader
{
	/**
	 * Carrega um arquivo de produtos próximos 
	 */
	public List<ProductProximity> execute(String fileName, Market market) throws Exception
	{
		List<ProductProximity> results = new ArrayList<ProductProximity>();
		
		InputStream is = new FileInputStream(fileName);
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader reader = new BufferedReader(isr);

		String line = null;
		
		while ((line = reader.readLine()) != null)
			processProduct(reader, line, market, results);

		reader.close();
		return results;
	}

	/**
 	 * Carrega um produto do arquivo de produtos próximos
 	 */
	private void processProduct(BufferedReader reader, String line, Market market, List<ProductProximity> results) throws Exception 
	{
		String[] tokens = line.split("\t");
		
		if (tokens.length != 3)
			throw new Exception("Formato de linha de produto inválido");
		
		if (tokens[0].compareTo("PROD") != 0)
			throw new Exception("Formato de linha de produto inválido: espero campo PROD");
			
		Product product = market.getProductById(tokens[1]); 
		
		if (product == null)
			throw new Exception("Formato de linha de produto inválido: produto não identificado (" + tokens[1] + ")");
			
		ProductProximity proximity = new ProductProximity(product);
		results.add(proximity);
			
		while ((line = reader.readLine()) != null && line.length() > 0)
			processProductDetail(line, market, proximity);
	}

	/**
 	 * Carrega os produtos próximos a um produto
 	 */
	private void processProductDetail(String line, Market market, ProductProximity proximity) throws Exception 
	{
		String[] tokens = line.split("\t");
		
		if (tokens.length != 3)
			throw new Exception("Formato de detalhe próximo inválido");
			
		double distance = Double.parseDouble(tokens[0].replace(',', '.'));
		Product closeProduct = market.getProductById(tokens[1]); 
		
		if (closeProduct == null)
			throw new Exception("Formato de detalhe de produto inválido: produto não identificado (" + tokens[1] + ")");
			
		proximity.add(closeProduct, distance);
	}
}