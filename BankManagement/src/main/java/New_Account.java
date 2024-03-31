

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class New_Account
 */
@WebServlet("/New_Account")
public class New_Account extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public New_Account() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		Long Account_no=Long.parseLong(request.getParameter("acno"));
		String Name=request.getParameter("name");
 		String Password=request.getParameter("psw");
		String Conform_psw=request.getParameter("cpsw");
		Long Amount=Long.parseLong(request.getParameter("amt"));
		String Address=request.getParameter("addr");
		Long Mobil_no=Long.parseLong(request.getParameter("mno"));
		if (!Password.equals(Conform_psw))
		{
			out.println("Password And Conform password missmatch");
			return;
		}
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","birendb","birendb");
			PreparedStatement ps=con.prepareStatement("insert into bank_employee values(?,?,?,?,?,?,?)");
			ps.setLong(1, Account_no);
			ps.setString(2, Name);
			ps.setString(3, Password);
			ps.setString(4, Conform_psw);
			ps.setLong(5, Amount);
			ps.setString(6, Address);
			ps.setLong(7, Mobil_no);
			int i=ps.executeUpdate();
			out.println("Register Successfully");
			con.close();
		}
		catch(Exception ex)
		{
			out.print(ex);
		}
	}

	}
