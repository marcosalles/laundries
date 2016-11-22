package models;

public enum Type {
	WASHER,
	DRYER,
	COMBO;

	public static Type typeOf(int type) {
		return values()[type-1];
	}
}
