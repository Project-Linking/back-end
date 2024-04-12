package tukorea.projectlink.global.common;

import lombok.Getter;

/**
 * Controller 공통 응답 포맷
 * @param <ResponseData>
 *     ResponseData을 제네릭 타입으로 지정해 모든 응답 객체를 매핑한 뒤 응답
 */
@Getter
public class CommonResponse<ResponseData> {

    private final boolean success;
    private final ResponseData data;
    private final CommonError errorStatus;

    /**
     * 데이터를 포함하지 않는 성공 응답
     * success 를 제외한 필드는 null로 응답
     */
    public static CommonResponse<?> successWithEmptyData(){
        return new CommonResponse<>(true,null,null);
    }


    /**
     * 데이터를 포함하는 성공 응답
     */
    public static <ResponseData> CommonResponse<ResponseData> successWithData(ResponseData responseData){
        return new CommonResponse<>(true, responseData,null);
    }

    /**
     * 에러 코드를 포함하는 실패 응답
     */
    public static CommonResponse<?> failureWithErrorCode(CommonError commonError){
        return new CommonResponse<>(false,null,commonError);
    }

    /**
     * 외부에서 단독 생성 불가
     */
    private CommonResponse(boolean success, ResponseData responseData, CommonError commonErrorcode) {
        this.success = success;
        this.data = responseData;
        this.errorStatus = commonErrorcode;
    }
}
