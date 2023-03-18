import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

public class LikeOrDislikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        String songId = request.getParameter("songId");
try{
        Class.forName("com.mysql.jdbc.Driver");  
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost/new?characterEncoding=utf8","root",""); 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from favorites where username ='" + username + "' and songid = " + songId); 
             response.setContentType("application/json");
                List<JSONObject> songs = new ArrayList<>();
                JSONObject song = new JSONObject();
        if(rs.next()) {
            song.put("is_there", true);
            songs.add(song);
JSONArray jsonArray = new JSONArray(songs);
response.getWriter().write(jsonArray.toString());
        }else {
            song.put("is_there", false);
            songs.add(song);
JSONArray jsonArray = new JSONArray(songs);
response.getWriter().write(jsonArray.toString());
}
}catch(Exception e) {

}        
    }
}
