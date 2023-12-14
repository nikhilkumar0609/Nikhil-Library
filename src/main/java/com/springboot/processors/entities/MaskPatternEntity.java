package com.springboot.processors.entities;

public class MaskPatternEntity {

	private String regex;
	private String replacementRegex;

	public MaskPatternEntity(String regex, String replacementRegex) {
		this.regex = regex;
		this.replacementRegex = replacementRegex;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getReplacementRegex() {
		return replacementRegex;
	}

	public void setReplacementRegex(String replacementRegex) {
		this.replacementRegex = replacementRegex;
	}
	
}
