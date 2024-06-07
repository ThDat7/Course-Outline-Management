package com.dat.repository;

import com.dat.pojo.Comment;

import java.util.Optional;

public interface CommentRepository {

    void saveOrUpDate(Comment comment, Integer courseOutlineId);

    Comment findById(Integer id);
}
