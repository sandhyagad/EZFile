<%@page import="com.db.FileDetailsFromDatabase"%>
<%@page import="com.pojo.DatabaseFilePojo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<title>User Details Menu</title>
<body background="http://genchi.info/images/ultra-wide-hd-wallpaper-31.png">
	 <header><h1>Hello &nbsp;&nbsp;<%=session.getAttribute("username")%> Welcome to EzFile Application</h1>
            <%
            	String s = session.getAttribute("username").toString();
                            session.setAttribute("username", s);
            %>

            
        </header>
	
	<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
</style>

	<table width="59%" border="1">
		<tr>
			<td>User</td>
			<td>FileName</td>
			<td>File Description</td>
			<td>Date Created</td>
			<td>Download</td>
			<td>Delete</td>
			<td>Update</td>
		</tr>
		<%
			String username = session.getAttribute("username").toString();
			System.out.println("<display list of flkes  " + username);
			FileDetailsFromDatabase info = new FileDetailsFromDatabase();
			pojo = info.fetchData(username);

			for (int i = 0; i < pojo.size(); i++) {
				DatabaseFilePojo object = new DatabaseFilePojo();
				object = pojo.get(i);
				String fn = object.getFileName();
				request.setAttribute("filename", fn);
		%>
		<tr>
			<td><%=object.getUserName()%></td>

			<td><%=object.getFileName()%></td>

			<td><%=object.getFileDescription()%></td>

			<td><%=object.getFileUploadTime()%></td>
			<td>
				<form action="UserData" method="post">
					<input type="submit" value="Download"> <input
						type="hidden" name="myObject" value=<%=object.getFileName()%> />
				</form>
			</td>
			<td>
				<form action="UserData" method="post">
					<input type="submit" value="Delete"> <input type="hidden"
						name="myObject" value=<%=object.getFileName()%> />
				</form>
			</td>
			<td>
				<form action="UserData" method="post"
					enctype="multipart/form-data">
					<input type="file" id="file" name="file" multiple> <input
						type="submit" value="Submit" id="btnSubmit" />
				</form>
			</td>
		</tr>

		<%
			}
		%>
	</table>


	<form id="myform" action="UserData"
		onsubmit="return myFunction();" method="post"
		enctype="multipart/form-data">

		<label for="file">Select file to upload</label> <input type="file"
			id="file" name="file" multiple="true"> <input type="submit"
			value="Submit" id="btnSubmit" /></br></br></br>
			<button><a href="Login.jsp">Logout</a></button>
	</form>
	
	<script>
		function myFunction() {

			var fileUpload = document.getElementById(file);
			var flag = false;

			if (typeof (fileUpload.files) !== "undefined") {
				System.out.println("Inside UserData.jsp line99");
				var size = fileUpload.files[i].size / 1024;
				var maxsize = 10 * 1024;

				if (size > maxsize) {
					alert("Upload smaller data file around 5MB");
					document.getElementById("myform").reset();
					flag = false;

				} else {
					flag = true;

				}

				// }
			} 
			return flag;
		}
	</script>


</body>

</html>
