package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Sentence extends PaperComponent{
	private ArrayList<String> wordsArrayList;
	private ArrayList<Integer> wordsOffsetArrayList;
	public ArrayList<Integer> wordsHashCode;
	private int classHash;
	
	public Sentence(String s, int offset) {
		super(s, offset);
		classHash = -1;
		wordsArrayList = new ArrayList<String>();
		wordsOffsetArrayList = new ArrayList<Integer>();
		wordsHashCode = new ArrayList<Integer>();
		String delimiters = "\\s+|\\s*\\[[0-9]*\\]\\W*\\s*|,\\s*|\\.\\s*";
		String words[] = s.split(delimiters);
		int pos = -1;
		for(String w : words) {
			if(!w.isEmpty()) {
				wordsArrayList.add(w);
				wordsHashCode.add(w.hashCode());
				pos = s.indexOf(w, pos + 1);
				wordsOffsetArrayList.add(offset + pos);
			}
		}
	}
	
	@Override
	public int getClassHash() {
		if(classHash != -1) return classHash;
		int result = 0;
		int hashes[] = new int[wordsArrayList.size()];
		for(int i = 0; i < wordsArrayList.size(); i++) {
			hashes[i] = wordsArrayList.get(i).hashCode();
		}
		result = Arrays.hashCode(hashes);
		classHash = result;
		return result;
	}
}
