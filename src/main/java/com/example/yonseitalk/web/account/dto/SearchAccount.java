package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.dto.projection.SearchAccountProjection;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchAccount {
    private String accountId;
    private String name;
    private String statusMessage;
    private String type;
    private Boolean isFriend;

    public static SearchAccount fromProjection(SearchAccountProjection searchAccountProjection){
        return SearchAccount.builder()
                .accountId(searchAccountProjection.getAccountId())
                .name(searchAccountProjection.getName())
                .statusMessage(searchAccountProjection.getStatusMessage())
                .type(searchAccountProjection.getType())
                .isFriend(searchAccountProjection.getIsFriend() != null).build();
    }
}
