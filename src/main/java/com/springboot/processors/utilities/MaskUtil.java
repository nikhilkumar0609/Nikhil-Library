package com.springboot.processors.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.springboot.processors.entities.MaskPatternEntity;

public class MaskUtil{

private static List<MaskPatternEntity> maskPatterns;
	
	protected MaskUtil(String name, String style) {
		super();
	}

	private static String mask(String message) {
		StringBuffer buffer = new StringBuffer();

		for (MaskPatternEntity maskPattren : getMaskPatterns()) {
			Pattern pattren = Pattern.compile(maskPattren.getRegex());
			Matcher matcher = pattren.matcher(message);
			boolean matcherFoundPattern = false;
			while (matcher.find()) {
				matcherFoundPattern = true;
				matcher.appendReplacement(buffer, maskPattren.getReplacementRegex());
			}
			if (matcherFoundPattern) {
				matcher.appendTail(buffer);
				message = buffer.toString();
			}
			buffer.setLength(0);
		}
		return message;
	}
	
	public static List<MaskPatternEntity> getMaskPatterns() {
		if (maskPatterns == null) {
			maskPatterns = new ArrayList<>();
			
			maskPatterns.add(new MaskPatternEntity("(\"msgTxt\" : \".*OTP for coding application verification is: )(\\d+)(\\..*\".*)", "$1******$3"));
			maskPatterns.add(new MaskPatternEntity("(\"password\"\\s*:\\s*\")(.*)(\"\\s*)", "$1******$3"));
		}
		return maskPatterns;
	}
	
	private static void maskAndPrintData(String logMessage) {
		String maskedMessage = mask(logMessage);
		System.out.println(maskedMessage);
	}
	
	public static void main(String[] args) {
		
		String logMessage = "\"msgTxt\" : \"The OTP for coding application verification is: 966163. Please do not share this OTP with anyone.\"";
		maskAndPrintData(logMessage);
		
		logMessage = "\"password\" : \"Abcd@1234\"";
		maskAndPrintData(logMessage);
	}
	
}
