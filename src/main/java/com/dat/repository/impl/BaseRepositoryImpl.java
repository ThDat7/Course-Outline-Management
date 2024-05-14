package com.dat.repository.impl;

import com.dat.pojo.User;
import com.dat.repository.BaseRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


@Transactional
public abstract class BaseRepositoryImpl<T, K extends Serializable> implements BaseRepository<T, K> {
    private Class<T> tClass;

    public BaseRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        this.factory = factory;
        this.env = env;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.tClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected LocalSessionFactoryBean factory;

    protected Environment env;

    protected abstract K getId(T t);

    abstract protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root);

    public List<T> getAll(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(tClass);
        Root root = q.from(tClass);
        q.select(root);

        if (params != null && params.size() > 0) {
            List<Predicate> predicates = filterByParams(params, b, root);
            if (predicates != null && predicates.size() > 0)
                q.where(predicates.toArray(Predicate[]::new));
        }

        q.orderBy(b.asc(root.get("id")));

        Query query = s.createQuery(q);

        filterPage(params, query);

        return query.getResultList();
    }

    protected void filterPage(Map<String, String> params, Query query) {
        String page = "1";
        if (params != null && params.get("page") != null && !params.get("page").isEmpty())
            page = params.get("page");
        int p = Integer.parseInt(page);
        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));

        query.setMaxResults(pageSize);
        query.setFirstResult((p - 1) * pageSize);
    }

    public Long count(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(tClass);
        Root root = q.from(tClass);
        q.select(b.count(root));

        if (params != null && params.size() > 0) {
            List<Predicate> predicates = filterByParams(params, b, root);
            if (predicates != null && predicates.size() > 0)
                q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = s.createQuery(q);
        return Long.parseLong(query.getSingleResult().toString());
    }

    public boolean addOrUpdate(T t) {
        Session s = factory.getObject().getCurrentSession();
        try {
            if (getId(t) == null)
                s.save(t);
            else s.update(t);

            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public T getById(K id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(tClass, id);
    }

    public void delete(K id) {
        Session s = factory.getObject().getCurrentSession();
        T a = s.get(tClass, id);
        if (a != null)
            s.delete(a);
    }
}
