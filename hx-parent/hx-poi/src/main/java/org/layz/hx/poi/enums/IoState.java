package org.layz.hx.poi.enums;
/***
 * @author 847792
 */
public enum IoState {
	INITED(1),

	BEGAN(2),

	CHECKED(3),

	READING(4),

	ENDED(5);

	private final int value;

	private IoState(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}
