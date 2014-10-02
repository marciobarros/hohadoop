package br.unirio.dsw.hadoop.similarity;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import br.unirio.dsw.hadoop.ho.calc.Distance;
import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;
import br.unirio.dsw.hadoop.ho.reader.splitter.ProductReader;

public class Map extends Mapper<LongWritable, Text, Text, ProximityWritable>
{
	private final static double MIN_PROXIMITY_THRESHOLD = 0.5;
	
	private Market market;

	@Override
	public void setup(Context context)
	{
		this.market = new Market();
		String inputFile = context.getConfiguration().get("map.input.file");
		
		try
		{
			new ProductReader().execute(inputFile, market);
		} 
		catch (Exception e)
		{
			System.err.println("Ocorreu um erro ao carregar o arquivo de produtos '" + inputFile + "': " + StringUtils.stringifyException(e));
		}
	}

	/**
	 * Processo de mapping
	 */
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String[] tokens = value.toString().split(",");
		
		if (tokens.length < 3)
		{
			System.err.println("Ocorreu um erro ao carregar um dado do arquivo de produtos: menos de 3 tokens.");
			return;
		}
		
		String id = tokens[0];
		String[] tags = new String[tokens.length-2];
		
		for (int i = 2; i < tokens.length; i++)
			tags[i-2] = tokens[i];

		for (Product relatedProduct : market.getProducts())
		{
			if (relatedProduct.getId().compareTo(id) != 0)
			{
				double distance = Distance.jaccardProductProductDistance(relatedProduct, tags);
				
				if (distance < MIN_PROXIMITY_THRESHOLD)
				{
					ProximityWritable pw = new ProximityWritable(relatedProduct.getId(), distance);
					Text productId = new Text(id);
					context.write(productId, pw);
				}
			}
		}		
	}
}