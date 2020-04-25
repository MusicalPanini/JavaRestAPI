package test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Path("/Person")
public class Person_Rest {

	String url = "jdbc:sqlserver://localhost;";
	String username = "Aidan";
	String password = "pass1";

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}")
	public String GetByID(@PathParam("id") int id) 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connection to Database successful");
			
			Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("Select * FROM Person WHERE PersonID=" + id);
            String htmlReturn = "";
            while (rs.next()) 
            {
                htmlReturn += "<h1>" + rs.getString(1) + " -- " + rs.getString(2) + " " + rs.getString(3) + " -- " + rs.getString(4) + "</h1>";
            }
            
            if (htmlReturn == "")
            	return "A person with ID: " + id + " does not exist";
            else
            	return htmlReturn;
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			
			return "<h3>Something broke during the GET request, check the logs</h3>";
		}
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/All")
	public String GetAll() 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			Connection connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connection to Database successful");
			
			Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("Select * FROM Person");
            String htmlReturn = "";
            while (rs.next()) {
                htmlReturn += "<h1>" + rs.getString(1) + " -- " + rs.getString(2) + " " + rs.getString(3) + " -- " + rs.getString(4) + "</h1>";
             }
            
            return htmlReturn;
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			
			return "<h3>Something broke during the during the GET request, check the logs</h3>";
		}
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String GetHelp() 
	{
		return "<h2>For all Person's add /All</h2>"
				+ "<h2>For a single person add /(id)";
	}

	@POST
	public String Post(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, 
			@QueryParam("DoB_Y") int DoB_Y, @QueryParam("DoB_M") int DoB_M, @QueryParam("DoB_D") int DoB_D) 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connection to Database successful");
			
			Statement st = connection.createStatement();
			st.execute(	"INSERT INTO Person (FirstName, LastName, DOB)"
            		  		+ "VALUES ('" + firstName + "', '" + lastName + "', '" + DoB_Y + "-" + DoB_M + "-" + DoB_D + "')" );
            
            return "Successfuly added " + firstName + " " + lastName;
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			
			return "<h3>Something broke during the POST request, check the logs</h3>";
		}
	}

	@DELETE
	@Path("/{id}")
	public String DeleteByID(@PathParam("id") int id) 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connection to Database successful");
			
			Statement st = connection.createStatement();

            st.execute("DELETE FROM Person WHERE PersonID=" + id);
            
            return "Updated Person from ID: " + id;
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			
			return "<h3>Something broke during the DELETE request, check the logs</h3>";
		}
	}
	
	@PUT
	@Path("/{id}")
	public String PutByID(@PathParam("id") int id, 
			@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, 
			@QueryParam("DoB_Y") int DoB_Y, @QueryParam("DoB_M") int DoB_M, @QueryParam("DoB_D") int DoB_D) 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connection to Database successful");
			
			Statement st = connection.createStatement();

            st.execute("UPDATE Person "
            		+ "SET FirstName = '" + firstName + "', LastName = '" + lastName + "', DoB = '" + DoB_Y + "-" + DoB_M + "-" + DoB_D + "'"
            		+ "WHERE PersonID=" + id);
            
            return "Updated Person from ID: " + id;
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			
			return "<h3>Something broke during the PUT request, check the logs</h3>";
		}
	}
}
