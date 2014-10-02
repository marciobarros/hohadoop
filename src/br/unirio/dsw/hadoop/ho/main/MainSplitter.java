package br.unirio.dsw.hadoop.ho.main;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.reader.splitter.ProductReader;

public class MainSplitter
{
	public static void main(String[] args) throws Exception
	{
		Market market = new Market();
		NumberFormat nf5 = new DecimalFormat("00000");

		for (int i = 0; i < 25; i++)
		{
			new ProductReader().execute("data\\split\\part-" + nf5.format(i), market);
			System.out.println("Categoria #" + i + ": " + market.countProducts() + " produtos carregados");
		}
	}
}