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
public class TransaksiJpaController implements Serializable {

    public TransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("atimmovies_Atimmovies_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaksi transaksi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Dvd dvdOrphanCheck = transaksi.getDvd();
        if (dvdOrphanCheck != null) {
            Transaksi oldTransaksiOfDvd = dvdOrphanCheck.getTransaksi();
            if (oldTransaksiOfDvd != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Dvd " + dvdOrphanCheck + " already has an item of type Transaksi whose dvd column cannot be null. Please make another selection for the dvd field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd dvd = transaksi.getDvd();
            if (dvd != null) {
                dvd = em.getReference(dvd.getClass(), dvd.getIdCD());
                transaksi.setDvd(dvd);
            }
            Pelanggan pelanggan = transaksi.getPelanggan();
            if (pelanggan != null) {
                pelanggan = em.getReference(pelanggan.getClass(), pelanggan.getIdPelanggan());
                transaksi.setPelanggan(pelanggan);
            }
            em.persist(transaksi);
            if (dvd != null) {
                dvd.setTransaksi(transaksi);
                dvd = em.merge(dvd);
            }
            if (pelanggan != null) {
                Transaksi oldTransaksiOfPelanggan = pelanggan.getTransaksi();
                if (oldTransaksiOfPelanggan != null) {
                    oldTransaksiOfPelanggan.setPelanggan(null);
                    oldTransaksiOfPelanggan = em.merge(oldTransaksiOfPelanggan);
                }
                pelanggan.setTransaksi(transaksi);
                pelanggan = em.merge(pelanggan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaksi(transaksi.getIdtransaksi()) != null) {
                throw new PreexistingEntityException("Transaksi " + transaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaksi transaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi persistentTransaksi = em.find(Transaksi.class, transaksi.getIdtransaksi());
            Dvd dvdOld = persistentTransaksi.getDvd();
            Dvd dvdNew = transaksi.getDvd();
            Pelanggan pelangganOld = persistentTransaksi.getPelanggan();
            Pelanggan pelangganNew = transaksi.getPelanggan();
            List<String> illegalOrphanMessages = null;
            if (dvdNew != null && !dvdNew.equals(dvdOld)) {
                Transaksi oldTransaksiOfDvd = dvdNew.getTransaksi();
                if (oldTransaksiOfDvd != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Dvd " + dvdNew + " already has an item of type Transaksi whose dvd column cannot be null. Please make another selection for the dvd field.");
                }
            }
            if (pelangganOld != null && !pelangganOld.equals(pelangganNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pelanggan " + pelangganOld + " since its transaksi field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (dvdNew != null) {
                dvdNew = em.getReference(dvdNew.getClass(), dvdNew.getIdCD());
                transaksi.setDvd(dvdNew);
            }
            if (pelangganNew != null) {
                pelangganNew = em.getReference(pelangganNew.getClass(), pelangganNew.getIdPelanggan());
                transaksi.setPelanggan(pelangganNew);
            }
            transaksi = em.merge(transaksi);
            if (dvdOld != null && !dvdOld.equals(dvdNew)) {
                dvdOld.setTransaksi(null);
                dvdOld = em.merge(dvdOld);
            }
            if (dvdNew != null && !dvdNew.equals(dvdOld)) {
                dvdNew.setTransaksi(transaksi);
                dvdNew = em.merge(dvdNew);
            }
            if (pelangganNew != null && !pelangganNew.equals(pelangganOld)) {
                Transaksi oldTransaksiOfPelanggan = pelangganNew.getTransaksi();
                if (oldTransaksiOfPelanggan != null) {
                    oldTransaksiOfPelanggan.setPelanggan(null);
                    oldTransaksiOfPelanggan = em.merge(oldTransaksiOfPelanggan);
                }
                pelangganNew.setTransaksi(transaksi);
                pelangganNew = em.merge(pelangganNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = transaksi.getIdtransaksi();
                if (findTransaksi(id) == null) {
                    throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.");
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
            Transaksi transaksi;
            try {
                transaksi = em.getReference(Transaksi.class, id);
                transaksi.getIdtransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Pelanggan pelangganOrphanCheck = transaksi.getPelanggan();
            if (pelangganOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaksi (" + transaksi + ") cannot be destroyed since the Pelanggan " + pelangganOrphanCheck + " in its pelanggan field has a non-nullable transaksi field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Dvd dvd = transaksi.getDvd();
            if (dvd != null) {
                dvd.setTransaksi(null);
                dvd = em.merge(dvd);
            }
            em.remove(transaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaksi> findTransaksiEntities() {
        return findTransaksiEntities(true, -1, -1);
    }

    public List<Transaksi> findTransaksiEntities(int maxResults, int firstResult) {
        return findTransaksiEntities(false, maxResults, firstResult);
    }

    private List<Transaksi> findTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaksi.class));
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

    public Transaksi findTransaksi(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaksi> rt = cq.from(Transaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
