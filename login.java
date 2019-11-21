import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.Cookie; 

public class login extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String un = req.getParameter("username");
		String ps = req.getParameter("password");
        
    try
	{
		int flag=0; 
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","system");
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select userid,password from users");
		String fname = rs.getString("fname");
		while(rs.next())
		{
			String id = rs.getString("userid");
			String pw = rs.getString("password");
			String fn = rs.getString("fname");

			if(un.equals(id))
			{
				flag = 1;
				if(pw.equals(ps))
				{
					if(flag==2)
					{
						out.println("<center><font color='red' size='12px'>Welcome '"+fn+"'</font></center>");
						RequestDispatcher rd = req.getRequestDispatcher("userbooking.html");
						Cookie ck = new Cookie("id",id);
						res.addCookie(ck);
						rd.include(req,res);
					}
					break;
				}
			}
		}

	    if(flag==1)
		{
			out.println("<center><font color='red' size='12px'>Incorret Password !</font></center>");
			RequestDispatcher rd = req.getRequestDispatcher("login.html");
			rd.include(req,res);
		}
		else
		{
			out.println("<center><font color='red' size='9px'>In Order to Login You Need to Signup First</font></center>");
			RequestDispatcher rd = req.getRequestDispatcher("login.html");
			rd.include(req,res);
		}
		rs.close();
		s.close();
		c.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	out.close();
	}
}