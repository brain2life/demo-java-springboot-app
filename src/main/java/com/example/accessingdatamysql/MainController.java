package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    // CREATE
    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    // READ ALL
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ ONE
    @GetMapping(path="/user/{id}")
    public @ResponseBody User getUserById(@PathVariable Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // UPDATE
    @PutMapping(path="/update/{id}")
    public @ResponseBody String updateUser(@PathVariable Integer id, @RequestParam String name, @RequestParam String email) {
        return userRepository.findById(id).map(user -> {
            user.setName(name);
            user.setEmail(email);
            userRepository.save(user);
            return "Updated";
        }).orElse("User not found");
    }

    // DELETE
    @DeleteMapping(path="/delete/{id}")
    public @ResponseBody String deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "Deleted";
        }
        return "User not found";
    }
}