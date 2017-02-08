package com.theironyard.controllers;

import com.theironyard.entities.Trip;
import com.theironyard.entities.User;
import com.theironyard.services.CarRepository;
import com.theironyard.services.PasswordStorage;
import com.theironyard.services.TripRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class EtriprController {
    @Autowired
    UserRepository users;

    @Autowired
    CarRepository cars;

    @Autowired
    TripRepository trips;

    @CrossOrigin
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(String email, String password, HttpSession session, HttpServletResponse response) throws Exception {
        User user = users.findByEmail(email);
        if (user != null) {
            if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
                throw new Exception("Wrong password");
            }
        } else {
            throw new Exception("Wrong email");
        }
        session.getAttribute(email);
        response.sendRedirect("/new-trip");
        return user;
    }

    @CrossOrigin
    @RequestMapping(path = "/new-user", method = RequestMethod.POST)
    public User newUser(String name, String password, String email, String car, HttpSession session, HttpServletResponse response ) throws Exception {
        User user = users.findByEmail(email);

        if(user == null) {
            user = new User(name, PasswordStorage.createHash(password), email, car);
            users.save(user);
        } else {
            throw new Exception("User already exists");
        }
        session.setAttribute("email", email);
        response.sendRedirect("/new-trip");
        return user;
    }

    @CrossOrigin
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("/login");
    }

//    @CrossOrigin
//    @RequestMapping(path = "/new-trip", method = RequestMethod.POST)
//    public Trip newTrip(String tripName, String startLocation, String endLocation, HttpSession session, HttpServletResponse response) throws Exception {
//
//
//
//
//    }
//
//    @CrossOrigin
//    @RequestMapping(path = "/trip-list", method = RequestMethod.GET)
//
//    }
}
