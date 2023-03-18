import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class MusicUpload extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // Define the directory where uploaded files will be saved
  private static final String SAVE_DIR = "C:\\Users\\vdsas\\Downloads\\uploads";

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Get the file chosen by the user in the form
    Part filePart = request.getPart("file");
    String fileName = filePart.getSubmittedFileName();

    // Get other form data
    String songId = request.getParameter("songId");
    String songName = request.getParameter("songName");
    String songType = request.getParameter("songType");

    // Set the save directory
    String savePath = SAVE_DIR;

    // Create the save directory if it does not exist
    File fileSaveDir = new File(savePath);
    if (!fileSaveDir.exists()) {
      fileSaveDir.mkdir();
    }

    // Save the file to the server
    filePart.write(savePath + File.separator + fileName);

    // Save the file location to the database
    try {
      // Connect to the database
      Class.forName("com.mysql.jdbc.Driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root","");

      // Create the SQL statement to insert the file location
      String sql = "INSERT INTO songs ( songid, song_name, file_path, song_type) VALUES (?, ?, ?, ?)";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setInt(1, Integer.parseInt(songId));
      statement.setString(2, songName);
      statement.setString(3, savePath + File.separator + fileName);
      statement.setString(4, songType);

      // Execute the SQL statement
      statement.executeUpdate();

      // Clean up
      statement.close();
      conn.close();

    } catch (Exception e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    // Redirect the user back to the form page
    response.sendRedirect("MainPageServlet");
  }
}
