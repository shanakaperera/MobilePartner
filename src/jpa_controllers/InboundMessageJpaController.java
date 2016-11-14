/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa_controllers;

import db_pojo.InboundMessage;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa_controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author Shanaka
 */
public class InboundMessageJpaController implements Serializable {

    public InboundMessageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InboundMessage inboundMessage) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(inboundMessage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InboundMessage inboundMessage) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            inboundMessage = em.merge(inboundMessage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inboundMessage.getId();
                if (findInboundMessage(id) == null) {
                    throw new NonexistentEntityException("The inboundMessage with id " + id + " no longer exists.");
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
            InboundMessage inboundMessage;
            try {
                inboundMessage = em.getReference(InboundMessage.class, id);
                inboundMessage.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inboundMessage with id " + id + " no longer exists.", enfe);
            }
            em.remove(inboundMessage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InboundMessage> findInboundMessageEntities() {
        return findInboundMessageEntities(true, -1, -1);
    }

    public List<InboundMessage> findInboundMessageEntities(int maxResults, int firstResult) {
        return findInboundMessageEntities(false, maxResults, firstResult);
    }

    private List<InboundMessage> findInboundMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InboundMessage.class));
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

    public InboundMessage findInboundMessage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InboundMessage.class, id);
        } finally {
            em.close();
        }
    }

    public int getInboundMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InboundMessage> rt = cq.from(InboundMessage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
