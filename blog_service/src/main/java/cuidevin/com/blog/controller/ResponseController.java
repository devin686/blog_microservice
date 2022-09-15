package cuidevin.com.blog.controller;

import cuidevin.com.blog.entity.Blog;
import cuidevin.com.blog.entity.Comment;
import cuidevin.com.blog.service.BlogService;
import cuidevin.com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogResponse")
public class ResponseController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/saveBlog")
    public void saveBlog(@RequestBody Blog blog) {
        blogService.saveBlog(blog);
    }

    @RequestMapping(value = "/listRecommendBlog")
    public List<Blog> listRecommendBlogTop(@RequestParam("size") Integer size) {
        return blogService.listRecommendBlogTop(size);
    }

    @RequestMapping(value = "/blog/{id}")
    public Blog getAndConvert(@PathVariable Long id) {
        return blogService.getAndConvert(id);
    }

    @RequestMapping("edit/{id}")
    public Blog editBlog(@PathVariable Long id) {
        Blog blog = blogService.getBlog(id);
        return blog;
    }

    @RequestMapping("/confirmEdit")
    public Blog confirmEdit(@RequestBody Blog blog) {
        Blog b = blogService.updateBlog(blog.getId(), blog);
        return b;
    }

    @RequestMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }

    @RequestMapping("/comments/{blogId}")
    public List<Comment> comments(@PathVariable Long blogId) {
        return commentService.listCommentByBlogId(blogId);
    }

    @RequestMapping(value = "/saveComment")
    public Comment saveComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    @RequestMapping(value = "/findCommentById/{id}")
    public Comment findUserById(@PathVariable Long id) {
        Comment comment = commentService.findByID(id);
        return comment;
    }

    @RequestMapping("deleteComment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }


}
