package model;

import java.util.ArrayList;

public class HighlightArea {
	public ArrayList<Integer> start, end, start1, end1;
	public HighlightArea() {
		start = new ArrayList<Integer>();
		end = new ArrayList<Integer>();
		start1 = new ArrayList<Integer>();
		end1 = new ArrayList<Integer>();
	}
	public int size() {
		return start.size();
	}
	public int size1() {
		return start1.size();
	}
}
