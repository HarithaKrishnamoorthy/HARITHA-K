package FrontLayer;

import DatabaseLayer.CreateDatabase;
import DatabaseLayer.ConvertPDFToText;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import BusinessLayer.RecieveMail;
import BusinessLayer.SendMail;

public class Test {
	public static Properties loadPropertiesFile() throws Exception {

		Properties prop = new Properties();
		InputStream in = new FileInputStream("C:\\Users\\hkrishnamoothy\\workspace\\MyProject\\resource\\access.properties");
		prop.load(in);
		in.close();
		return prop;
	}
	public static void main(String[] args) throws Exception{
		//To Receive Mail
		Properties prop = loadPropertiesFile();
		CreateDatabase invoice= new CreateDatabase();
		String response;
		String get;
		do{
		
		 String host = prop.getProperty("host");
	     String port = prop.getProperty("port");
	     String userName = prop.getProperty("username");
	     String password = prop.getProperty("password");
	     String saveDirectory = "D:/";
	     
	     RecieveMail receiver = new RecieveMail();
	     receiver.setSaveDirectory(saveDirectory);
	     receiver.downloadEmailAttachments(host, port, userName, password);
	      
	     // To Convert PDF to Text File
	     ConvertPDFToText text=new ConvertPDFToText();
	     text.ConvertToText(receiver.filesaved);
	     
	     //To Create Database with invoice details
	     invoice.Invoice();
	     System.out.println("Do you like to recieve next invoice attachment: yes/no");
	     Scanner sc= new Scanner(System.in);
	     response=sc.nextLine();
		}while(response.equals("yes"));
 	
	 	Scanner sc=new Scanner(System.in);
	 	do{
	 	System.out.println("Are you willing to update any invoice: yes/no");
	 	String answer=sc.nextLine(); 
	 	
	 	
	 	if(answer.equals("yes"))
	 	{
	 		System.out.println("Enter the Invoice No your willing to Approve");
		 	String invoiceNo = sc.nextLine();
	 		boolean check=invoice.UpdateInvoice(invoiceNo);
	 		//To Send Approve Mail
	 		if(check){
		 	Thread.sleep(1000);
	 		String Senderhost = prop.getProperty("Senderhost");
	        String Senderport = prop.getProperty("Senderport");
	        String mailFrom = prop.getProperty("username");
	        String Senderpassword = prop.getProperty("password");
	 
	        // outgoing message information
	        String mailTo = "haritha2697@gmail.com";
	        String subject = "Invoice Approval";
	        String message = "Invoice No.: " + invoiceNo + " is Approved.";
	        SendMail mailer = new SendMail();
	        try {
	        	
	            mailer.sendPlainTextEmail(Senderhost, Senderport, mailFrom, Senderpassword, mailTo,subject, message);
	            System.out.println("Email sent.");
	        } catch (Exception ex) {
	            System.out.println("Failed to sent email.");
	            ex.printStackTrace();
	        }
	        finally{
	        	if(sc !=null)
	        	{
	        		sc.close();
	        	}
	        }
	 	  }
	 	}
	 	else if(answer.equals("no")){
	 		System.out.println("Invoice is not Approved");
	 	}
	 	System.out.println("Are you willing to proceed with further invoice Approval: yes/no");
	 	get=sc.nextLine();
	}while(get.equals ("yes"));
	 	
	}
}
