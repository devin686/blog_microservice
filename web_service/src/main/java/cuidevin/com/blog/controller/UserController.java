package cuidevin.com.blog.controller;

import cuidevin.com.blog.entity.User;
import cuidevin.com.blog.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @RequestMapping("/testRibbon")
    @ResponseBody
    public String testRibbon(){
        return  userFeignClient.testRibbon();
    };

    @RequestMapping("/login")
    public String loginPage() {

        return "login";
    }

    @RequestMapping("/register")
    public String register() {

        return "register";
    }


    @RequestMapping("/loginConfirm")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes, Model model) {
        User user = userFeignClient.checkUser(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:http://www.cuidevin.com:8080:8080/";


        } else {
            attributes.addFlashAttribute("message", "username or password is wrong");

            return "redirect:http://www.cuidevin.com:8080/login";
        }

    }

    @RequestMapping("/user")
    public String userInformation(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);

        return "user";
    }

    @RequestMapping("/registerConfirm")
    public String registerConfirm(@Valid User user, @RequestParam String username, RedirectAttributes attributes) {
        User user1 = userFeignClient.checkRepeatUser(username);
        if (user1 == null) {
            userFeignClient.saveUser(user);
            attributes.addFlashAttribute("message", "Register successfully!");
            return "redirect:http://www.cuidevin.com:8080/login";
        } else {
            attributes.addFlashAttribute("message", "The username is repeated, please input another one");

            return "redirect:http://www.cuidevin.com:8080/register";
        }

    }

    @RequestMapping("/editUserInfo")
    public String editUserInfo(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        model.addAttribute("user", user);
        return "editUserInfo";
    }

    @RequestMapping("/update")
    public String updateUser(HttpServletRequest request, @Valid User user, RedirectAttributes attributes) {

        HttpSession session = request.getSession();
        String username_new = user.getUsername();
        String password_new = user.getPassword();

        User user_old = (User) session.getAttribute("user");
        String username_old = user_old.getUsername();
        String password_old = user_old.getPassword();

        User user1 = userFeignClient.checkRepeatUser(username_new);
        if (user1 == null || user1.getId() == user.getId()) {
            userFeignClient.updateUser(user);
            if (username_new.equals(username_old) && password_new.equals(password_old)) {
                session.removeAttribute("user");
                User user_new = userFeignClient.checkUser(username_new, password_new);
                session.setAttribute("user", user_new);

                return "redirect:http://www.cuidevin.com:8080/user";
            } else {

                return "redirect:http://www.cuidevin.com:8080/login";
            }
        } else {
            attributes.addFlashAttribute("message", "The username is repeated, please input another one");

            return "redirect:http://www.cuidevin.com:8080/editUserInfo";
        }


    }


    @RequestMapping("/logout")
    public void logout(HttpSession session) {
        session.removeAttribute("user");

    }
}
