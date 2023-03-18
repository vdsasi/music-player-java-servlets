import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 *
 * @author itadmin
 */
public class SignupServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try{
            Class.forName("com.mysql.jdbc.Driver");  
             Connection con=DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root","");  
             String name = request.getParameter("first-name");
             String password = request.getParameter("password");
             Statement st=con.createStatement();
             
             int s = st.executeUpdate("insert into users(name, password) values ('"+ name + "', '" + password + "')");
             out.println("signup succesful");
        } catch( Exception e ) {
            
        }
    }

}
