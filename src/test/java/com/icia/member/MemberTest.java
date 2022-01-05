package com.icia.member;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// 테스트코드에는 @SpringBootTest를 붙인다.
@SpringBootTest
public class MemberTest {

    /*
        테스트코드의 주 목적
        코드가 잘 동작 되는지 먼저 확인해보는 곳

        MemberServiceImpl.save() 메서드가 잘 동작하는지를 테스트

        회원가입 테스트
        save.html 에서 회원정보 입력 후 가입 클릭
        DB확인

        TEST코드 실행 방법 : 테스트 메서드 옆 녹색 화살표 클릭 및 Run 돌리기
        녹색 화살표가 뜨는지 확인 및 데이터베이스에 데이터가 들어갔는지 확인.
     */

    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("회원가입 테스트")
    public void memberSaveTest() {
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트회원이메일1");
        memberSaveDTO.setMemberPassword("테스트회원비번1");
        memberSaveDTO.setMemberName("테스트회원이름1");

        ms.save(memberSaveDTO);

    }

}
