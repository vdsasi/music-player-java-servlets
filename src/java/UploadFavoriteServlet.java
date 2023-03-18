import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UploadFavoriteServlet extends HttpServlet {
  
  // Change the following constants to match your database configuration
  

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
    String songId = request.getParameter("songId");
    try  {
           Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root","");   
             
      String insertSql = "INSERT INTO favorites (username, songid) VALUES (?, ?)";
      try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
        stmt.setString(1, username);
        stmt.setString(2, songId);
        stmt.executeUpdate();
      }

      PrintWriter out = response.getWriter();
      out.println("Song added to favorites!");
    } catch (SQLException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Error adding song to favorites");
    }
    catch(Exception e) {
}
  }
}
