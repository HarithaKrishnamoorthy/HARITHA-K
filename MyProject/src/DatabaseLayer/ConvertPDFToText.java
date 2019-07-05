package DatabaseLayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ConvertPDFToText {
	public void ConvertToText(String file) throws Exception{
		PDDocument pd=null;
		 BufferedWriter wr=null;
		 try {
		         File input = new File(file);  // The PDF file from where you would like to extract
		         File output = new File("D:/SampleText.txt"); // The text file where you are going to store the extracted data
		         pd = PDDocument.load(input);
		         PDFTextStripper stripper = new PDFTextStripper();
		         stripper.setStartPage(1); 
		         stripper.setEndPage(1); 
		         wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
		         stripper.writeText(pd, wr);		         
		 } 
		 catch (Exception e){
			 System.out.println("No Attachment found in the mail");
		         //e.printStackTrace();
		        }
		 
		 finally{
			 if (pd != null) {
	             pd.close();
	         }
	         if(wr !=null){
	        wr.close();
	        System.out.println("PDF Converted to Text file");
	         }
		 }
}
}
