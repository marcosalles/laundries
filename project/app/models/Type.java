package models;

public enum Type {
	WASHER,
	DRYER,
	COMBO;

	public Type typeOf(int type) {
		return values()[type-1];
	}
}
