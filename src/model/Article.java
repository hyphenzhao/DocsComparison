package model;

import java.util.ArrayList;

public class Article {
	public ArrayList<String> rawParas;
	public ArrayList<Paragraph> paragraphs;
	
	public Article(ArrayList<String> rawData) {
		rawParas = rawData;
		int offset = 0;
		paragraphs = new ArrayList<Paragraph>();
		for(String rawPara : rawParas) {
			paragraphs.add(new Paragraph(rawPara, offset));
			offset += rawPara.length() + 1;
		}
	}
	
	public String getWholeArticle() {
		String result = "";
		for(String l : this.rawParas) {
			result += l + "\n";
		}
		return result;
	}
}
