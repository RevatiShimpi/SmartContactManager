package com.smart.controller;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal; //used to get user from db
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @ModelAttribute   //runs for every attribute
    public void AddCommonData(Model m,Principal p){
        String username=p.getName();  //this gets only username
        System.out.println("USERNAME"+username);
        User user=userRepository.getUserByUserName(username); //this gets complete userdata
        m.addAttribute("user","user");

    }
    @RequestMapping("/index")
    public String dashboard(Model m, Principal p){

        return "normal/user_dashboard";
    }
    @RequestMapping("/addcontact")
    public String openAddContactForm(Model m){
        m.addAttribute("title","Add Contact");
        m.addAttribute("contact",new Contact());
        return "normal/addcontactform";
    }
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, Principal principal, HttpSession session){
        String name=principal.getName();
        User user=this.userRepository.getUserByUserName(name);
        contact.setUser(user);
        user.getContacts().add(contact);
        this.userRepository.save(user);
        //message succes...
        session.setAttribute("message",new Message("Contact added successfully!!","success"));

        return "normal/add_contact_form";
    }
    //Show Contacts
    @GetMapping("/show-contacts/{page}")
    //per page 5
    public String showContacts(@PathVariable("page") Integer page, Model m, Principal p){
        m.addAttribute("title","Show User Contacts");
//        String userName=p.getName();
//        User user=this.userRepository.getUserByUserName(userName);
//        List<Contact> contacts=user.getContacts();
        String userName=p.getName();
        User user=this.userRepository.getUserByUserName(userName);
        Pageable pageable= PageRequest.of(page,5);
        Page<Contact> contacts=this.contactRepository.findContactsByUser(user.getId(),pageable);
        m.addAttribute("contacts",contacts);
        m.addAttribute("currentPage",page);
        m.addAttribute("totalPages",contacts.getTotalPages());
        return "normal/show_contacts";
    }

    //Delete Contact Handler

    @GetMapping("/delete/{CiD}")
    public String deleteContact(@PathVariable("CiD") Integer cId,Model m,Principal p,HttpSession session){
        Optional<Contact> contactOptional=this.contactRepository.findById(cId);
        Contact contact = contactOptional.get();

        String username=p.getName();
        User user=this.userRepository.getUserByUserName(username);
        contact.setUser(null);
        this.contactRepository.delete(contact);

        //check(security..the person deleting contact should be the user)
        if(user.getId()==contact.getUser().getId()){
            m.addAttribute("contact",contact);
            m.addAttribute("title",contact.getName());
        }
        session.setAttribute("message",new Message("Contact deleted successfully","success"));
        return "redirect:/user/show-contacts/0";
    }
    //Update Form Handler
    @PostMapping("/update/{CiD}")
    public String Update(@PathVariable("CiD") Integer cId, Model m,Principal p){

        m.addAttribute("title","Update Contact");

        Contact contact=this.contactRepository.findById(cId).get();
        m.addAttribute("contact",contact);
        return "/normal/updateform";
    }
    @RequestMapping(value="/process-update",method=RequestMethod.POST)
    public String updateHandler(@ModelAttribute Contact contact,Principal p){
        try{

            //old contact details
            Contact oldcontact=this.contactRepository.findById(contact.getCid()).get();

            User user=this.userRepository.getUserByUserName(p.getName());
            contact.setUser(user);
            this.contactRepository.save(contact);

        } catch(Exception e){

        }
        return "redirect:/normal/show-contacts ";
    }


}

