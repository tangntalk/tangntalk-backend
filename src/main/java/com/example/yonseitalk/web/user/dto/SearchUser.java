package com.example.yonseitalk.web.user.dto;

import com.example.yonseitalk.web.user.dto.projection.SearchUserProjection;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchUser{
    private String userId;
    private String name;
    private String statusMessage;
    private String type;
    private Boolean isFriend;

    public static SearchUser fromProjection(SearchUserProjection searchUserProjection){
        return SearchUser.builder()
                .userId(searchUserProjection.getUserId())
                .name(searchUserProjection.getName())
                .statusMessage(searchUserProjection.getStatusMessage())
                .type(searchUserProjection.getType())
                .isFriend(searchUserProjection.getIsFriend() != null).build();
    }
}
