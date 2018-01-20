package com.plaidman1701.lcarsserver03.presentation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import com.plaidman1701.lcarsserver03.entity.Starship;

public class TimeDecorator extends TableDecorator 
{
	public String getDob()

	{
		String blankString = "";
		
		Date launchDate = ((Starship)(this.getCurrentRowObject())).getLaunched();
		if ((launchDate == null) || (blankString.equals(launchDate)))
		{
			return blankString;
		}
		
		DateFormat formatter = new SimpleDateFormat( "yyyy/MM/dd" );
		return formatter.format(launchDate);
	}
}
