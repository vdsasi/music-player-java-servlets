import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FetchMusic extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the song ID from the request parameter
        String songId = request.getParameter("songId");

        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root","");  
            Statement stmt = con.createStatement();

            // Retrieve the file path of the requested song from the database
            ResultSet rs = stmt.executeQuery("SELECT file_path FROM songs WHERE songid = " + songId);
            String filePath = "";
            if (rs.next()) {
                filePath = rs.getString("file_path");
            }

            // Set the content type of the response
            response.setContentType("audio/mpeg");

            // Create an input stream from the song file
            InputStream in = new FileInputStream(filePath);

            // Create a buffer to hold the song data
            byte[] buffer = new byte[4096];

            // Create an output stream to write the song data to the response
            OutputStream out = response.getOutputStream();

            // Stream the song data to the client
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            // Close the input and output streams
            in.close();
            out.flush();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
