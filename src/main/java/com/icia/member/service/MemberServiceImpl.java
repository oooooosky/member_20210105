package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {

        /*
            1. memberSaveDTO -> memberEntity에 옮기기
            2. memberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달
         */
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        // 정보를 저장함과 동시에 값을 가져올 수 있음.
        return mr.save(memberEntity).getId();

    }

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

}
