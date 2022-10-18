/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atimmovies.Atimmovies;

import atimmovies.Atimmovies.exceptions.IllegalOrphanException;
import atimmovies.Atimmovies.exceptions.NonexistentEntityException;
import atimmovies.Atimmovies.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author OS
 */
public class DvdJpaController implements Serializable {

    public DvdJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("atimmovies_Atimmovies_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dvd dvd) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi = dvd.getTransaksi();
            if (transaksi != null) {
                transaksi = em.getReference(transaksi.getClass(), transaksi.getIdtransaksi());
                dvd.setTransaksi(transaksi);
            }
            em.persist(dvd);
            if (transaksi != null) {
                Dvd oldDvdOfTransaksi = transaksi.getDvd();
                if (oldDvdOfTransaksi != null) {
                    oldDvdOfTransaksi.setTransaksi(null);
                    oldDvdOfTransaksi = em.merge(oldDvdOfTransaksi);
                }
                transaksi.setDvd(dvd);
                transaksi = em.merge(transaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDvd(dvd.getIdCD()) != null) {
                throw new PreexistingEntityException("Dvd " + dvd + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dvd dvd) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd persistentDvd = em.find(Dvd.class, dvd.getIdCD());
            Transaksi transaksiOld = persistentDvd.getTransaksi();
            Transaksi transaksiNew = dvd.getTransaksi();
            List<String> illegalOrphanMessages = null;
            if (transaksiOld != null && !transaksiOld.equals(transaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Transaksi " + transaksiOld + " since its dvd field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transaksiNew != null) {
                transaksiNew = em.getReference(transaksiNew.getClass(), transaksiNew.getIdtransaksi());
                dvd.setTransaksi(transaksiNew);
            }
            dvd = em.merge(dvd);
            if (transaksiNew != null && !transaksiNew.equals(transaksiOld)) {
                Dvd oldDvdOfTransaksi = transaksiNew.getDvd();
                if (oldDvdOfTransaksi != null) {
                    oldDvdOfTransaksi.setTransaksi(null);
                    oldDvdOfTransaksi = em.merge(oldDvdOfTransaksi);
                }
                transaksiNew.setDvd(dvd);
                transaksiNew = em.merge(transaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = dvd.getIdCD();
                if (findDvd(id) == null) {
                    throw new NonexistentEntityException("The dvd with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd dvd;
            try {
                dvd = em.getReference(Dvd.class, id);
                dvd.getIdCD();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dvd with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Transaksi transaksiOrphanCheck = dvd.getTransaksi();
            if (transaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dvd (" + dvd + ") cannot be destroyed since the Transaksi " + transaksiOrphanCheck + " in its transaksi field has a non-nullable dvd field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(dvd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dvd> findDvdEntities() {
        return findDvdEntities(true, -1, -1);
    }

    public List<Dvd> findDvdEntities(int maxResults, int firstResult) {
        return findDvdEntities(false, maxResults, firstResult);
    }

    private List<Dvd> findDvdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dvd.class));
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

    public Dvd findDvd(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dvd.class, id);
        } finally {
            em.close();
        }
    }

    public int getDvdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dvd> rt = cq.from(Dvd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
