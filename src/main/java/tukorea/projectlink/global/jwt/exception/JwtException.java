package tukorea.projectlink.global.jwt.exception;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException{
    private final JwtErrorCode jwtErrorCode;

    public JwtException(JwtErrorCode jwtErrorCode){
        this.jwtErrorCode = jwtErrorCode;
    }
}
