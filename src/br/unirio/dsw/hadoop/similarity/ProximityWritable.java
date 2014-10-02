package br.unirio.dsw.hadoop.similarity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import lombok.Getter;

import org.apache.hadoop.io.Writable;

/**
 * Classe que representa a proximidade de um produto com um produto indicado na chave
 * 
 * @author Marcio Barros
 */
public class ProximityWritable implements Writable/*Comparable*/
{
	private @Getter String productId;
	private @Getter double proximity;

	/**
	 * Inicializa a proximidade
	 */
	public ProximityWritable()
	{
		this("", 0.0);
	}
	
	/**
	 * Inicializa a proximidade, indicando seus parâmetros
	 */
	public ProximityWritable(String productId, double proximity)
	{
		this.productId = productId;
		this.proximity = proximity;
	}

	/**
	 * Carrega os dados de proximidade de um arquivo
	 */
	@Override
	public void readFields(DataInput in) throws IOException
	{
		productId = in.readUTF();
		proximity = in.readDouble();
	}

	/**
	 * Salva os dados de proximidade em um arquivo
	 */
	@Override
	public void write(DataOutput out) throws IOException
	{
		out.writeUTF(productId);
		out.writeDouble(proximity);
	}

	/*@Override
	public int compareTo(Object o)
	{
        ProximityWritable t = (ProximityWritable) o;
        
        int res = productId.compareTo(t.getProductId());
        
        if (res != 0)
        	return res;
        
        double diff = proximity - t.getProximity();
        
        if (diff > 0.001)
        	return 1;
        
        if (diff < -0.001)
        	return -1;
        
		return 0;
	}*/
}