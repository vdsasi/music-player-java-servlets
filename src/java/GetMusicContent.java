import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetMusicContent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");  
             Connection con=DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root","");  

            String query = "SELECT * FROM songs";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<JSONObject> songs = new ArrayList<>();

            while (resultSet.next()) {
                JSONObject song = new JSONObject();
                song.put("song_id", resultSet.getInt("songid"));
                song.put("file_path", resultSet.getString("file_path"));
                song.put("song_name", resultSet.getString("song_name"));
                song.put("song_type", resultSet.getString("song_type"));
                songs.add(song);
            }

            // Convert the list of songs to a JSON array
            JSONArray jsonArray = new JSONArray(songs);

            // Set the response type to application/json
            response.setContentType("application/json");

            // Write the JSON array to the response output stream
            response.getWriter().write(jsonArray.toString());

            // Clean up
            resultSet.close();
            statement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
