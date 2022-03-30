package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.dto.projection.FriendAccountProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
public class FriendQueryResponse {
    private List<FriendQueryDto> online;
    private List<FriendQueryDto> offline;

    public static FriendQueryResponse fromFriendDtoList(List<FriendDto> friendDtoList){
        return FriendQueryResponse.builder()
                .online(
                        friendDtoList
                                .stream()
                                .filter(FriendDto::getConnectionStatus)
                                .map(FriendQueryDto::fromFriendDto)
                                .collect(Collectors.toList()))
                .offline(
                        friendDtoList
                                .stream()
                                .filter(friendDto -> {
                                    return  !friendDto.getConnectionStatus();
                                })
                                .map(FriendQueryDto::fromFriendDto)
                                .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class FriendQueryDto{
        private String accountId;
        private String name;
        private String statusMessage;
        private String type;
        private String accountLocation;
        private Long chatroomId;

        public static FriendQueryDto fromFriendDto(FriendDto friendDto){
            return FriendQueryDto.builder()
                    .accountId(friendDto.getAccountId())
                    .name(friendDto.getName())
                    .statusMessage(friendDto.getStatusMessage())
                    .type(friendDto.getType())
                    .accountLocation(friendDto.getAccountLocation())
                    .chatroomId(friendDto.getChatroomId())
                    .build();
        }

    }
}
