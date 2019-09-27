package hr.ja.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
public class MyRestController {

    @RequestMapping("/")
    public String home(Principal principal) {
        log.debug("user {}", principal);
        return "index";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();

        return "redirect:/";
    }

    @Secured("ROLE_WEB")
    @RequestMapping("/protected_web")
    public String protectedWeb(Principal principal) {
        log.debug("user {}", principal);
        return "protected_web";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin_page")
    public String adminPage(Principal principal) {
        log.debug("Admin user {}", principal);
        return "admin_page1";
    }

    @RequestMapping("/web1")
    public String web1(Principal principal) {
        log.debug("user {}", principal);
        return "web1";
    }
}
