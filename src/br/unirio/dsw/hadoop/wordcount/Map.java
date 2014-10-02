package br.unirio.dsw.hadoop.wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Mapper do processo Hadoop que calcula o número de vezes que uma palavra ocorre
 * 
 * @author Marcio Barros
 */
public class Map extends Mapper<LongWritable, Text, Text, IntWritable>
{
	/**
	 * Processo de mapping
	 */
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String line = removePonctuation(value.toString());			
		StringTokenizer tokenizer = new StringTokenizer(line);
		
		while (tokenizer.hasMoreTokens())
		{
			String nextWord = tokenizer.nextToken();
			
			if (validWord(nextWord))
			{
				Text word = new Text(nextWord);
				IntWritable one = new IntWritable(1);
				context.write(word, one);
			}
		}
	}

	/**
	 * Determina se uma palavra é válida
	 */
	private boolean validWord(String word)
	{
		int len = word.length();
		
		if (len <= 2)
			return false;

		for (int i = 0; i < len; i++)
			if (!Character.isDigit(word.charAt(i)))
				return true;
		
		return false;
	}

	/**
	 * Remove todos os caracteres de pontuação de uma string
	 */
	private String removePonctuation(String line)
	{
		int len = line.length();
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < len; i++)
		{
			char c = line.charAt(i);
			
			if (!isPontuation(c))
				result.append(c);
		}
		
		return result.toString();
	}

	/**
	 * Determina se um caractere é de pontuação
	 */
	private boolean isPontuation(char c)
	{
		final String DISCARDED = "/\\\'\".,;:()#@[]{}%";

		int len = DISCARDED.length();
		
		for (int i = 0; i < len; i++)
			if (DISCARDED.charAt(i) == c)
				return true;
		
		return false;
	}
}