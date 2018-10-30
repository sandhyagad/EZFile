package General.EZController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import General.EZData.UserData;
import General.EZModel.UserModel;


/*Sandhya*/

@MultipartConfig

@WebServlet("/user")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside do get starting line");
   	 String filename = request.getParameter("filename");
   	 String emailid = request.getParameter("emailid"); 
   	System.out.println(filename);
   	System.out.println(emailid);
   	 File file = new UserData().downloadObject(filename,emailid);

   	 if(!file.exists()){
			throw new ServletException("File doesn't exists on server.");
		}
   	 System.out.println("File Path"+file.getAbsolutePath());
   
   	// ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		
		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = new byte[4096];
		int read=0;
		while((read = fis.read(bufferData)) > 0){
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("Download successfullys");
    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
    if(request.getParameter("name").equals("signup")) {
    	System.out.println("line 67 in UserController java");
    	String fname=request.getParameter("fname");
    	String lname=request.getParameter("lname");
		String emailid=request.getParameter("emailid");
		String pwd=request.getParameter("pwd");
		if(new UserData().storeUserDetails(fname,lname,emailid,pwd)) {
	
			request.setAttribute("name", "success");
			request.getRequestDispatcher("Login.jsp").forward(request, response);							
		}
		else {
			request.setAttribute("name", "failed");
			request.getRequestDispatcher("UserRegistration.jsp").forward(request, response);
		}	
    }
    else if(request.getParameter("name").equals("login")) {
		String emailid=request.getParameter("emailid");
		String pwd=request.getParameter("pwd");
		if(new UserData().validateUserDetails(emailid,pwd)) {	
			String username = new UserData().retrieveName(emailid);
			ArrayList<UserModel> files = new UserData().retrieveFiles(emailid);
			if(files != null) {		
				request.setAttribute("emailid",emailid);
			request.setAttribute("files",files);
			request.setAttribute("username",username);
			request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
		    }
			else
			{
				request.setAttribute("emailid",emailid);
				request.setAttribute("username",username);
				request.setAttribute("files", null);
				request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
			 }
		}
		else {
			request.setAttribute("name", "Unsuccessfull Login");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}   		
    }

			else
			{
				request.setAttribute("emailid",emailid);
				request.setAttribute("username",username);
				request.setAttribute("files", null);
				request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
			 }
		}
		else {
			request.setAttribute("name", "Unsuccessfull Login");
			request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
		}   		
    }
    /*else if(request.getParameter("name").equals("googlelogin")) {
  		String emailid=request.getParameter("emailid");
  		String uname=request.getParameter("uname");
  		System.out.println("email = " + emailid);
  		System.out.println("username = "+uname);
  			ArrayList<UserModel> files = new UserData().retrieveFiles(emailid);
  			if(files != null) {		
  				request.setAttribute("emailid",emailid);
  			request.getRequestDispatcher("Usermenu.jsp").forward(request, response);
  		    }*/
  			else
  			{
  				request.setAttribute("emailid",emailid);
  				request.setAttribute("username",googleusername);
  				request.setAttribute("files", null);
  				request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
  			 }  		
      }
    else if(request.getParameter("name").equals("upload")) {
    	String emailid=request.getParameter("emailid");
		String description=request.getParameter("desc");
		Part filePart = request.getPart("fileName");
		InputStream inputStream = null;
		if (filePart != null) {
			 String filename = getFileName(filePart);			
             Long filesize = filePart.getSize();
             inputStream = filePart.getInputStream(); 
             if(new UserData().uploadObject(filename,filePart,inputStream,emailid)) {
		     if(new UserData().uploadFile(filename,description,filesize,emailid)) {	
			 String username = new UserData().retrieveName(emailid);
			 ArrayList<UserModel> files = new UserData().retrieveFiles(emailid);	
			 if(files != null) {				
			    request.setAttribute("files",files);
			    request.setAttribute("username",username);
			    request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
		     }
			 else
			 {
			    request.setAttribute("username",username);
			    request.setAttribute("files", null);
				request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
			 }
		     }
		     		
       }   			
	}
    else if(request.getParameter("name").equals("download")) {
    	System.out.println("delete");
    	 String filename = request.getParameter("fileName");
    	 String emailid = request.getParameter("emailid"); 
    	 File file = new UserData().downloadObject(filename,emailid);
    	 PrintWriter out = response.getWriter();
    	 if(!file.exists()){
 			throw new ServletException("Server doesn't have the file.");
 		}
    	 System.out.println("Path::"+file.getAbsolutePath());
    
 		FileInputStream fis = new FileInputStream(file);

 		int read=0;
 		while((read = fis.read()) != 0){
 			
 			out.write(read);
 			
 	 		out.close();
 	 		fis.close();
 		}

 		System.out.println("File downloaded at client successfully");

    }
    else if(request.getParameter("name").equals("delete") && filename != null ) {
    	 String emailid = request.getParameter("emailid"); 
    	 String filename = request.getParameter("filename");

    	 if(new UserData().deleteObject(filename,emailid)) {
    	 else if(new UserData().deleteFile(filename,emailid)) {
    		 String username = new UserData().retrieveName(emailid);
 			ArrayList<UserModel> files = new UserData().retrieveFiles(emailid);
 			System.out.println(files);
 			if(files != null) {		
 				System.out.println("yes");
 				request.setAttribute("emailid",emailid);
 			request.setAttribute("files",files);
 			request.setAttribute("username",username);
 			request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
 		     }
 			else
 			{
 				request.setAttribute("emailid",emailid);
 				request.setAttribute("files", null);
 				request.getRequestDispatcher("UserMenu.jsp").forward(request, response);
 			 }
    	 }
    	 }
    }

}
	public String getFileName(final Part part) {

	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
}
