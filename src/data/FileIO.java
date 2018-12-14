package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class FileIO {
	private String filename;
	private ArrayList<String> rawTextLines = new ArrayList<String>();
	private File file;
	public FileIO(String filename) {
		this.filename = filename;
		this.file = new File(this.filename);
	}
	
	public FileIO(File file) {
		this.file = file;
		this.filename = this.file.getName();
	}
	
	public ArrayList<String> loadFileText() {
		try {
			if (file.isFile() && file.exists())
	        {
				//System.out.println(this.filename);
				if(this.filename.toLowerCase().endsWith("doc")) {
		            FileInputStream fis = new FileInputStream(this.file.getAbsolutePath());
		            HWPFDocument document = new HWPFDocument(fis);
		            WordExtractor extractor = new WordExtractor(document);
		            String[] fileData = extractor.getParagraphText();
		            for (int i = 0; i < fileData.length; i++)
		            {
		                if (fileData[i] != null)
		                	rawTextLines.add(fileData[i]);
		            }
		            document.close();
		            fis.close();
				} else if(this.filename.toLowerCase().endsWith("docx")) { 
					FileInputStream fis = new FileInputStream(file.getAbsolutePath());
	                XWPFDocument document = new XWPFDocument(fis);
	                List<XWPFParagraph> paragraphs = document.getParagraphs();

	                for (XWPFParagraph para : paragraphs) {
	                	rawTextLines.add(para.getText());
	                }
	                document.close();
	                fis.close();
				} else {
		            InputStreamReader inputStream = new InputStreamReader(
		                    new FileInputStream(file));
		            BufferedReader bufferedReader = new BufferedReader(inputStream);
		            String lineText = null;
	
		            while ((lineText = bufferedReader.readLine()) != null)
		            {
		            	if(lineText != "")
		            		rawTextLines.add(lineText);
		            }
		            bufferedReader.close();
		            inputStream.close();
				}
	        }
		}catch(Exception e){
			System.out.println("Input file error.");
			e.printStackTrace();
		}
		return this.rawTextLines;
	}
	
	public void writeToFile(String text) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename));
		    writer.write(text);
		    writer.close();
		}catch(Exception e){
			System.out.println("Output file error.");
			e.printStackTrace();
		}
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public boolean isEmptyFile() {
		try{
			File file = new File(this.filename);
			if (file.isFile() && file.exists())
	        {
	            return false;
	        } else {
	        	return true;
	        }
		} catch(Exception e){
			System.out.println("Input file error.");
			e.printStackTrace();
		}
		return rawTextLines.isEmpty();
	}
}

