

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<title>EzFile Registration</title>
<h1> Please enter the details to become member of EzFile</h1>
<body background="http://genchi.info/images/ultra-wide-hd-wallpaper-31.png">        
        
<!--
    <form>
      <input type="text" placeholder="username"/>
      <input type="password" placeholder="password" /
    </form-->
      <center><form class="login-form" action="RegistrationServlet" method="post">
      Firstname:<input type="text" placeholder="Enter your Firstname"  name="firstname"  required/></br></br>
      Lastname:<input type="text" placeholder="Enter your Lastname"  name="lastname"  required/></br></br> 
      Username:<input type="text" placeholder="Username"  name="username"  required/></br></br>
      Password:<input type="password" placeholder="Password" name="password"  required/></br></br>
      <tr>
      </br><button>Register</button>      
      
    </form></center>
  </div>
</div>
   <script type="text/javascript">
    function validateForm()
    {
        
        if (username==null || username=="",password==null ||password =="")
        {
            alert("All fields are mendatory");
            return false;
        }
    }
    
    </script>
    </body>
    </html>
    
