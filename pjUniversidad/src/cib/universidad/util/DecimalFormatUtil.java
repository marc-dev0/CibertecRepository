package cib.universidad.util;

import java.text.DecimalFormat;

public class DecimalFormatUtil{
	public final static String format = "###,###,###,##0.00";
	private static DecimalFormat df = new DecimalFormat(format);

	public static String getTextFormatted(Object value){
		return df.format(value);

	}
}
