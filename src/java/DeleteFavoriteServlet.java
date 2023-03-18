import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteFavoriteServlet extends HttpServlet {
  

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
    String songId = request.getParameter("songId");

    try {
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root","");   
      String deleteSql = "DELETE FROM favorites WHERE username = ? AND songid = ?";
      try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
        stmt.setString(1, username);
        stmt.setString(2, songId);
        int numRowsDeleted = stmt.executeUpdate();
        if (numRowsDeleted > 0) {
          PrintWriter out = response.getWriter();
          out.println("Song removed from favorites!");
        } else {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST,
              "Song not found in favorites for user " + username);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Error removing song from favorites");
    }
  }
}
