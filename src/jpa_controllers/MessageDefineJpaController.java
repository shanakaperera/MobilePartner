/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa_controllers;

import db_pojo.MessageDefine;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa_controllers.exceptions.NonexistentEntityException;


/**
 *
 * @author Shanaka
 */
public class MessageDefineJpaController implements Serializable {

    public MessageDefineJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MessageDefine messageDefine) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(messageDefine);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MessageDefine messageDefine) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            messageDefine = em.merge(messageDefine);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = messageDefine.getId();
                if (findMessageDefine(id) == null) {
                    throw new NonexistentEntityException("The messageDefine with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MessageDefine messageDefine;
            try {
                messageDefine = em.getReference(MessageDefine.class, id);
                messageDefine.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The messageDefine with id " + id + " no longer exists.", enfe);
            }
            em.remove(messageDefine);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MessageDefine> findMessageDefineEntities() {
        return findMessageDefineEntities(true, -1, -1);
    }

    public List<MessageDefine> findMessageDefineEntities(int maxResults, int firstResult) {
        return findMessageDefineEntities(false, maxResults, firstResult);
    }

    private List<MessageDefine> findMessageDefineEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MessageDefine.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MessageDefine findMessageDefine(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MessageDefine.class, id);
        } finally {
            em.close();
        }
    }

    public MessageDefine findMessageDefine(String para, String attrName) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<MessageDefine> cq = cb.createQuery(MessageDefine.class);
            Root<MessageDefine> from = cq.from(MessageDefine.class);
            cq.where(cb.equal(from.get(attrName), para));
            Query q = em.createQuery(cq);
            q.getSingleResult();
            return (MessageDefine) q.getSingleResult();
        } catch (NoResultException ex) {
          //  System.out.println(ex);
            return null;
        } finally {
            em.close();
        }
    }

    public int getMessageDefineCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MessageDefine> rt = cq.from(MessageDefine.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
