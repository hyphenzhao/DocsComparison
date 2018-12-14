package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Paragraph extends PaperComponent {
	public ArrayList<String> sensArrayList;
	public ArrayList<Sentence> sentences;
	private int classHash;
	
	public Paragraph(String s, int offset) {
		super(s, offset);
		classHash = -1;
		sensArrayList = new ArrayList<String>();
		sentences = new ArrayList<Sentence>();
		String delimiters = "\\.\\s*";
		String sens[] = s.split(delimiters);
		int pos = -1;
		for(String sen : sens) {
			if(!sen.isEmpty()) {
				sensArrayList.add(sen);
				pos = s.indexOf(sen, pos + 1);
				sentences.add(new Sentence(sen, offset + pos));
			}
		}
	}
	
	@Override
	public int getClassHash() {
		if(classHash != -1) return classHash;
		int result = 0;
		int hashes[] = new int[sentences.size()];
		for(int i = 0; i < sentences.size(); i++) {
			hashes[i] = sentences.get(i).getClassHash();
		}
		result = Arrays.hashCode(hashes);
		classHash = result;
		return result;
	}

}
