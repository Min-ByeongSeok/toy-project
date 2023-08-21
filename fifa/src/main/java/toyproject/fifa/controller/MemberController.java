package toyproject.fifa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import toyproject.fifa.domain.Member;
import toyproject.fifa.dto.SignIn;
import toyproject.fifa.dto.SignUp;
import toyproject.fifa.security.TokenProvider;
import toyproject.fifa.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/fifa/signup")
    public ResponseEntity<SignUp.Response> signup(@RequestBody SignUp.Request request) {
        return ResponseEntity.ok(SignUp.Response.fromDto(memberService.signup(request)));
    }

    @PostMapping("/fifa/signin")
    public ResponseEntity<SignIn.Response> signin(@RequestBody SignIn.Request request) {
        Member member = memberService.signin(request);

        String token = tokenProvider.generateToken(member.getUserId(), member.getRoles());

        return ResponseEntity.ok(
                SignIn.Response.builder()
                .token(token)
                .build());
    }
}
