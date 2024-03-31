

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class withdraw
 */
@WebServlet("/withdraw")
public class withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public withdraw() {
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
		Long Amount=Long.parseLong(request.getParameter("amt"));
		Long old_amt;
		Long new_amt;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","birendb","birendb");
			PreparedStatement ps=con.prepareStatement("select amount from bank_employee where Account_Number=? AND Name=? AND Password=?");
			ps.setLong(1, Account_Number);
            ps.setString(2, Name);
            ps.setString(3, Password);
            ResultSet rs=ps.executeQuery();

            
            if (rs.next()) {
                old_amt = rs.getLong(1);
                new_amt = old_amt - Amount;
                if(new_amt<0)
                {
                	out.println("<b>Insufficient fund</b>");
                	return;
                }
                
                
                PreparedStatement ps2 = con.prepareStatement("update bank_employee set Amount=? where Account_Number=?");
                ps2.setLong(1, new_amt);
                ps2.setLong(2, Account_Number);

                ps2.executeUpdate();

                out.print("<b>My original balance is: </b>" + old_amt + "<br>");
                out.print("<b>My withdraw balance is: </b>"+ Amount + "<br>");
                out.print("<b>New balance is: </b>" + new_amt+"<br>");
            }
		}
		catch(Exception ex)
		{
			out.print(ex);
		}
	}

}
