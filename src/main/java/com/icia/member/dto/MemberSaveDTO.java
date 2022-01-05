package com.icia.member.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MemberSaveDTO {

    @NotBlank(message = "이메일은 필수입니다.")
    private String memberEmail;
    @NotBlank
    private String memberPassword;
    private String memberName;

}
