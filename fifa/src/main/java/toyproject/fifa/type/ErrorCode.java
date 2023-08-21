package toyproject.fifa.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("내부 서버 오류입니다."),

    ALREADY_EXIST_USER_ID("이미 존재하면 사용자명입니다."),
    NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER_ID("존재하지 않는 사용자명입니다."),;

    private final String description;
}
