package com.dat.service.impl;

import com.dat.pojo.Comment;
import com.dat.repository.CommentRepository;
import com.dat.service.CommentService;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;

    @Override
    public void saveOrUpdate(Comment comment, Integer courseOutlineId) {
        if (comment.getId() != null) {
            Comment oldComment = commentRepository.findById(comment.getId());
            if (oldComment == null) {
                throw new RuntimeException("Comment not found");
            }
            if (!oldComment.getUser().getId().equals(userService.getCurrentUser().getId())) {
                throw new RuntimeException("You are not the owner of this comment");
            }
        }

        commentRepository.saveOrUpDate(comment, courseOutlineId);
    }
}
