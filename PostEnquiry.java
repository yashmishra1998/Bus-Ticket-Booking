import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class PostEnquiry extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		String dep = req.getParameter("departure");
		String des = req.getParameter("destination");
		String bt = req.getParameter("bustype");
		double fare;
		int dis;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","system");
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT distance FROM fare WHERE departure='"+dep+"' and destination='"+des+"'");
			rs.next();
			dis =(rs.getInt("distance"));
			if(bt.equals("super deluxe"))
				{
					fare=dis*3.5;
					pw.println("<center><font color='white' size='25px'>Fare: "+fare+"</font></center>");
					RequestDispatcher rd = req.getRequestDispatcher("PostEnquiry.html");
					rd.include(req,res);
				}	
			if(bt.equals("deluxe"))
				{
					fare=dis*2.75;
					pw.println("<center><font color='white' size='25px'>Fare: "+fare+"</font></center>");
					RequestDispatcher rd = req.getRequestDispatcher("PostEnquiry.html");
					rd.include(req,res);
				}
			if(bt.equals("basic"))
				{
					fare=dis*2;
					pw.println("<center><font color='white' size='25px'>Fare: "+fare+"</font></center>");
					RequestDispatcher rd = req.getRequestDispatcher("PostEnquiry.html");
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
	} 
}