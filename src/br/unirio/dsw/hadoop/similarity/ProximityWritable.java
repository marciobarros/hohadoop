package br.unirio.dsw.hadoop.similarity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import lombok.Getter;

import org.apache.hadoop.io.WritableComparable;

@SuppressWarnings("rawtypes")
public class ProximityWritable implements WritableComparable
{
	private @Getter String productId;
	private @Getter double proximity;
	
	public ProximityWritable(String productId, double proximity)
	{
		this.productId = productId;
		this.proximity = proximity;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException
	{
		this.productId = in.readLine();
		this.proximity = in.readDouble();
	}

	@Override
	public void write(DataOutput out) throws IOException
	{
		out.writeChars(productId);
		out.writeDouble(proximity);
	}

	@Override
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
	}
}