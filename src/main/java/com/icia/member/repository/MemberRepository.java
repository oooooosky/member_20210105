package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당 Entity 클래스 이름, PK타입>
// JpaRepository를 상속받으면 자동으로 Repository라 인식하기 때문에 따로 어노테이션을 지정해 줄 필요가 없다.
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}
