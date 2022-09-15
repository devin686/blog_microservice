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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class myBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private BlogFeignClient blogFeignClient;

    @RequestMapping("/myBlog")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request, Model model) {

        User user = (User) request.getSession().getAttribute("user");
        Long userId = user.getId();

        for (Blog blog : blogService.listBlog(userId, pageable).getContent()) {

            blog.setUser(userFeignClient.findUserById(blog.getUserId()));

        }
        model.addAttribute("page", blogService.listBlog(userId, pageable));

        return "myBlog";
    }

    @RequestMapping("edit/{id}")
    public String editBlog(@PathVariable Long id, Model model) {
        Blog blog = blogFeignClient.editBlog(id);
        model.addAttribute("blog", blog);
        return "editBlog";

    }

    @RequestMapping("/confirmEdit")
    public String confirmEdit(Blog blog) {
        Blog b = blogFeignClient.confirmEdit(blog);
        if (b == null) {
            throw new NotFoundException("The blog does not exit");
        }

        return "redirect:http://www.cuidevin.com:8080/myBlog";

    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        blogFeignClient.delete(id);

        return "redirect:http://www.cuidevin.com:8080/myBlog";
    }


}
