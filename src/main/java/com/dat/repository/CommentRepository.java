package com.dat.repository;

import com.dat.pojo.Comment;

public interface CommentRepository {

    void saveOrUpDate(Comment comment, Integer courseOutlineId);
}
