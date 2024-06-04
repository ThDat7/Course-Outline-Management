package com.dat.controllers;

import com.dat.dto.CommentRequestDto;
import com.dat.pojo.Comment;
import com.dat.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class ApiCommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/{courseOutlineId}")
    public void newComment(@PathVariable Integer courseOutlineId, @RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = dto2Entity(commentRequestDto);
        commentService.saveOrUpdate(comment, courseOutlineId);
    }

    private Comment dto2Entity(CommentRequestDto commentRequestDto) {
        return modelMapper.map(commentRequestDto, Comment.class);
    }


}
