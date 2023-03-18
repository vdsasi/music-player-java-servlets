/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vdsas
 */
public class FavoritePageServlet extends HttpServlet {

    

    protected void processrequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);

        String username = (String) session.getAttribute("username");
PrintWriter out = response.getWriter();
        try {
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root","");
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("SELECT s.songid, s.song_name, s.song_type, s.file_path FROM songs s INNER JOIN favorites f ON s.songid = f.songid WHERE f.username = '" + username + "'");
    RequestDispatcher rd = request.getRequestDispatcher("Navbar.html");
    rd.include(request, response);

    if (rs.next()) {
        out.println("<div style='display:flex;flex-wrap:wrap;'>");
        do {
            out.println("<div class=\"card\" style=\"padding:10px;width:21rem;margin:10px\">");
            out.println("<h5 class='card-title'>"+ rs.getString("song_name") +"</h5>");
            out.println("<p class=\"card-text\">" + rs.getString("song_type") + "</p>");
            out.println("<audio controls>");
            out.println("<source src= 'FetchMusic?songId="+rs.getInt("songid") + "' type=\"audio/mpeg\">");
            out.println("Your browser does not support the audio element.");
            out.println("</audio><br>");

            out.println("<form action='DeleteFavoriteServlet' method='post'><input type=\"hidden\" name=\"songId\" value=\" " +rs.getInt("songid") + "\"><button type='submit'>Dislike</button></form>");
            out.println("</div>");
        } while (rs.next());
        out.println("</div>");
    } else {
        out.println("<p>No Favorite Elements Found.Go To Home Page to Add One Click <a href='MainPageServlet'>Here</a></p>");
    }
} catch(Exception e) {
    // handle exceptions here
    out.println(e);
}

    }
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
processrequest(request,response);
}
protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
processrequest(request,response);
}

}

