package com.plaidman1701.lcarsserver03.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
//public class ResponseCode
public class ResponseCode implements Serializable
{

	protected String code;
	protected String desc;

	public String getCode()
	{
		return code;
	}

	public void setCode( String value )
	{
		this.code = value;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc( String value )
	{
		this.desc = value;
	}

	@Override
	public String toString()
	{
		return "ResponseCode [code=" + code + ", desc=" + desc + "]";
	}

}