/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa_controllers;

import db_pojo.SavedContacts;
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
public class SavedContactsJpaController implements Serializable {

    public SavedContactsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SavedContacts savedContacts) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(savedContacts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SavedContacts savedContacts) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            savedContacts = em.merge(savedContacts);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = savedContacts.getId();
                if (findSavedContacts(id) == null) {
                    throw new NonexistentEntityException("The savedContacts with id " + id + " no longer exists.");
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
            SavedContacts savedContacts;
            try {
                savedContacts = em.getReference(SavedContacts.class, id);
                savedContacts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The savedContacts with id " + id + " no longer exists.", enfe);
            }
            em.remove(savedContacts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SavedContacts> findSavedContactsEntities() {
        return findSavedContactsEntities(true, -1, -1);
    }

    public List<SavedContacts> findSavedContactsEntities(int maxResults, int firstResult) {
        return findSavedContactsEntities(false, maxResults, firstResult);
    }

    private List<SavedContacts> findSavedContactsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SavedContacts.class));
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

    public SavedContacts findSavedContacts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SavedContacts.class, id);
        } finally {
            em.close();
        }
    }

    public SavedContacts findSavedContact(String para, String attrName) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SavedContacts> cq = cb.createQuery(SavedContacts.class);
            Root<SavedContacts> from = cq.from(SavedContacts.class);
            cq.where(cb.equal(from.get(attrName), para));
            Query q = em.createQuery(cq);
            return (SavedContacts) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public int getSavedContactsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SavedContacts> rt = cq.from(SavedContacts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
