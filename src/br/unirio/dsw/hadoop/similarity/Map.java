package br.unirio.dsw.hadoop.similarity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.StringUtils;

import br.unirio.dsw.hadoop.ho.calc.Distance;
import br.unirio.dsw.hadoop.ho.model.Market;
import br.unirio.dsw.hadoop.ho.model.Product;

public class Map extends Mapper<LongWritable, Text, Text, ProximityWritable>
{
	private final static double MIN_PROXIMITY_THRESHOLD = 0.5;

	private Market market;

	@Override
	public void setup(Context context)
	{
		this.market = new Market();
		
		Configuration conf = context.getConfiguration();
		String inputFile = ((FileSplit) context.getInputSplit()).getPath().toString();
		System.err.println("Arquivo de produtos: " + inputFile);

		try
		{
			FileSystem fs = FileSystem.get(new URI("s3://hadoop-example-dsw"), conf);
			Path path = new Path(inputFile.substring(23));
			
			if (fs.exists(path))
			{
				FSDataInputStream fis = fs.open(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String line = null;

				while ((line = br.readLine()) != null && line.trim().length() > 0)
				{
					String[] tokens = line.split(",");
					
					if (tokens.length < 3)
						throw new Exception("Formato de linha de produto inválido");
					
					Product product = market.addProduct(tokens[0], tokens[1]);
					
					for (int i = 2; i < tokens.length; i++)
						product.addTag(tokens[i]);
				}

				br.close();
			}
			
		} catch (Exception e)
		{
			System.err.println("Ocorreu um erro ao carregar o arquivo de produtos '" + inputFile + "': " + StringUtils.stringifyException(e));
		}
	}

	private int counter = 0;
	
	/**
	 * Processo de mapping
	 */
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String[] tokens = value.toString().split(",");

		System.err.println("Mapper com " + market.countProducts() + " produtos ...");
		
		if (counter++ % 1000 == 0)
			System.err.println("Entrada: " + value.toString());

		if (tokens.length < 3)
		{
			System.err.println("Ocorreu um erro ao carregar um dado do arquivo de produtos: menos de 3 tokens.");
			return;
		}

		String id = tokens[0];
		String[] tags = new String[tokens.length - 2];

		for (int i = 2; i < tokens.length; i++)
			tags[i - 2] = tokens[i];

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