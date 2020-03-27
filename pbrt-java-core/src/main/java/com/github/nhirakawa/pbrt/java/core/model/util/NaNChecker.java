package com.github.nhirakawa.pbrt.java.core.model.util;

public final class NaNChecker {

	private NaNChecker() {
		throw new UnsupportedOperationException();
	}

	public static boolean isNaN(double d) {
		return Double.isNaN(d);
	}

	public static boolean isNaN(float f) {
		return Float.isNaN(f);
	}

}
