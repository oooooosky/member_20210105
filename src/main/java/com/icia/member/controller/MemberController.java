package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final 키워드가 붙은 필드만으로 생성자를 만들어줌.
public class MemberController {

    private final MemberService ms;

    @GetMapping("save")
    public String saveForm(Model model) {
        // 아래 코드는 thyleaf이기 때문에 할 수 있는 기능을 쓰기 위한 사전 작업
        model.addAttribute("member", new MemberSaveDTO());
        return "member/save";
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("login", new MemberLoginDTO());
        return "member/login";
    }

    @PostMapping("save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult bindingResult) {
        System.out.println("MemberController.save"); // soutm
        System.out.println("memberSaveDTO = " + memberSaveDTO); // soutp

        if (bindingResult.hasErrors()) {
            return "member/save";
        }

        ms.save(memberSaveDTO);

        return "redirect:/member/login";
    }

    @PostMapping("login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "member/login";
        }
        return "redirect:/member/findAll";
    }

    // 상세조회
    // /member/2, /member/15 => /member/{memberId}
    // @PathVariavle : 경로상에 있는 변수를 가져올 때 사용
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {
        System.out.println("memberId = " + memberId);
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member",member);
        return "member/detail";
    }

}
