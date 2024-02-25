package com.smart.controller;


import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String home(Model m){
        m.addAttribute("title","Home-Smart Contact Manager");
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model m){
        m.addAttribute("title","About-Smart Contact Manager");
        return "about";
    }
    @RequestMapping("/signup")
    public String signup(Model m){
        m.addAttribute("title","SignUp-Smart Contact Manager");
        m.addAttribute("user",new User());
        return "signup";
    }

    @RequestMapping(value="/do_register",method= RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult results, @RequestParam(value="agreement",defaultValue = "false")boolean agreement, Model model, HttpSession session){
        if(results.hasErrors()){
            model.addAttribute("user",user);
            System.out.println(results);
            return "signup";
        }
        try{
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User result=this.userRepository.save(user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("Successfully registered","alert-success"));

        }
        catch(Exception e){
            e.printStackTrace();
            model.addAttribute("user",user);
            session.setAttribute("message",new Message("Soemthing went wrong"+e.getMessage(),"alert-error"));
            return "signup";
        }
        return "signup";

    }
    @RequestMapping("/signin")
    public String customlogin(Model m){
        m.addAttribute("title","Smart Contact Manager");
        return "login";
    }


}
