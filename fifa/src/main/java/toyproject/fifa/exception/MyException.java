package toyproject.fifa.exception;

import lombok.*;
import toyproject.fifa.type.ErrorCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;


    public MyException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
