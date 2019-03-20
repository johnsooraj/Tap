package com.cyspan.tap.subscription.models;

import java.util.HashMap;

public enum SubscriptionConstants {

	DRAFT(0), FINISHED(1), MULTIPLE_CHOICE(2), RATING(3), FREE_COMMENT(4), IMAGES(5);

	private final int value;

	SubscriptionConstants(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	final static HashMap<Integer, SubscriptionConstants> actions = new HashMap<>();
	static {
		for (SubscriptionConstants e : SubscriptionConstants.values()) {
			actions.put(e.value, e);
		}
	}
	
	public static SubscriptionConstants fromAction(String scn) {
		return actions.get(new Integer(scn).intValue());
	}
}
