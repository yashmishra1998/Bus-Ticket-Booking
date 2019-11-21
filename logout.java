import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.Cookie; 

public class logout extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		Cookie ck=new Cookie("id","");  
        	ck.setMaxAge(0);  
        	res.addCookie(ck);

		 HttpSession session=req.getSession();  
         session.invalidate();
        res.sendRedirect("http://www.google.com");
        out.println("You are Successfully logged out!");
    }
}