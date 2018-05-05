package com.in28minutes.microservices.limitsservice.bean;

public class LimitConfiguration {
	int minimum;
	int maximum;

	public LimitConfiguration() {
	}

	public LimitConfiguration(int minimum, int maximum) {
		super();
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	@Override
	public String toString() {
		return "LimitConfiguration [minimum=" + minimum + ", maximum=" + maximum + "]";
	}
}
