package tukorea.projectlink.global;

import lombok.RequiredArgsConstructor;

import java.util.Random;
@RequiredArgsConstructor
public class PasswordUtil {

    public static String getRandomPassword(){
        Random random = new Random();
        StringBuffer randomBuf = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            // Random.nextBoolean() : 랜덤으로 true, false 리턴 (true : 랜덤 소문자 영어, false : 랜덤 숫자)
            if (random.nextBoolean()) {
                // 26 : a-z 알파벳 개수
                // 97 : letter 'a' 아스키코드
                // (int)(random.nextInt(26)) + 97 : 랜덤 소문자 아스키코드
                randomBuf.append((char)((int)(random.nextInt(26)) + 97));
            } else {
                randomBuf.append(random.nextInt(10));
            }
        }
        return randomBuf.toString();
    }
}
