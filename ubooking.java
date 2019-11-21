import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class ubooking extends HttpServlet
{
	
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String fn = req.getParameter("firstname");
		String ln = req.getParameter("lastname");
		String pn = req.getParameter("phonenumber");
		String em = req.getParameter("email");
		String ge = req.getParameter("gender");
		String ag = req.getParameter("age");
		String dj = req.getParameter("doj");
		String bty = req.getParameter("bustype");
		String bt = req.getParameter("bustime");
		String dp = req.getParameter("departure");
		String ds = req.getParameter("destination");
		String bn = req.getParameter("busnumber");

		if(dp.equals(ds))
		{
			out.println("<center><font color='red'>Sorry! Departure & Destination can't be same</font></center>");
		}
		else
		{
			
			HttpSession session = req.getSession();

			session.setAttribute("fname",fn);
			session.setAttribute("lname",ln);
			session.setAttribute("pno",pn);
			session.setAttribute("eml",em);
			session.setAttribute("gen",ge);
			session.setAttribute("age",ag);
			session.setAttribute("doj",dj);
			session.setAttribute("btype",bty);
			session.setAttribute("btime",bt);
			session.setAttribute("dp",dp);
			session.setAttribute("ds",ds);
			session.setAttribute("bno",bn);
		try
		{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","system");
		Statement s = c.createStatement();
		//PreparedStatement ps = c.prepareStatement("SELECT seatno,state FROM availability WHERE doj='"+dj+"' and departure='"+dp+"' and destination='"+ds+"' and bustype='"+bty+"' and timing='"+bt+"' and busno='"+bn+"'");
		//ResultSet rs = ps.executeQuery();
		out.println("<center><font color='white' size='10px'><b>"+dj+"<hr></b></font></center>");
		String dateofjourney = (String)dj;    // NAUTANKI HAVE TO CONVERT DOJ TWICE TO GET IT INTO STRING TYPE IN ORDER TO COMPARE OT WITH THE DATABASE		
		ResultSet rs= s.executeQuery("SELECT seatno FROM availability WHERE doj='"+dateofjourney+"' and departure='"+dp+"' and destination='"+ds+"' and bustype='"+bty+"' and timing='"+bt+"' and busno='"+bn+"' and state='Available'");
		//ResultSet rs1 = s.executeQuery("select seatno from availability where doj='2018-07-24' and departure='"+dp+"' and destination='"+ds+"' and bustype='"+bty+"' and timing='"+bt+"' and busno='"+bn+"' and state='Available'");
		//while(rs1.next())
		//	{
		//		String seatno = rs1.getString("seatno");
		//		out.println(seatno+"	");
		//	}
		out.println("<center><font size='7px' color='white'><b>Available Seats:</b></font></center>");
		while(rs.next())
		{
			String sn = rs.getString("seatno");
			out.println("<font color='white' size='5px'><b>"+sn+" </b>,</font>");
		}

		out.println("<br><center><font color='white' size='15px'><hr>Final Step...Select your seat by looking at the seat arrangement in the pic below <hr></font><br><br><br><img style='border-radius:50px; border: solid 5px;' src='seatarrangement.png'><br><br><br></center>");
		
		RequestDispatcher rd = req.getRequestDispatcher("ubookseat.html");
		
		rd.include(req,res);
		

		//used in above print statement out.println("<br><br><center><form action='uticket' method='Post'><input style='background-color: green; color:white; width:80px; height:35px; border-style: solid ;border-radius: 10px; font-size: 15px;' type='submit' value='Book Now'></form></center>");
		//rs1.close();
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
	
}


