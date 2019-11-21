import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class signup extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		int j= 0;
		res.setContentType("text/html");
		PrintWriter pr = res.getWriter();

		String un = req.getParameter("username");
		String fn = req.getParameter("firstname");
		String ln = req.getParameter("lastname");
		String pas = req.getParameter("password");
		String cps = req.getParameter("confirmpassword");
		String ge = req.getParameter("gender");
		String ag = req.getParameter("age");
		String em = req.getParameter("email");
		String pn = req.getParameter("phonenumber");
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","system");
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select userid from users");
		while(rs.next())
		{
			String userid = rs.getString("userid");
			if(userid.equals(un))
			{
				pr.println("<center>Entered Username Already Exist <b>:)</b> Please Login OR Signup as a New User</center>");
				RequestDispatcher rd = req.getRequestDispatcher("signup.html");
				rd.include(req,res);
				j=1;
				break;
			}
		}
		if(!(pas.equals(cps)))
			{
				pr.println("<center>Sorry! Password & ConfirmPassword did not match</center>");
				RequestDispatcher rd = req.getRequestDispatcher("signup.html");
				rd.include(req,res);
				j=2;
			}
		if(j==0)
			{
				PreparedStatement ps = c.prepareStatement("insert into users values(?,?,?,?,?,?,?,?)");
				ps.setString(1,un);
				ps.setString(2,pas);
				ps.setString(3,fn);
				ps.setString(4,ln);
				ps.setString(5,ge);
				ps.setString(6,ag);
				ps.setString(7,em);
				ps.setString(8,pn);
				int i = ps.executeUpdate();
				ps.close();

				if(i>0)
				{
					pr.println("<center>Registration Successful!</center>");
					RequestDispatcher rd = req.getRequestDispatcher("login.html");
					rd.include(req,res);
				}
			}
			rs.close();
			s.close();
			c.close();
	}
	catch(Exception e)
		{
			System.out.println(e);
		}
		pr.close();	
	}
}