package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import com.icia.member.service.MemberService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberDetailDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity member) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(member.getId());
        memberDetailDTO.setMemberEmail(member.getMemberEmail());
        memberDetailDTO.setMemberPassword(member.getMemberPassword());
        memberDetailDTO.setMemberName(member.getMemberName());
        return memberDetailDTO;
    }

    public static List<MemberDetailDTO> change(List<MemberEntity> memberEntityList) {
        List<MemberDetailDTO> memberList = new ArrayList<>();
        for(MemberEntity m: memberEntityList) {

//            for(int i = 0; i<memberEntityList.size(); i++) {
//            memberDetailDTO.setMemberId(memberEntityList.get(i).getId());
//            memberDetailDTO.setMemberEmail(memberEntityList.get(i).getMemberEmail());
//            memberDetailDTO.setMemberPassword(memberEntityList.get(i).getMemberPassword());
//            memberDetailDTO.setMemberName(memberEntityList.get(i).getMemberName());
//            memberList.add(memberDetailDTO);

            // Entity 객체를 MemberDetailDTO로 변환하고 memberList에 담음.
            memberList.add(toMemberDetailDTO(m));
        };
        return memberList;
    }

}
