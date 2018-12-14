package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import model.Article;
import model.HighlightArea;
import model.Paragraph;
import model.Sentence;

public class StaticMethods {
	public static HighlightArea[] getComparisonHighlightResult(Article a1, Article a2) {
		HighlightArea result[] = new HighlightArea[2];
		result[0] = new HighlightArea();
		result[1] = new HighlightArea();
		boolean m1[] = new boolean[a1.paragraphs.size()];
		boolean m2[] = new boolean[a2.paragraphs.size()];
		for(int i = 0; i < a1.paragraphs.size(); i++)
			for(int j = 0; j < a2.paragraphs.size(); j++) {
				Paragraph p1 = a1.paragraphs.get(i);
				Paragraph p2 = a2.paragraphs.get(j);
				if(p1 == null || p2 == null || m1[i] || m2[j]) continue;
				if(p1.getRawHash() == p2.getRawHash() 
					|| p1.getClassHash() == p2.getClassHash()) {
					m1[i] = true; m2[j] = true;
					result[0].start.add(p1.getOffset());
					result[0].end.add(p1.getOffset() + p1.getLength());
					result[1].start.add(p2.getOffset());
					result[1].end.add(p2.getOffset() + p2.getLength());
				}
			}
		for(int i = 0; i < a1.paragraphs.size(); i++)
			if(!m1[i])
				for(int j = 0; j < a2.paragraphs.size(); j++)
					if(!m2[j]) {
						Paragraph p1 = a1.paragraphs.get(i);
						Paragraph p2 = a2.paragraphs.get(j);
						if(p1 == null || p2 == null) continue;
						for(int k = 0; k < p1.sentences.size(); k++)
							for(int l = 0; l < p2.sentences.size(); l++) {
								Sentence s1 = p1.sentences.get(k);
								Sentence s2 = p2.sentences.get(l);
								if(s1 == null || s2 == null) continue;
								if(s1.getRawHash() == s2.getRawHash()
									|| s1.getClassHash() == s2.getClassHash()) {
									result[0].start.add(s1.getOffset());
									result[0].end.add(s1.getOffset() + s1.getLength() + 1);
									result[1].start.add(s2.getOffset());
									result[1].end.add(s2.getOffset() + s2.getLength() + 1);
								} else {
									double cosSimilarity = cosineSimilarity(s1.getRawData(), s2.getRawData());
									double degrees = Math.toDegrees(Math.acos(cosSimilarity));				
									if(degrees < GlobalValue.SIMILARITY_THRESHOLD) {
										System.out.println(degrees);
										result[0].start1.add(s1.getOffset());
										result[0].end1.add(s1.getOffset() + s1.getLength() + 1);
										result[1].start1.add(s2.getOffset());
										result[1].end1.add(s2.getOffset() + s2.getLength() + 1);
									}
								}
							}
					}
		return result;
	}
	
    /**
    * @param terms values to analyze
    * @return a map containing unique 
    * terms and their frequency
    */
   public static Map<String, Integer> getTermFrequencyMap(String[] terms) {
       Map<String, Integer> termFrequencyMap = new HashMap<>();
       for (String term : terms) {
           Integer n = termFrequencyMap.get(term);
           n = (n == null) ? 1 : ++n;
           termFrequencyMap.put(term, n);
       }
       return termFrequencyMap;
   }

   /**
    * @param text1 
    * @param text2 
    * @return cosine similarity of text1 and text2
    */
   public static double cosineSimilarity(String text1, String text2) {
       //Get vectors
       Map<String, Integer> a = getTermFrequencyMap(text1.split("\\W+"));
       Map<String, Integer> b = getTermFrequencyMap(text2.split("\\W+"));

       //Get unique words from both sequences
       HashSet<String> intersection = new HashSet<>(a.keySet());
       intersection.retainAll(b.keySet());

       double dotProduct = 0, magnitudeA = 0, magnitudeB = 0;

       //Calculate dot product
       for (String item : intersection) {
           dotProduct += a.get(item) * b.get(item);
       }

       //Calculate magnitude a
       for (String k : a.keySet()) {
           magnitudeA += Math.pow(a.get(k), 2);
       }

       //Calculate magnitude b
       for (String k : b.keySet()) {
           magnitudeB += Math.pow(b.get(k), 2);
       }
       
       //return cosine similarity
       return dotProduct / Math.sqrt(magnitudeA * magnitudeB);
   }
}
