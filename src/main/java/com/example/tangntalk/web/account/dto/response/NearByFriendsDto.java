package com.example.tangntalk.web.account.dto.response;

import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.dto.projection.NearByAccountProjection;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NearByFriendsDto {
    List<NearByAccount> online;
    List<NearByAccount> offline;

    public NearByFriendsDto(List<NearByAccountProjection> projectionList) {
        online = new ArrayList<>();
        offline = new ArrayList<>();
        projectionList.forEach(this::groupFriend);
    }

    public void groupFriend(NearByAccountProjection projection) {
        if (projection.getConnectionStatus()) {
            online.add(NearByAccount.fromProjection(projection));
            return;
        }
        offline.add(NearByAccount.fromProjection(projection));
    }

    @Builder
    @Data
    public static class NearByAccount {
        private String username;
        private String name;
        private String statusMessage;
        private String type;
        private String accountLocation;
        private Long chatroomId;

        public static NearByAccount fromProjection(NearByAccountProjection projection) {
            return NearByAccount.builder()
                    .username(projection.getUsername())
                    .name(projection.getName())
                    .statusMessage(projection.getStatusMessage())
                    .type(projection.getType())
                    .accountLocation(projection.getAccountLocation())
                    .chatroomId(projection.getChatroomId())
                    .build();
        }
    }
}