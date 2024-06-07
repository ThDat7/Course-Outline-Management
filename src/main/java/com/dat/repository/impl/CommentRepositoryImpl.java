package com.dat.repository.impl;

import com.dat.pojo.Comment;
import com.dat.pojo.CourseOutline;
import com.dat.pojo.User;
import com.dat.repository.CommentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void saveOrUpDate(Comment comment, Integer courseOutlineId) {
        Session s = factory.getObject().getCurrentSession();
        if (comment.getId() == null) {
            Integer userId = 1;
            comment.setUser(new User(userId));
            comment.setCourseOutline(new CourseOutline(courseOutlineId));
            comment.setTime(LocalTime.now());
            s.save(comment);
        } else {
            s.createQuery("UPDATE Comment c SET c.cmt = :comment " +
                            "WHERE c.id = :id AND c.courseOutline.id = :courseOutlineId")
                    .setParameter("comment", comment.getCmt())
                    .setParameter("id", comment.getId())
                    .setParameter("courseOutlineId", courseOutlineId)
                    .executeUpdate();
        }
    }

    @Override
    public Comment findById(Integer id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Comment.class, id);
    }
}
