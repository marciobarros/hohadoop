package br.unirio.dsw.hadoop.ho.model;

import lombok.Getter;

/**
 * Enumeração dos tipos de rating de produtos
 * 
 * @author marcio.barros
 */
public enum RatingType
{
	Like(3),
	Neutral(0),
	Unlike(-3);
	
	private @Getter int code;
	
	private RatingType(int code)
	{
		this.code = code;
	}

	public static RatingType get(int code)
	{
		for (RatingType type : values())
			if (type.getCode() == code)
				return type;
		
		return null;
	}
}