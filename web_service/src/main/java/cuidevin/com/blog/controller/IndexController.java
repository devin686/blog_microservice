package cuidevin.com.blog.controller;

import cuidevin.com.blog.NotFoundException;
import cuidevin.com.blog.entity.Blog;
import cuidevin.com.blog.entity.User;
import cuidevin.com.blog.feign.BlogFeignClient;
import cuidevin.com.blog.feign.UserFeignClient;
import cuidevin.com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private BlogFeignClient blogFeignClient;

    @RequestMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        //System.out.println(blogService.listBlog(pageable).getContent());
        for (Blog blog : blogService.listBlog(pageable).getContent()) {
            blog.setUser(userFeignClient.findUserById(blog.getUserId()));
        }
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("recommendBlogs", blogFeignClient.listRecommendBlogTop(8));

        return "index";
    }

    @RequestMapping("/inputBlog")
    public String inputBlog() {

        return "blogs-input";
    }

    @RequestMapping("/blogInputConfirm")
    public String blogInputConfirm(Blog blog, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
        blogFeignClient.saveBlog(blog);

        return "redirect:http://www.cuidevin.com:8080/myBlog";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        for (Blog blog : blogService.listBlog("%" + query + "%", pageable).getContent()) {
            blog.setUser(userFeignClient.findUserById(blog.getUserId()));
        }
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = blogFeignClient.getAndConvert(id);
        if (blog == null) {
            throw new NotFoundException("The blog does not exit");
        }
        blog.setUser(userFeignClient.findUserById(blog.getUserId()));
        model.addAttribute("blog", blog);

        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model) {
        model.addAttribute("newblogs", blogFeignClient.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }


}
