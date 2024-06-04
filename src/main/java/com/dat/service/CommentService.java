package com.dat.service;

import com.dat.pojo.Comment;

public interface CommentService {
    void saveOrUpdate(Comment comment, Integer courseOutlineId);
}
