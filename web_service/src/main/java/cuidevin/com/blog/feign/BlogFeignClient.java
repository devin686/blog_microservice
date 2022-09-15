package cuidevin.com.blog.feign;

import cuidevin.com.blog.entity.Blog;
import cuidevin.com.blog.entity.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "blog-server", path = "/blogResponse")
public interface BlogFeignClient {
    @RequestMapping(value = "/saveBlog", consumes = "application/json")
    public void saveBlog(@RequestBody Blog blog);

    @RequestMapping(value = "/listRecommendBlog")
    public List<Blog> listRecommendBlogTop(@RequestParam("size") Integer size);

    @RequestMapping(value = "/blog/{id}")
    public Blog getAndConvert(@PathVariable Long id);

    @RequestMapping("edit/{id}")
    public Blog editBlog(@PathVariable Long id);

    @RequestMapping(value = "/confirmEdit", consumes = "application/json")
    public Blog confirmEdit(@RequestBody Blog blog);

    @RequestMapping("delete/{id}")
    public void delete(@PathVariable Long id);

    @RequestMapping("/comments/{blogId}")
    public List<Comment> comments(@PathVariable Long blogId);

    @RequestMapping(value = "/saveComment", consumes = "application/json")
    public Comment saveComment(@RequestBody Comment comment);

    @RequestMapping("deleteComment/{id}")
    public void deleteComment(@PathVariable Long id);

    @RequestMapping("/findCommentById/{id}")
    public Comment findCommentById(@PathVariable("id") Long id);


}
