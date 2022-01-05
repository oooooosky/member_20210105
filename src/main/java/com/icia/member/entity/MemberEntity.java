package com.icia.member.entity;

import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    // Entity Class를 사용할 때 반드시 primary key가 있어야함.

    // @Id : pk를 지정할 때 사용하는 어노테이션
    // @GeneratedValue(strategy = GenerationType.IDENTITY) : auto_increment
    // @Column(name = "") : 별도 컬럼명을 지정할 때
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // memberEmail : 크기50, unique
    @Column(length = 50, unique = true)
    private String memberEmail;

    // memberPassword : 크기20
    @Column(length = 20)
    private String memberPassword;

    // Column을 생략하면 default 크기가 255로 지정됨
    private String memberName;

    // 카멜케이스로 작성하면 DB에 member_email로 자동 변경됨.
    // 이것이 싫다면 소문자로만 작성해야함.

    /*
        DTO 클래스 객체를 전달받아 Entity 클래스 필드값으로 세팅하고
        Entity 객체를 리턴하는 메서드 선언

        static 메서드(정적 메서드) : 클래스 메서드, 객체를 만들지 않고도 바로 호출 가능
     */
    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;
    }

}
