package tukorea.projectlink.global.errorcode;

import com.fasterxml.jackson.annotation.JsonFormat;
import tukorea.projectlink.global.common.CommonError;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BoardErrorCode implements CommonError {
    BOARD_ID_INVALID("BOARD-001", "작업을 수행하고자 하는 BOARD_ID가 없습니다.");

    private final String code;
    private final String description;


    BoardErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
