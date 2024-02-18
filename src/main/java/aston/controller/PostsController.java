package aston.controller;

import aston.dto.PostDto;
import aston.entity.Post;
import aston.repository.JDBCPostRepository;
import aston.repository.JDBCUserRepository;
import aston.service.JDBCPostService;
import aston.service.JDBCUserService;
import aston.service.PostService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/", "/post"})
public class PostsController extends HttpServlet {

    private final PostService postService =
            new JDBCPostService(new JDBCPostRepository(), new JDBCUserService(new JDBCUserRepository()));

    public PostsController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/post")) {
            JSONObject jsnObj = jSONConverter(request);
            int id = (int) jsnObj.get("id");
            String title = (String) jsnObj.get("title");
            PostDto post = postService.findPostDTOByName(id, title);
            request.setAttribute("post", post);
            getServletContext().getRequestDispatcher("/post.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsnObj = jSONConverter(request);
        String title = (String) jsnObj.get("title");
        String description = (String) jsnObj.get("description");
        postService.create(new Post(title, description));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsnObj = jSONConverter(request);
        int id = (int) jsnObj.get("id");
        postService.delete(id);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsnObj = jSONConverter(request);
        int id = (int) jsnObj.get("id");
        String title = (String) jsnObj.get("title");
        String description = (String) jsnObj.get("description");
        Post post = new Post(id, title, description);
        postService.update(post);
    }

    private JSONObject jSONConverter(HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(request.getInputStream(), JSONObject.class);
    }

}

