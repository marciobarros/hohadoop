package br.unirio.dsw.hadoop.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Reducer para um processo Hadoop que calcula o número de vezes que uma palavra ocorre
 * 
 * @author Marcio Barros
 */
public class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>
{
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
	{
		int sum = 0;
		
		for (IntWritable value : values)
			sum += value.get();

		context.write(key, new IntWritable(sum));
	}
}