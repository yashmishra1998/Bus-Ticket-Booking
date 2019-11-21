import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class manage extends HttpServlet
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
		ResultSet rs = s.executeQuery("select userid,password,fname from users");
		
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
					flag=2;
					out.println("<center><font color='red' size='10px'>Kindly copy the \"Ticket Id\" in order to cancel a ticket</center><br>");
					out.println("<center><font color='black' size='5px'>Detailed List of Tickets Booked:</font></center><hr><br>");
					Statement s1 = c.createStatement();
					ResultSet rs1= s.executeQuery("Select * from tickets where userid='"+id+"'");
					while(rs1.next())
					{
						String tid = rs1.getString("ticketid");
						String uid = rs1.getString("userid");
						//String fnm = rs.getString("fname");
						String ln = rs1.getString("lname");
						String ge = rs1.getString("gender");
						String ag = rs1.getString("age");
						String em = rs1.getString("email");
						String pn = rs1.getString("phoneno");
						String dp = rs1.getString("departure");
						String ds = rs1.getString("destination");
						String dj = rs1.getString("doj");
						String bt = rs1.getString("bustype");
						String bn = rs1.getString("busno");
						String sn = rs1.getString("seatno");
						String ti = rs1.getString("timing");
						String dis = rs1.getString("distance");
						String td = rs1.getString("travelduration");
						String fare = rs1.getString("fare");

						out.println("<center><font color='black' size='3px' style ='margin-top:100px;white-space:pre;''>* TICKET_ID: "+tid+" <b>|</b>USER_ID: "+uid+" <b>|</b>D.O.J: "+dj+" <b>|</b>BUS_TIMING: "+ti+" <b>|</b>DEPARTURE: "+dp+" <b>|</b>DESTINATION: "+ds+" <b>|</b>DISTANCE: "+dis+" <b>|</b>TRAVEL_DURATION: "+td+" <b>|</b>FARE: "+fare+" <b>|</b>BUS_TYPE: "+bt+" <b>|</b>BUS_NO: "+bn+" <b>|</b>SEAT_NO: "+sn+"</font></pre></center><br>");
						flag=3;
					}
					if(flag!=3)
					{
						out.println("<center><font color='red' size='10px'>You haven't booked any ticket yet!</font></center>");
						
					}
					out.println("<center><hr><a href='cancelticket.html' style='text-decoration:none;color:red;'><font size='7px' color='red'>CANCEL TICKETS</font></a></center>");
					s1.close();
					rs1.close();
					break;
				}
			}
		}

		if(flag==1)
		{
			out.println("<center><font color='red' font='12px'>Incorret Password !</font></center>");
		}
		if(flag==0)
		{
			out.println("<center><font color='red' font='9px'>In Order to Login You Need to Signup First</font></center>");
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