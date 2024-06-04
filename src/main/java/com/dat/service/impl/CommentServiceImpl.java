package com.dat.service.impl;

import com.dat.pojo.Comment;
import com.dat.repository.CommentRepository;
import com.dat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveOrUpdate(Comment comment, Integer courseOutlineId) {
//        if update, check current user is the owner of the comment
        commentRepository.saveOrUpDate(comment, courseOutlineId);
    }
}
