package cuidevin.com.user.service;

import cuidevin.com.user.entity.User;

public interface UserService {

    User findByID(Long id);

    User checkUser(String username, String password);

    User checkRepeatUser(String username);

    User saveUser(User user);

    User updateUser(Long id, User user);

    Long countUser();

}
