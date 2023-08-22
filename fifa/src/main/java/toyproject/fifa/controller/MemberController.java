package toyproject.fifa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.fifa.domain.Member;
import toyproject.fifa.dto.SignIn;
import toyproject.fifa.dto.SignUp;
import toyproject.fifa.security.TokenProvider;
import toyproject.fifa.service.MemberService;

@RestController
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

        return ResponseEntity.ok(SignIn.Response.fromDto(member, token));
    }

//    @GetMapping("/fifa/inventory")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<Inventory> getInventory(){
//
//    }
}
