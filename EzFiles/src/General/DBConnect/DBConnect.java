package General.DBConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.server.LoginServlet;

public class DBConnect {
   
	private static DBConstring conString;
	public PreparedStatement prepstmt,prepstmt1;
	public ResultSet res;
	boolean flag isLoggedin;

public static Connection getMySqlConnection() {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conString = DriverManager.getConnection("jdbc:mysql://RDS end point:3306/EzFile","sandhyagadgoli","s***js**********u23***45");	
		return conString;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
	
	
	statementObject=connectionObject.prepareStatement(prepstmt);
    statementObject.setString(1, uname);
    statementObject.setString(2, pass);
    resultSetObject = statementObject.executeQuery();
    
    if(resultSetObject.next())
    	isLoggedin=true; 
    else
    	isLoggedin=false;
    
    if(isLoggedin)
    {
        HttpSession UserSession = request.getSession(true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserMenu.jsp");
       dispatcher.forward(request, response);
    
}

}