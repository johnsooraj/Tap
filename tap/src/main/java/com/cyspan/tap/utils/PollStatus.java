package com.cyspan.tap.utils;

public enum PollStatus {

	ActivePoll(0), DeletedPoll(1), BlockedPoll(2), ObjectiveContentPoll(3);

	private int value;

	private PollStatus(int value) {
		this.value = value;
	}

	public byte getValue() {
		return (byte) value;
	}

}
