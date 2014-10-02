package br.unirio.dsw.hadoop.similarity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import lombok.Getter;

import org.apache.hadoop.io.WritableComparable;

import br.unirio.dsw.hadoop.ho.model.ProductProximity;

@SuppressWarnings("rawtypes")
public class ProductProximityWritable implements WritableComparable
{
	private @Getter ProductProximity proximity;
	
	public ProductProximityWritable(ProductProximity pp)
	{
		this.proximity = pp;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException
	{
		String s = in.readLine();
		String[] tokens = s.split(",");
		
		for (int i = 0; i < tokens.length; i += 2)
			proximity.add(tokens[i], Double.parseDouble(tokens[i+1]));
	}

	@Override
	public void write(DataOutput out) throws IOException
	{
		String s = "";
		String separator = "";
		
		for (int i = 0; i < proximity.countCloseProducts(); i++)
		{
			s += separator + proximity.getCloseProductIndex(i) + "," + proximity.getDistanceIndex(i);
			separator = ",";
		}
		
		out.writeChars(s + "\n");
	}

	@Override
	public int compareTo(Object o)
	{
        ProductProximityWritable t = (ProductProximityWritable) o;
        return t.compareTo(proximity);
	}
}