import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


/**
 *
 * @author itadmin
 */
public class MainPageServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session!=null ) {
            
                PrintWriter out = response.getWriter();
                RequestDispatcher rd = request.getRequestDispatcher("Navbar.html");
                rd.include(request, response);
                RequestDispatcher rd1 = request.getRequestDispatcher("content.html");
                rd1.include(request, response);
        } else {
         response.sendRedirect("index.html");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        RequestDispatcher rd = request.getRequestDispatcher("Navbar.html");
        rd.include(request, response);
        RequestDispatcher rd1 = request.getRequestDispatcher("content.html");
        rd1.include(request, response);
        
    }

}
