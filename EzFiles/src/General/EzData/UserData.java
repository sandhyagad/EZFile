package com.cloudstore.dao;
package General.EZData;
package General.EZModel;
package General.EZController;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

//import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;



public class UserData {
	GetConnection gc= new GetConnection();
	UserModel u = null;
	String bucketname = "YOUR BUCKET NAME HERE";
	FileOutputStream fos = null;
	final String SUFFIX = "/";	
	public boolean storeUserDetails(String Email, String Password, String FName,String LName)
	{
		String sql="insert into UserLogin(Email,Password,FName,LName) values (?,?,?,?)";
		GetConnection gc= new GetConnection();
		try {
			gc.ps= GetConnection.getMySqlConnection().prepareStatement(sql);
			gc.ps.setString(1,Email);
			gc.ps.setString(2, Password);
			gc.ps.setString(3, FName);
			gc.ps.setString(4, LName);
			int i= gc.ps.executeUpdate();
			if(i !=0 ) {
			return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}	
	// create a client connection based on credentials	

	public boolean validateUserDetails(String Email, String Password)
	{
		String sql="select * from UserLogin where Email=? and Password=?";
		GetConnection gc= new GetConnection();
		try {
			gc.ps= GetConnection.getMySqlConnection().prepareStatement(sql);
			gc.ps.setString(1, Email);
			gc.ps.setString(2, Password);
			gc.result= gc.ps.executeQuery();			
			return gc.rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public String retrieveName(String Email) {
		String sql = "select FName, Lname from UserLogin where Email=?";
		String username = null;
		try {
			gc.ps= GetConnection.getMySqlConnection().prepareStatement(sql);
			gc.ps.setString(1, Email);
			gc.rs= gc.ps.executeQuery();
			while(gc.rs.next())
			{
            username = gc.rs.getString("FName") + gc.rs.getString("LName");
            return username;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return username;
		
	}
	
	public ArrayList<UserModel> retrieveFiles(String Email)
	{
		ArrayList<UserModel> a = new ArrayList<UserModel>();
		String sql="select FileName, Description, FileSize, FileUploadtime, FileUpdadtedtime, Email from UserFileDetails where Email=?	";		
		try {
			gc.ps= GetConnection.getMySqlConnection().prepareStatement(sql);
			gc.ps.setString(1, Email);
			gc.rs= gc.ps.executeQuery();
			int count = 0;
			
			while(gc.rs.next())
			{		
				u = new UserModel();
				u.setFileName(gc.rs.getString("FileName"));
				u.setDescription(gc.rs.getString("Description"));
				u.setFileSize(gc.rs.getString("FileSize"));
				u.setCreatedTime(gc.rs.getDate("FileUploadtime"));
				u.setUpdatedTime(gc.rs.getDate("FileUpdadtedtime"));	
				u.setEmailId(gc.rs.getString("Email"));
				a.add(u);				
				count = count+1;
			}
			if (count != 0)
			{
				return a;
			}
			else
			{
				return null;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	public boolean fileUpload(String FileName,String Description,Long FileSize,String Email)
	{
		try {
		String sql="update FileDetails SET Description=?,FileSize=? where FileName=? && Email=?";		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		GetConnection gc= new GetConnection();
		System.out.println("inUSerData java line 126");
			gc.ps.setString(1,Description);
			gc.ps.setLong(2, FileSize);
			gc.ps.setString(3,FileName);
			gc.ps.setString(4, Email);
			int i= gc.ps.executeUpdate();
			if(i == 0 ) {
				String sql1="insert into UserFileDetails(FileName,Description,FileSize,FileUploadtime,Email) values (?,?,?,?,?)";
				GetConnection gc1= new GetConnection();
				try {
			 gc1.ps1= GetConnection.getMySqlConnection().prepareStatement(sql1);
			 gc1.ps1.setString(1, FileName);
			 gc1.ps1.setString(2, Description);
			 gc1.ps1.setLong(3, FileSize);
			 gc1.ps1.setTimestamp(4, timestamp);
			 gc1.ps1.setString(5, Email);
			 int i1= gc1.ps1.executeUpdate();
			 if(i1 !=0 ) {																																																																																																																																																																																			
			 return true;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
			}
			else {
				 return true;																																																																																																																																																																																																																																																														
			}
		}
			catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
		
	}
	public boolean uploadObject(String fileName, Part part, InputStream inputStream, String emailid) {	
		System.out.println("before screw up");
		
		
		/*
	   // Sandhya starts
		
		 String clientRegion = "us-west-1";
	     String bucketName = "cloudhomework2";
	     String stringObjKeyName = "testobject";
	     String fileObjKeyName = "testfileobject";
	     String UploadFile = "C:\\Users\\bakala\\eclipse-workspace\\UploadS3\\Upload\\Xample.txt";

	     try {
	    	 BasicAWSCredentials awsCreds = new BasicAWSCredentials("", "");
	         AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	               .withRegion(clientRegion)
	                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
	                 .build();
	         File file = new File(UploadFile);
	         // Upload a text string as a new object.
	         s3Client.putObject(bucketName, stringObjKeyName, file);
	         System.out.println("AFter s3Client");
	         // Upload a file as a new object with ContentType and title specified.
	         PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
	         ObjectMetadata metadata = new ObjectMetadata();
	         metadata.setContentType("plain/text");
	         metadata.addUserMetadata("x-amz-meta-title", "someTitle");
	         request.setMetadata(metadata);
	         s3Client.putObject(request);
	     }
	     catch(AmazonServiceException e) {
	
	         e.printStackTrace();
	     }
	     catch(SdkClientException e) {

	         e.printStackTrace();
	     }
	 
     */
	//Sandhya ends
		
         
         final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient(); 
		System.out.println("after screw up");		
		try {
			System.out.println(emailid);
			ObjectMetadata metadata = new ObjectMetadata();
	
	
		metadata.setContentLength(part.getSize());
		metadata.setContentLength(Long.valueOf(part.getInputStream().available()));
		String ext = FilenameUtils.getExtension(part.getName());
		String keyName = fileName + '.' + ext;
		String keyName = emailid + SUFFIX + fileName;		
		
		s3Client.putObject(new PutObjectRequest(bucketname , keyName, part.getInputStream(),metadata));
		return true;
		}
		
		catch(AmazonServiceException e) {
           e.printStackTrace();
       } catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	public File downloadObject(String filename,String emailid) {
		final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
		try {
			String keyName = emailid + SUFFIX + filename;
		    S3Object object = s3Client.getObject(bucketname, keyName);
		    File file = new File(filename);
		    FileUtils.copyInputStreamToFile(object.getObjectContent(), file);
		    return file;
		
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		
		} catch (IOException e) {
		    System.err.println(e.getMessage());
		    System.exit(1);
		}
		return null;
	}
	public boolean deleteFile(String filename,String emailid) {
		String sql = "delete from Files where filename=? && emailid=?";
		System.out.println("delete");
		GetConnection gc= new GetConnection();
		try {
			gc.ps= GetConnection.getMySqlConnection().prepareStatement(sql);prepStmtDel
			System.out.println("delete");
			gc.ps.setString(1, filename);
			gc.ps.setString(2, emailid);
						
			int i= gc.ps.executeUpdate();
			if(i !=0 ) {
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return false;
	}
	
	
}
