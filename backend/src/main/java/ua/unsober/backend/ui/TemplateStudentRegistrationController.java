package ua.unsober.backend.ui;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.utils.cookies.CookieUtils;
import ua.unsober.backend.feature.auth.JwtTokenService;
import ua.unsober.backend.feature.speciality.SpecialityService;
import ua.unsober.backend.feature.student.StudentRequestDto;
import ua.unsober.backend.feature.student.StudentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TemplateStudentRegistrationController {
    private final JwtTokenService jwtService;
    private final StudentService studentService;
    private final SpecialityService specialityService;

    @GetMapping("/ui/student-registration")
    public String showRegistration(HttpServletRequest request, Model model) {
        model.addAttribute("requestDto", new StudentRequestDto());
        model.addAttribute("specialities", specialityService.getAll());
        Optional<String> token = CookieUtils.getJwtFromCookies(request);
        if (token.isEmpty())
            return "redirect:/ui/login";

        List<Role> roles = jwtService.extractRoles(token.get());

        if (!roles.contains(Role.ADMIN))
            return "redirect:/ui/login";

        return "student-registration";
    }

    @PostMapping("/register-student")
    public String register(@ModelAttribute StudentRequestDto requestDo) {
        studentService.create(requestDo);
        return "redirect:/ui/account";
    }
}
