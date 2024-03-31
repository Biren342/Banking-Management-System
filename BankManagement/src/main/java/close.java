

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class close
 */
@WebServlet("/close")
public class close extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public close() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		Long Account_Number=Long.parseLong(request.getParameter("acno"));
		String Name=request.getParameter("name");
		String Password=request.getParameter("psw");
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","birendb","birendb");
			PreparedStatement ps=con.prepareStatement("delete from bank_employee where Account_Number=? AND Name=? AND Password=?");
			ps.setLong(1, Account_Number);
			ps.setString(2, Name);
			ps.setString(3, Password);
			int i=ps.executeUpdate();
			if(i>0)
			{
				out.println("Account Closed Successfully");
			}
			else
			{
				out.println("Wrong Account Number and Password");
			}
			con.close();
		}
		catch(Exception ex)
		{
			out.println(ex);
		}
	}

}
