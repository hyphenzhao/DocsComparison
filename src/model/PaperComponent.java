package model;

public abstract class PaperComponent {
	protected String rawData;
	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	protected int offset;
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	protected int rawHash;
	
	public PaperComponent(String s, int offset) {
		this.rawData = s;
		this.offset = offset;
		rawHash = -1;
	}
	
	public abstract int getClassHash();
	
	public int getRawHash() {
		if(rawHash == -1) rawHash = rawData.hashCode();
		return rawHash;
	}
	
	public int getLength() {
		return this.rawData.length();
	}
}
