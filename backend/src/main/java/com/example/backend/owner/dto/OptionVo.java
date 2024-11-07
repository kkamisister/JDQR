package com.example.backend.owner.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OptionVo {
    private Integer optionId;
    private String optionName;
    private Integer maxChoiceCount;
    private Boolean mandatory;
    private Integer choiceId;
    private String choiceName;
    private Integer price;
}
