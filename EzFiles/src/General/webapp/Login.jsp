<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html><body background="https://cdn.hipwallpaper.com/m/82/26/JMjEgV.jpg">
 <h2>Welcome to the EzFile - Application!</h2>
  <h3>This simple application demonstrates how easy it is perform File Activities like Upload, Edit, and Delete a file with Register, Login, and securely authenticate
                users on our website .</h3>


  <div class="form">
          <center>
          <form action="username?password=Login" method="post"/> <tb>
           Username: <input type="text" placeholder="username"  name="username"  required/> <br/> <br/>
           Password: <input type="password" placeholder="password" name="password" required/> <br/> <br/>
          <br/><button>Login</button>
                
      <p class="message">Not a EzFile user? <a href="UserRegistration.jsp">Go sign-up now !!</a></p>
      </center>
    </form>
  </div>

   <script type="text/javascript">
   function validateLogin() {
	    var x = document.forms["username"]["password"].value;
	    if (x == "") {
	        alert("Username OR Password can't be NULL");
	        return false;
	    }
	}
    
    </script>
    </body>
    </html>