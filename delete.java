import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class delete extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String tid = req.getParameter("ticketid");

    try
	{ 
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","system");
		Statement s = c.createStatement();
		
			{
				out.println("<br><br><br><center><font color='green' size='10px'>Ticket Cancellation Successful.<br><br>Money will be refunded in your bank account within 24 Hours...</font></center>");
				Statement s1 = c.createStatement();
				HttpSession session = req.getSession();


				//String fn = (String)session.getAttribute("fname");
				//String ln = (String)session.getAttribute("lname");
				//String pn = (String)session.getAttribute("pno");
				//String em = (String)session.getAttribute("eml");
				//String ge = (String)session.getAttribute("gen");
				//String ag = (String)session.getAttribute("age");
				//String dj = (String)session.getAttribute("doj");
				//String bty = (String)session.getAttribute("btype");
				//String bt = (String)session.getAttribute("btime");
				//String dp = (String)session.getAttribute("dp");
				//String ds = (String)session.getAttribute("ds");
				//String bn = (String)session.getAttribute("bno");
				//String uid = (String)session.getAttribute("uid");
				//String sn = (String)session.getAttribute("sn");
				ResultSet rs2 = s1.executeQuery("select * from tickets where ticketid='"+tid+"'");
				rs2.next();

				String dj = rs2.getString("doj");
				String dp = rs2.getString("departure");
				String ds = rs2.getString("destination");
				String bt = rs2.getString("timing");
				String bty = rs2.getString("bustype");
				String bn = rs2.getString("busno");
				String sn = rs2.getString("seatno");

				ResultSet rs1 = s1.executeQuery("update availability set state='Available' where doj='"+dj+"' and departure='"+dp+"' and destination='"+ds+"' and timing='"+bt+"' and bustype='"+bty+"' and busno='"+bn+"' and seatno='"+sn+"'");
				rs1.next();				ResultSet rs = s.executeQuery("delete from tickets where ticketid='"+tid+"'");
				RequestDispatcher rd = req.getRequestDispatcher("cancelticket.html");
				rs.next();
				rd.include(req,res);
				rs1.close();
				rs2.close();
				s1.close();
				rs.close();
			}
		//if(!(rs.next()))
		//{
		//	out.println("<br><br><br><br><br><center><font color='red' size='10px'>You haven't booked any ticket yet!</font></center>");
		//	RequestDispatcher rd = req.getRequestDispatcher("cancelticket.html");
		//	rd.include(req,res);
		//}
		s.close();
		c.close();
	}
	catch(Exception e)
	{
		out.println(e+"<br><br><br><br><br><center><font color='red' size='10px'>Sorry! No ticket found with this Id...</font></center>");
	}
	}
}