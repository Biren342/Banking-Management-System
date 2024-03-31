import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Deposit() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Long Account_Number = Long.parseLong(request.getParameter("acno"));
        String Name = request.getParameter("name");
        String Password = request.getParameter("psw");
        Long Amount = Long.parseLong(request.getParameter("amt"));
        Long old_amt, new_amt;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "birendb", "birendb");
            
            // Selecting existing amount
            PreparedStatement ps = con.prepareStatement("select amount from bank_employee where Account_Number=? AND Name=? AND Password=?");
            ps.setLong(1, Account_Number);
            ps.setString(2, Name);
            ps.setString(3, Password);
            ResultSet rs=ps.executeQuery();

            
            if (rs.next()) {
                old_amt = rs.getLong(1);
                new_amt = old_amt + Amount;
                
                // Updating the amount
                PreparedStatement ps2 = con.prepareStatement("update bank_employee set Amount=? where Account_Number=?");
                ps2.setLong(1, new_amt);
                ps2.setLong(2, Account_Number);

                ps2.executeUpdate();

                out.print("<b>My original balance is: </b>" + old_amt + "<br>");
                out.print("<b>My deposit balance is: </b>"+ Amount + "<br>");
                out.print("<b>New balance is: </b>" + new_amt+"<br>");
            } else {
                out.print("Invalid credentials or account not found.");
            }
        } catch (Exception ex) {
            out.print(ex);
        }
    }
}
