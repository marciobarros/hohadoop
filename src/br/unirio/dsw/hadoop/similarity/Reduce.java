package br.unirio.dsw.hadoop.similarity;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import br.unirio.dsw.hadoop.ho.model.ProductProximity;

/**
 * Classe reducer para um processo Hadoop de cálculo de similaridade
 * 
 * @author Marcio Barros
 */
public class Reduce extends Reducer<Text, ProximityWritable, Text, ProductProximityWritable>
{
	/**
	 * Insere os diversos produtos similares em uma lista ordenada pela proximidade
	 */
	@Override
	protected void reduce(Text key, Iterable<ProximityWritable> values, Context context) throws IOException, InterruptedException
	{
		ProductProximity pp = new ProductProximity(key.toString());
		
		for (ProximityWritable value : values)
			pp.add(value.getProductId(), value.getProximity());

		context.write(key, new ProductProximityWritable(pp));
	}
}