package webservice.springboot2.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.config.auth.SessionUser;
import webservice.springboot2.test.config.auth.dto.OAuthAttributes;
import webservice.springboot2.test.domain.plansGoles.Plans;
import webservice.springboot2.test.domain.user.User;
import webservice.springboot2.test.domain.user.UserRepository;
import webservice.springboot2.test.web.dto.plansGolesDto.PlansSaveRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class GuestsService {
    private final UserRepository userRepository;
    private final HttpServletRequest servletRequest;
    private final HttpSession httpSession;

    @Transactional
    public long save() {

        SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd");
        Date time = new Date();
        String LoginTime = format.format(time);

        String ClinetIp = getClientIp(servletRequest);

        User user = userRepository.findByIp(ClinetIp)
                .orElse(User.builder()
                        .name("Guest_"+LoginTime)
                        .ip(ClinetIp)
                        .build()
                );

        long id = userRepository.save(user).getId();

        httpSession.setAttribute("user", new SessionUser(user));
        return id;
    }

    private static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        System.out.println("> X-FORWARDED-FOR : " + ip);
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println(">  WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            System.out.println("> getRemoteAddr : "+ip);
        }
        System.out.println("> Result : IP Address : "+ip);

        return ip;
    }
}
