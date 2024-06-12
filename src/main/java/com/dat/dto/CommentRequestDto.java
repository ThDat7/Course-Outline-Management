package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentRequestDto {
    private Integer id;
    @NotNull(message = "{comment.cmt.nullErr}")
    @Size(min = 2, message = "{comment.cmt.lengthErr}")
    private String cmt;
}
