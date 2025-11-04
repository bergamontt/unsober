package ua.unsober.backend.ui;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.utils.cookies.CookieUtils;
import ua.unsober.backend.feature.admin.AdminResponseDto;
import ua.unsober.backend.feature.admin.AdminService;
import ua.unsober.backend.feature.auth.JwtTokenService;
import ua.unsober.backend.feature.student.StudentResponseDto;
import ua.unsober.backend.feature.student.StudentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TemplateAccountController {
    private final JwtTokenService jwtService;
    private final StudentService studentService;
    private final AdminService adminService;

    @GetMapping("/account")
    public String accountPage(HttpServletRequest request, Model model) {
        Optional<String> token = CookieUtils.getJwtFromCookies(request);
        if (token.isEmpty())
            return "redirect:/login";

        List<Role> roles = jwtService.extractRoles(token.get());
        String email = jwtService.extractSubject(token.get());

        if (roles.contains(Role.STUDENT)) {
            StudentResponseDto student = studentService.getByEmail(email);
            model.addAttribute("accountType", "STUDENT");
            model.addAttribute("student", student);
            return "account";
        } else if (roles.contains(Role.ADMIN)) {
            AdminResponseDto admin = adminService.getByEmail(email);
            model.addAttribute("accountType", "ADMIN");
            model.addAttribute("admin", admin);
            return "account";
        } else {
            return "redirect:/login";
        }
    }
}
