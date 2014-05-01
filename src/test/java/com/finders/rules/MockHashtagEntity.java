package com.finders.rules;

import twitter4j.HashtagEntity;

public class MockHashtagEntity implements HashtagEntity {
	
	private static final long serialVersionUID = 7611282220284760591L;
	
	private final String text;
	private final int start;
	private final int end;
	
	public MockHashtagEntity(String text, int start, int end) {
		super();
		this.text = text;
		this.start = start;
		this.end = end;
	}

	public String getText() {
		return text;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

}
