package ua.unsober.backend.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.unsober.backend.feature.auth.AuthRequest;
import ua.unsober.backend.feature.auth.AuthService;

@Controller
@RequiredArgsConstructor
public class TemplateLoginController {
    private final AuthService authService;

    @GetMapping({"/ui/login"})
    public String showLogin(Model model) {
        model.addAttribute("authRequest", new AuthRequest());
        return "login";
    }

    @PostMapping("/ui/auth")
    public String authenticate(@ModelAttribute AuthRequest authRequest,
                               HttpServletResponse response) {
        String jwt = authService.authenticate(authRequest).getToken();

        Cookie jwtCookie = new Cookie("jwt", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return "redirect:/ui/account";
    }
}
