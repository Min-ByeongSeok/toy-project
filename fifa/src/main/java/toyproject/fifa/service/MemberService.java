package toyproject.fifa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.fifa.domain.Info;
import toyproject.fifa.domain.Inventory;
import toyproject.fifa.domain.Member;
import toyproject.fifa.dto.SignIn;
import toyproject.fifa.dto.SignUp;
import toyproject.fifa.exception.MyException;
import toyproject.fifa.repository.MemberRepository;
import toyproject.fifa.type.Authority;
import toyproject.fifa.type.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Member signup(SignUp.Request request) {
        boolean exists = memberRepository.existsByUserId(request.getUserId());

        if (exists) {
            throw new MyException(ErrorCode.ALREADY_EXIST_USER_ID);
        }

        // TODO USER권한을 request으로 받지않고 내부적으로 처리하기
        return memberRepository.save(
                Member.builder()
                        .name(request.getName())
                        .userId(request.getUserId())
                        .password(passwordEncoder.encode(request.getPassword()))
//                        .password(request.getPassword())
                        .info(Info.builder()
                                .inventory(Inventory.builder()
                                        // TODO 지급되는 아이템
                                        .build())
                                .build())
                        .build());
    }

    public Member signin(SignIn.Request request) {
        Member member = memberRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new MyException(ErrorCode.NOT_FOUND_USER_ID));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MyException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUserId(username).orElseThrow(
                () -> new MyException(ErrorCode.NOT_FOUND_USER_ID)
        );
    }
}
