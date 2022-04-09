package com.example.tangntalk.web.account.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
public class OnlineAndOfflineFriendListDto {
    private List<FriendQueryDto> online;
    private List<FriendQueryDto> offline;

    public static OnlineAndOfflineFriendListDto fromFriendDtoList(List<FriendDto> friendDtoList){
        return OnlineAndOfflineFriendListDto.builder()
                .online(
                        friendDtoList
                                .stream()
                                .filter(FriendDto::getConnectionStatus)
                                .map(FriendQueryDto::fromFriendDto)
                                .collect(Collectors.toList()))
                .offline(
                        friendDtoList
                                .stream()
                                .filter(friendDto -> !friendDto.getConnectionStatus())
                                .map(FriendQueryDto::fromFriendDto)
                                .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class FriendQueryDto{
        private String username;
        private String name;
        private String statusMessage;
        private String type;
        private String accountLocation;
        private Long chatroomId;

        public static FriendQueryDto fromFriendDto(FriendDto friendDto){
            return FriendQueryDto.builder()
                    .username(friendDto.getUsername())
                    .name(friendDto.getName())
                    .statusMessage(friendDto.getStatusMessage())
                    .type(friendDto.getType())
                    .accountLocation(friendDto.getAccountLocation())
                    .chatroomId(friendDto.getChatroomId())
                    .build();
        }

    }
}
