package cuidevin.com.user.controller;

import cuidevin.com.user.entity.User;
import cuidevin.com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/userResponse")
public class ResponseController {
    @Value("${server.port}")
    private String port;
    @Value("${spring.cloud.client.ip-address}")
    private String ip;
    @Autowired
    private UserService userService;

    @RequestMapping("/testRibbon")
    public String testRibbon() {
        return ip +": "+ port;
    }

    @RequestMapping("/login")
    public String loginPage() {

        return "login";
    }

    @RequestMapping("/register")
    public String register() {

        return "register";
    }

    @RequestMapping(value = "/findUserById/{id}")
    public User findUserById(@PathVariable Long id) {
        User user = userService.findByID(id);
        return user;
    }

    @RequestMapping("/checkUser")
    public User checkUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.checkUser(username, password);
        System.out.println("访问的服务地址: " + ip + ": " + port);
        return user;
    }

    @RequestMapping("/checkRepeatUser")
    public User checkRepeatUser(@RequestParam String username) {
        User user1 = userService.checkRepeatUser(username);

        return user1;
    }

    @RequestMapping("/saveUser")
    public void saveUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping("/updateUser")
    public void updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user.getId(), user);
    }


}
