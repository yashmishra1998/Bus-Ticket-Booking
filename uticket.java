import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.UUID;

public class uticket extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession();

		String fn = (String)session.getAttribute("fname");
		String ln = (String)session.getAttribute("lname");
		String pn = (String)session.getAttribute("pno");
		String em = (String)session.getAttribute("eml");
		String ge = (String)session.getAttribute("gen");
		String ag = (String)session.getAttribute("age");
		String dj = (String)session.getAttribute("doj");
		String bty = (String)session.getAttribute("btype");
		String bt = (String)session.getAttribute("btime");
		String dp = (String)session.getAttribute("dp");
		String ds = (String)session.getAttribute("ds");
		String bn = (String)session.getAttribute("bno");
		String uid = (String)session.getAttribute("uid");

		String sn = req.getParameter("seatnumber");

		session.setAttribute("sn",sn);

		String fare="0";
		int dis;
		String diss;
		String td;

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","system");
			Statement s = c.createStatement();
			
			ResultSet rs = s.executeQuery("SELECT distance,departure FROM fare WHERE departure='"+dp+"' AND destination='"+ds+"'");
			rs.next();
			dis =(rs.getInt("distance"));
			String dep = rs.getString("departure");
			if(bty.equals("superdeluxe"))
				fare=Double.toString(dis*3.5);
			if(bty.equals("deluxe"))
				fare=Double.toString(dis*2.75);
			if(bty.equals("basic"))
				fare=Double.toString(dis*2);
			
			td = Double.toString(dis*1.25);

			diss = Double.toString(dis);

			UUID uuid = UUID.randomUUID();
			String ticketid = uuid.toString().substring(0,8);

			PreparedStatement ps = c.prepareStatement("insert into tickets(ticketid,userid,fname,lname,gender,age,email,phoneno,departure,destination,doj,bustype,busno,seatno,timing,distance,travelduration,fare) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1,ticketid);
			ps.setString(2,uid);
			ps.setString(3,fn);
			ps.setString(4,ln);
			ps.setString(5,ge);
			ps.setString(6,ag);
			ps.setString(7,em);
			ps.setString(8,pn);
			ps.setString(9,dep);
			ps.setString(10,ds);
			ps.setString(11,dj);
			ps.setString(12,bty);
			ps.setString(13,bn);
			ps.setString(14,sn);
			ps.setString(15,bt);
			ps.setString(16,diss);
			ps.setString(17,td);
			ps.setString(18,fare);
			ps.executeUpdate();

			PreparedStatement ps1 =	c.prepareStatement("update availability set state='Unavailable' where doj='"+dj+"' and departure='"+dep+"' and destination='"+ds+"' and timing='"+bt+"' and seatno='"+sn+"' and busno='"+bn+"' and bustype='"+bty+"'");		
			ps1.executeUpdate();
			out.println("<center><font size='10px'><b>Leisure Bus Services</b></center><hr><br><br><table width='50%' align='center'><tr><td>					TicketID :</td><td>"+ticketid+"</td></tr><tr><td>				FirstName:</td><td>"+fn+"</td></tr><tr><td>			LastName:</td><td>"+ln+"</td></tr><tr><td>Gender:</td><td>"+ge+"</td></tr><tr><td>Age:</td><td>"+ag+"</td></tr><tr><td>E-mail:</td><td>"+em+"</td></tr><tr><td>Phone_no:</td><td>"+pn+"</td></tr><tr><td>Departure:</td><td>"+dep+"</td></tr><tr><td>Destination:</td><td>"+ds+"</td></tr><tr><td>D.O.J:</td><td>"+dj+"</td></tr><tr><td>BusType:</td><td>"+bty+"</td></tr><tr><td>Bus_no:</td><td>"+bn+"</td></tr><tr><td>Seat_no:</td><td>"+sn+"</td></tr><tr><td>Timing:</td><td>"+bt+"</td></tr><tr><td>Distance:</td><td>"+diss+"</td></tr><tr><td>Travel_Duration:</td><td>"+td+"</td></tr><tr><td>Fare:</td><td>"+fare+"</td></tr></table><br><br><center><hr><form method='Post'><input formaction='PostEnquiry.html' style='background-color: green; color:white; width:110px; height:35px; border-style: solid ;border-radius: 10px; font-size: 15px;' type='submit' value='Book More'><br><input formaction='Homepage.html' style='background-color: rgb(0,176,255); color:blue; width:110px; height:35px; border-style: solid ;border-radius: 10px; font-size: 15px;' type='submit' value='HomePage'><br><input formaction='logout' style='background-color: rgb(255,255,0); color:chocolate; width:110px; height:35px; border-style: solid ;border-radius: 10px; font-size: 15px;' type='submit' value='Log Out'></form>Keep Visiting & Keep Booking</center>");

			ps1.close();
			ps.close();
			rs.close();
			s.close();
			c.close();
		}
		catch(Exception e)
		{
			System.out.println("Error in uticket.java"+e);
		}
		out.close();
	}
	
}