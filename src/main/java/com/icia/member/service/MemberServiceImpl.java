package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    // 생성자 주입
    private final MemberRepository mr;

    // 회원정보 저장
    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {

        /*
            1. memberSaveDTO -> memberEntity에 옮기기
            2. memberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달
         */
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        // 정보를 저장함과 동시에 값을 가져올 수 있음.

        // 사용자가 입력한 이메일 중복체크
        MemberEntity emailCheckResult = mr.findByMemberEmail(memberSaveDTO.getMemberEmail());
        // 이메일 중복체크 결과가  null이 아니라면 예외를 발생시킴.
        // 예외종류 : IllegalStateException, 예외메세지 : 중복된 이메일입니다
        if (emailCheckResult != null) {
            throw new IllegalStateException("중복된 이메일입니다");
        }

        return mr.save(memberEntity).getId();

    }

    // 회원 상세 조회
    @Override
    public MemberDetailDTO findById(Long memberId) {
        /*
            1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴.
            2. MemberEntity를 MemberDetailDTO로 바꿔서 컨트롤러로 리턴.
         */

        // 1.
        // .get : Optional을 한번 까주는 역할
        MemberEntity member = mr.findById(memberId).get();

        // 2.
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(member);
        System.out.println("memberDetailDTO.toString() = " + memberDetailDTO.toString());

        return memberDetailDTO;
    }

    // 회원 이메일(아이디) 중복체크
    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        // 1. 사용자가 입력한 이메일을 조건으로 DB에서 조회 (select * from member_table where member_email=?)
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        // 2. 비밀번호 일치여부 확인 (Entity에서 조회한 결과의 비밀번호와 입력받은 비밀번호를 비교)
        if (memberEntity != null) {
            if (memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 회원목록 요청
    @Override
    public List<MemberDetailDTO> findAll() {
        // jpa에서 제공하는 findAll이 있다.
        List<MemberEntity> memberEntityList = mr.findAll();
        // 그냥 쓸 수 도 있지만 DTO로 변환
        // List<MemberEntity> -> List<MemberDetailDTO>
        List<MemberDetailDTO> memberList = MemberDetailDTO.change(memberEntityList);
        return memberList;
    }

}
