package cuidevin.com.blog.feign;

import cuidevin.com.blog.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@FeignClient(name = "user-server", path = "/userResponse")
public interface UserFeignClient {

    @RequestMapping("/testRibbon")
    public String testRibbon();

    @RequestMapping("/findUserById/{id}")
    public User findUserById(@PathVariable("id") Long id);

    @RequestMapping("/checkUser")
    public User checkUser(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping("/checkRepeatUser")
    public User checkRepeatUser(@RequestParam("username") String username);

    @RequestMapping(value = "/saveUser", consumes = "application/json")
    public void saveUser(@Valid @RequestBody User user);

    @RequestMapping(value = "/updateUser", consumes = "application/json")
    public void updateUser(@Valid @RequestBody User user);


}
