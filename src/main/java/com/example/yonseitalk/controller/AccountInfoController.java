package com.example.yonseitalk.controller;

import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.account.dto.AccountDtoMerged;
import com.example.yonseitalk.web.account.dto.AccountInfoQueryResponse;
import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.dto.nearbyAccount;
import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccountInfoController {

    private final AccountService accountService;
    private final ChatroomRepository chatroomRepository;

    @GetMapping(value = "/{user_id}")
    public Response.Item<AccountInfoQueryResponse> accountInfo(@PathVariable("user_id") String accountId){
        return new Response.Item<>(accountService.accountInfoQuery(accountId));
    }

    @PatchMapping(value = "/{user_id}")
    public Response.Empty modifyInformation(@PathVariable("user_id") String accountId,
                                                    @RequestBody AccountDtoMerged.Request.ModifyInfo modifyInfo){
        accountService.modifyInformation(accountId ,modifyInfo);
        return new Response.Empty();
    }

    @DeleteMapping(value = "/{user_id}")
    public Response.Empty deleteUser(@PathVariable("user_id") String userId){
        accountService.deleteById(userId);
        return new Response.Empty();
    }

    //nearby
    @GetMapping(value = "/{user_id}/nearby/{target_location}")
    public ResponseEntity<?> nearbyUser(@PathVariable("user_id") String userId,@PathVariable("target_location") String target_location){

        Map<String, Object> response = new HashMap<>();

        Optional<AccountDto> userDto = accountService.findById(userId);


        if(!userDto.isPresent()) {
            response.put("success", false);
            response.put("code", new NotFoundException());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        String location=userDto.get().getAccountLocation();
        response.put("success",true);
        response.put("myplace",location);
        //추가하기

        ArrayList<nearbyAccount> onlineUser = new ArrayList<>();
        ArrayList<nearbyAccount> offlineUser =new ArrayList<>();

        //ArrayList<User>
        List<AccountDto> nearbyPeople = accountService.findByLocation(target_location);

        for(AccountDto user2: nearbyPeople){
            if(user2.getAccountId().equals(userDto.get().getAccountId())){
                continue;
            }
            if(user2.getConnectionStatus()){
                //connection
                nearbyAccount onlineNearByAccount = new nearbyAccount();
                onlineNearByAccount.setAccountId(user2.getAccountId());
                onlineNearByAccount.setName(user2.getName());
                onlineNearByAccount.setType(user2.getType());
                onlineNearByAccount.setStatusMessage(user2.getStatusMessage());
                //chatroom 추가
                Optional<Chatroom> chatroom =chatroomRepository.findByPairUser(userDto.get().getAccountId(),user2.getAccountId());

                if (chatroom.isPresent()){
                    onlineNearByAccount.setChatroomId(chatroom.get().getChatroomId());
                }

                onlineUser.add(onlineNearByAccount);
            }
            else{//not connection
                nearbyAccount offlineNearByAccount = new nearbyAccount();
                offlineNearByAccount.setAccountId(user2.getAccountId());
                offlineNearByAccount.setName(user2.getName());
                offlineNearByAccount.setType(user2.getType());
                offlineNearByAccount.setStatusMessage(user2.getStatusMessage());

                // chatroom
                Optional<Chatroom> chatroom =chatroomRepository.findByPairUser(userDto.get().getAccountId(),user2.getAccountId());

                if (chatroom.isPresent()){
                    offlineNearByAccount.setChatroomId(chatroom.get().getChatroomId());
                }


                offlineUser.add(offlineNearByAccount);
            }

        }
        response.put("online",onlineUser);
        response.put("offline",offlineUser);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
