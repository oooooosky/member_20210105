package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당 Entity 클래스 이름, PK타입>
// JpaRepository를 상속받으면 자동으로 Repository라 인식하기 때문에 따로 어노테이션을 지정해 줄 필요가 없다.
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    /*
        pk가 아닌 값을 사용할 때 메서드를 Repository에 메서드를 선언해줘야함.
        메서드만 만들면 JPA가 알아서 작업을 수행해줌.
        이메일을 조건으로 회원 조회 (select * from member_table where member_email=?)
        메서드 리턴타입 : MemberEntity
        메서드 이름 : findByMemberEmail
        메서드 매개변수 : String memberEmail
     */

    MemberEntity findByMemberEmail(String memberEmail);

}
