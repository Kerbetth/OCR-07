package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.exceptions.IdDoesntExistException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * These controllers manage CRUD operations with the User object
 * and generate a model for listing, adding and updating
 */

@Controller
public class UserController {

    @Autowired
    private UserService userRepository;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userRepository.loadAllUser());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result) {
        if (userRepository.findUserByID(user.getId()) == null) {
            if (!result.hasErrors()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setEncodePassword(encoder.encode(user.getBrutePassword()));
                userRepository.updateUser(user);
                return "redirect:/user/list";
            }
            return "user/add";
        }
        throw new IdDoesntExistException(user.getId());
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findUserByID(id);
        user.setEncodePassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result) {
        if (userRepository.findUserByID(id) != null) {
            if (!result.hasErrors()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setEncodePassword(encoder.encode(user.getBrutePassword()));
                user.setId(id);
                userRepository.updateUser(user);
                return "redirect:/user/list";
            }
            return "user/update";
        }
        throw new IdDoesntExistException(id);
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        User user =userRepository.findUserByID(id);
        if (user != null) {
            userRepository.deleteUser(user);
            return "redirect:/user/list";
        }
        throw new IdDoesntExistException(id);
    }
}