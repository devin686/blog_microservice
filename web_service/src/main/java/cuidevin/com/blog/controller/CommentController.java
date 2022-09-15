package cuidevin.com.blog.controller;

import cuidevin.com.blog.entity.Comment;
import cuidevin.com.blog.feign.BlogFeignClient;
import cuidevin.com.blog.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private BlogFeignClient blogFeignClient;

    @RequestMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = blogFeignClient.comments(blogId);
        for (Comment comment : comments) {
            comment.setUser(userFeignClient.findUserById(comment.getUserId()));
        }
        System.out.println(comments);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    @RequestMapping("/comments")
    public String publishComment(Comment comment) {

        blogFeignClient.saveComment(comment);
        return "redirect:http://www.cuidevin.com:8080/comments/" + comment.getBlogId();
    }

    @RequestMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable Long id) {
        Comment comment = blogFeignClient.findCommentById(id);
        blogFeignClient.deleteComment(id);
//        return "redirect:http://localhost:8080/blog/" + comment.getBlogId();
        return "redirect:http://www.cuidevin.com:8080/blog/" + comment.getBlogId();
    }
}
