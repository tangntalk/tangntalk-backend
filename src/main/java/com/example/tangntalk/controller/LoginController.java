package com.example.tangntalk.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.util.login.service.LoginService;
import com.example.tangntalk.web.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin("*")
@RequestMapping("")
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

//    @GetMapping("/")
//    @ResponseBody
//    public void loginEnter(HttpServletRequest request, HttpServletResponse response) throws IOException{
//        // 로그인 들어오는것
//        //프론트에서 알아서 해준다.
//        //기존에 로그인이 되어 있으면 바로 해당 유저의 공간으로 리디렉션해줌
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Object user = session.getAttribute("loginUser");
//            Account loginUser= (Account)user;
//            String redirect="/"+loginUser.getUsername();
//            response.sendRedirect(redirect);
//        }
//    }
    @PostMapping("/login")
    public Response.Item<AccountDto.Response.Login> login(@RequestBody AccountDto.Request.Login loginRequest){

        return new Response.Item<>(loginService.login(loginRequest));

    }

//    @PostMapping("/{username}/logout")
    @PostMapping("/logout")
    public Response.Empty logout(@PathVariable("username") String username){

        loginService.updateConnectionFalse(username);
        return new Response.Empty();

    }

}
