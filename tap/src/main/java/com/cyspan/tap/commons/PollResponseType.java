package com.cyspan.tap.commons;

public enum PollResponseType {

	ImageOptions("ImageOption"), ThisOrThatOptions("ThisOrThatOption"), RatingOptions("RatingOption"),
	MultipleOptions("MultipleOption");

	String option;

	private PollResponseType(String option) {
		this.option = option;
	}

	public String getOption() {
		return option;
	}

}
