

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Balance
 */
@WebServlet("/Balance")
public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Balance() {
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
			PreparedStatement ps=con.prepareStatement("SELECT Amount from bank_employee where Account_number=? AND Name=? AND password=?");
			ps.setLong(1, Account_Number);
			ps.setString(2,Name);
			ps.setString(3, Password);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				Long balance=rs.getLong("Amount");
				out.println("Account number= "+Account_Number+"<br>");
				out.println("balance= "+balance);
			}
			else {
				out.println("Authentication failed");
			}
			con.close();
		}
		catch(Exception ex)
		{
			out.println(ex);
		}
	}

}
