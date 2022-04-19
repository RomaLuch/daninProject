package ru.itmo.kotiki.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.util.HibernateSessionFactoryUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CatDaoImpl implements CatDao {
    @Override
    public Cat findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Cat cat = session.get(Cat.class, id);
        session.close();
        return cat;
    }

    @Override
    public void save(Cat cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cat);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Cat cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(cat);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Cat cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(cat);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Cat> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Cat> cr = cb.createQuery(Cat.class);
        Root<Cat> root = cr.from(Cat.class);
        cr.select(root);
        Query<Cat> query = session.createQuery(cr);
        List<Cat> results = query.getResultList();
        session.close();
        return results;
    }
}
