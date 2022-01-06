package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    @Transactional  // 테스트 시작할 때 새로운 트랜잭션 시작
    @Rollback   // 테스트 종료 후 롤백 수행
    @DisplayName("회원조회 테스트")
    public void memberDetailTest() {
        // given : 테스트 조건 설정
            // 1. 새로운 회원을 등록하고 해당 회원의 번호(member_id)를 가져옴.
        // 1.1 테스트용 데이터 객체 생성
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("조회용회원이메일", "조회용회원비번", "조회용회원이름");
        // 1.2 테스트용 데이터를 DB에 저장하고, member_id를 가져옴.
        Long memberId = ms.save(memberSaveDTO);
        // when : 테스트 수행
            // 2. 위에서 가져온 회원번호를 가지고 조회기능 수행
        MemberDetailDTO findMember = ms.findById(memberId);
        // then : 테스트 결과 검증
            // 3. 1번에서 가입한 회원의 정보와 2번에서 조회한 회원의 정보가 일치하면 테스트 통과 일치하지 않으면 테스트 실패
        // memberSaveDTO의 이메일값과 findMember의 이메일 값이 일치하는지 확인.
        assertThat(memberSaveDTO.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
        // 좌변과 우변이 같은지 비교해주는 코드
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("로그인 테스트")
    public void loginTest() {
        /*
            1. 테스트용 회원가입(MemberSaveDTO)
            2. 로그인용 객체 생성(MemberLoginDTO)
            1.2 수행할 때 동일한 이메일, 패스워드를 사용하도록 함.
            3. 로그인 수행
         */
        // given
            // 1. 테스트용 데이터 객체 생성
//        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("로그인용이메일", "로그인용비밀번호", "로그인용이름");
//            // 2. 테스트용 데이터를 DB에 저장하고, member_id를 가져옴.
//        Long memberId = ms.save(memberSaveDTO);
//            // 3. 로그인용 객체 생성 (DB값과 비교용으로 사용)
//        MemberLoginDTO memberLoginDTO = new MemberLoginDTO("로그인용이메일", "로그인용비밀번호");
//        // when
//            // 1. 위에서 가져온 회원번호를 가지고 회원정보 조회
//        MemberDetailDTO findMember = ms.findById(memberId);
//        // then
//            // 로그인용 객체에 담긴 정보와 조회한 회원정보를 가지고 비교
//        assertThat(findMember.getMemberEmail()).isEqualTo(memberLoginDTO.getMemberEmail());
//        assertThat(findMember.getMemberPassword()).isEqualTo(memberLoginDTO.getMemberPassword());

        // 선생님 방법
        // given
        final String testMemberEmail = "로그인테스트이메일";
        final String testMemberPassword = "로그인테스트비밀번호";
        final String testMemberName = "로그인테스트이름";
        // 1.
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(testMemberEmail, testMemberPassword, testMemberName);
        ms.save(memberSaveDTO);
        // when
        // 2.
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO(testMemberEmail, testMemberPassword);
        boolean loginResult = ms.login(memberLoginDTO);
        // then
        assertThat(loginResult).isEqualTo(true);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원목록 테스트")
    public void memberListTest() {

        /*
            member_table에 아무 데이터가 없는 상태에서
            3명의 회원을 가입시킨 후 memberList 사이즈를 조회하여 3이면 테스트 통과
         */

        // 1. 3명의 회원을 가입
//        for (int i=1; i<=3; i++) {
//            MemberSaveDTO memberSaveDTO = new MemberSaveDTO("조회이메일"+i, "조회비밀번호"+i, "조회이름"+i);
//            ms.save(memberSaveDTO);
//            // 위 코드를 아래 한줄로도 가능
//            ms.save(new MemberSaveDTO("조회이메일"+i, "조회비밀번호"+i, "조회이름"+i));
//        }

        // 이런 방식도 존재.
        // IntStream 방식, -> : Arrow Function(화살표 함수)
        // IntStream : 숫자를 일정 간격으로 만들어줌. 1,2,3 ....
        // rangeClosed : 시작과 끝값을 지정해줌. 1부터 3까지 만들어주라.
        // forEach부분 : for문과 동일
        IntStream.rangeClosed(1,3).forEach(i -> {
            ms.save(new MemberSaveDTO("조회이메일"+i, "조회비밀번호"+i, "조회이름"+i));
        });

        // 2. 목록 요청 및 목록 비교
//        List<MemberDetailDTO> memberList =  ms.findAll();

        // 3. 목록 비교
//        assertThat(memberList.size()).isEqualTo(3);

        // 위 2, 3 번을 한줄로 표현
        assertThat(ms.findAll().size()).isEqualTo(3);

    }
}
