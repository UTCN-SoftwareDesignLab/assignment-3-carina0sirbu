package service.user;

import dto.UserDto;
import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.security.MessageDigest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public boolean create(UserDto userDto) {

        String password = encoder.encode(userDto.getPassword());

        User user = new User(userDto.getName(), userDto.getUsername(), password, Role.DOCTOR);
        System.out.println(user);

        userRepository.save(user);
        return true;
    }

    @Override
    public boolean delete(UserDto userDto) {


        User user = new User(userDto.getName(), userDto.getUsername(), encoder.encode(userDto.getPassword()), Role.DOCTOR);
        userRepository.delete(user);
        return true;
    }

    @Override
    public boolean update(UserDto userDto) {

        User user = userRepository.findById(userDto.getId()).get();

        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setPassword(encoder.encode(userDto.getPassword()));

        userRepository.save(user);

        return userRepository.findById(user.getId()).isPresent();
    }

    @Override
    public User findByName(String name) {

        return userRepository.findByName(name);
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {

        return userRepository.findByUsernameAndPassword(username, encoder.encode(password));
    }

    @Override
    public boolean deleteById(Long id) {

        userRepository.deleteById(id);

        return true;
    }
}
