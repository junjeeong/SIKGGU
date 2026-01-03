package dev.junyeong.sikggu.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("회원 정보 업데이트가 정상적으로 수행된다")
    void updateInfo() {
        // given
        User user = User.builder()
            .nickname("original")
            .phoneNumber("010-1234-5678")
            .latitude(37.0)
            .longitude(127.0)
            .build();

        String newNickname = "newNick";
        String newPhone = "010-9876-5432";
        Double newLat = 38.0;
        Double newLon = 128.0;

        // when
        user.updateInfo(newNickname, newPhone, newLat, newLon);

        // then
        assertThat(user.getNickname()).isEqualTo(newNickname);
        assertThat(user.getPhoneNumber()).isEqualTo(newPhone);
        assertThat(user.getLatitude()).isEqualTo(newLat);
        assertThat(user.getLongitude()).isEqualTo(newLon);
    }

    @Test
    @DisplayName("null 값이 들어오면 해당 필드는 업데이트되지 않는다")
    void updateInfoWithNulls() {
        // given
        String originalNick = "original";
        User user = User.builder()
            .nickname(originalNick)
            .phoneNumber("010-1234-5678")
            .latitude(37.0)
            .longitude(127.0)
            .build();

        // when
        user.updateInfo(null, null, null, null);

        // then
        assertThat(user.getNickname()).isEqualTo(originalNick);
    }
}
