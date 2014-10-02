package br.unirio.dsw.hadoop.similarity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import lombok.Getter;

import org.apache.hadoop.io.Writable;

import br.unirio.dsw.hadoop.ho.model.ProductProximity;

/**
 * Classe que representa a lista de produtos próximos a um produto representado na chave
 * 
 * @author Marcio Barros
 */
public class ProductProximityWritable implements Writable/*Comparable*/
{
	private @Getter ProductProximity proximity;

	/**
	 * Inicializa a lista de produtos próximos
	 */
	public ProductProximityWritable()
	{
		this(new ProductProximity(""));
	}
	
	/**
	 * Inicializa a lista de produtos próximos, indicando seu parâmetro
	 */
	public ProductProximityWritable(ProductProximity pp)
	{
		this.proximity = pp;
	}
	
	/**
	 * Carrega a lista de produtos próximos de um arquivo
	 */
	@Override
	public void readFields(DataInput in) throws IOException
	{
		String s = in.readUTF();
		String[] tokens = s.split(",");
		
		for (int i = 0; i < tokens.length; i += 2)
			proximity.add(tokens[i], Double.parseDouble(tokens[i+1]));
	}

	/**
	 * Escreve a lista de produtos próximos em um arquivo
	 */
	@Override
	public void write(DataOutput out) throws IOException
	{
		out.writeUTF(toString() + "\n");
	}

	/**
	 * Escreve a lista de produtos próximos em uma string
	 */
	@Override
	public String toString()
	{
		String s = "";
		String separator = "";
		
		for (int i = 0; i < proximity.countCloseProducts(); i++)
		{
			s += separator + proximity.getCloseProductIndex(i) + "," + proximity.getDistanceIndex(i);
			separator = ",";
		}
		
		return s;
	}
}