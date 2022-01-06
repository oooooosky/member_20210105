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

import javax.servlet.http.HttpSession;
import java.util.List;

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

        try {
            ms.save(memberSaveDTO);
        } catch (IllegalStateException e) {
            // e.getMessage()에는 서비스에서 지정한 예외 메세지가 담겨있음.
            bindingResult.reject("emailCheck", e.getMessage());
            // 오류메세지로 데이터를 보내기 위해선 return값을 가지고 있어야함.
            return "member/save";
        }

        return "redirect:/member/login";
    }

    @PostMapping("login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors()) {
            return "member/login";
        }

//        boolean loginResult = ms.login(memberLoginDTO);
//        if (loginResult) {
//        return이 boolean타입이기 때문에 굳이 변수에 담을 필요가 없음.

        if (ms.login(memberLoginDTO)) {
            session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            return "redirect:/member/findAll";
        } else {
            bindingResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다");
            return "member/login";
        }

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

    // 목록출력 (/member) 따라서 아래에 value를 지정할 필요가 없음.
    @GetMapping
    public String findAll(Model model) {
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute(memberList);
        return "member/findAll";
    }

}
