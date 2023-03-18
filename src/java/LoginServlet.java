import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 *
 * @author itadmin
 */
public class LoginServlet extends HttpServlet {

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
             String name = request.getParameter("username");
             String password = request.getParameter("password");
             Statement st=con.createStatement();
             ResultSet rs = st.executeQuery("select * FROM users WHERE name = '" + name + "' AND password = '" + password + "'");
             
             if( rs.next() ) {
                     HttpSession session = request.getSession();
                    session.setAttribute("username", name);
                 RequestDispatcher rd = request.getRequestDispatcher("MainPageServlet");
                 rd.forward(request, response);
             } else {
                 out.println("Incorrect Details entered go to login page by clicking <a href='index.html'>here</a>");
                  
             }
//             
        } catch( Exception e ) {
            out.println(e);
        }
    }

}
