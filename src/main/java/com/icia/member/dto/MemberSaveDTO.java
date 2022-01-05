package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
@NoArgsConstructor  // 기본 생성자
public class MemberSaveDTO {

    @NotBlank(message = "이메일은 필수입니다.")
    private String memberEmail;
    @NotBlank
    private String memberPassword;
    private String memberName;

}
