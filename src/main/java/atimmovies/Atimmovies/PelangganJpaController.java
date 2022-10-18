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
public class PelangganJpaController implements Serializable {

    public PelangganJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("atimmovies_Atimmovies_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pelanggan pelanggan) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Transaksi transaksiOrphanCheck = pelanggan.getTransaksi();
        if (transaksiOrphanCheck != null) {
            Pelanggan oldPelangganOfTransaksi = transaksiOrphanCheck.getPelanggan();
            if (oldPelangganOfTransaksi != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Transaksi " + transaksiOrphanCheck + " already has an item of type Pelanggan whose transaksi column cannot be null. Please make another selection for the transaksi field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai pegawai = pelanggan.getPegawai();
            if (pegawai != null) {
                pegawai = em.getReference(pegawai.getClass(), pegawai.getIdPegawai());
                pelanggan.setPegawai(pegawai);
            }
            Transaksi transaksi = pelanggan.getTransaksi();
            if (transaksi != null) {
                transaksi = em.getReference(transaksi.getClass(), transaksi.getIdtransaksi());
                pelanggan.setTransaksi(transaksi);
            }
            em.persist(pelanggan);
            if (pegawai != null) {
                Pelanggan oldPelangganOfPegawai = pegawai.getPelanggan();
                if (oldPelangganOfPegawai != null) {
                    oldPelangganOfPegawai.setPegawai(null);
                    oldPelangganOfPegawai = em.merge(oldPelangganOfPegawai);
                }
                pegawai.setPelanggan(pelanggan);
                pegawai = em.merge(pegawai);
            }
            if (transaksi != null) {
                transaksi.setPelanggan(pelanggan);
                transaksi = em.merge(transaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPelanggan(pelanggan.getIdPelanggan()) != null) {
                throw new PreexistingEntityException("Pelanggan " + pelanggan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pelanggan pelanggan) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelanggan persistentPelanggan = em.find(Pelanggan.class, pelanggan.getIdPelanggan());
            Pegawai pegawaiOld = persistentPelanggan.getPegawai();
            Pegawai pegawaiNew = pelanggan.getPegawai();
            Transaksi transaksiOld = persistentPelanggan.getTransaksi();
            Transaksi transaksiNew = pelanggan.getTransaksi();
            List<String> illegalOrphanMessages = null;
            if (pegawaiOld != null && !pegawaiOld.equals(pegawaiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pegawai " + pegawaiOld + " since its pelanggan field is not nullable.");
            }
            if (transaksiNew != null && !transaksiNew.equals(transaksiOld)) {
                Pelanggan oldPelangganOfTransaksi = transaksiNew.getPelanggan();
                if (oldPelangganOfTransaksi != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Transaksi " + transaksiNew + " already has an item of type Pelanggan whose transaksi column cannot be null. Please make another selection for the transaksi field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pegawaiNew != null) {
                pegawaiNew = em.getReference(pegawaiNew.getClass(), pegawaiNew.getIdPegawai());
                pelanggan.setPegawai(pegawaiNew);
            }
            if (transaksiNew != null) {
                transaksiNew = em.getReference(transaksiNew.getClass(), transaksiNew.getIdtransaksi());
                pelanggan.setTransaksi(transaksiNew);
            }
            pelanggan = em.merge(pelanggan);
            if (pegawaiNew != null && !pegawaiNew.equals(pegawaiOld)) {
                Pelanggan oldPelangganOfPegawai = pegawaiNew.getPelanggan();
                if (oldPelangganOfPegawai != null) {
                    oldPelangganOfPegawai.setPegawai(null);
                    oldPelangganOfPegawai = em.merge(oldPelangganOfPegawai);
                }
                pegawaiNew.setPelanggan(pelanggan);
                pegawaiNew = em.merge(pegawaiNew);
            }
            if (transaksiOld != null && !transaksiOld.equals(transaksiNew)) {
                transaksiOld.setPelanggan(null);
                transaksiOld = em.merge(transaksiOld);
            }
            if (transaksiNew != null && !transaksiNew.equals(transaksiOld)) {
                transaksiNew.setPelanggan(pelanggan);
                transaksiNew = em.merge(transaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pelanggan.getIdPelanggan();
                if (findPelanggan(id) == null) {
                    throw new NonexistentEntityException("The pelanggan with id " + id + " no longer exists.");
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
            Pelanggan pelanggan;
            try {
                pelanggan = em.getReference(Pelanggan.class, id);
                pelanggan.getIdPelanggan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pelanggan with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Pegawai pegawaiOrphanCheck = pelanggan.getPegawai();
            if (pegawaiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pelanggan (" + pelanggan + ") cannot be destroyed since the Pegawai " + pegawaiOrphanCheck + " in its pegawai field has a non-nullable pelanggan field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Transaksi transaksi = pelanggan.getTransaksi();
            if (transaksi != null) {
                transaksi.setPelanggan(null);
                transaksi = em.merge(transaksi);
            }
            em.remove(pelanggan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pelanggan> findPelangganEntities() {
        return findPelangganEntities(true, -1, -1);
    }

    public List<Pelanggan> findPelangganEntities(int maxResults, int firstResult) {
        return findPelangganEntities(false, maxResults, firstResult);
    }

    private List<Pelanggan> findPelangganEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pelanggan.class));
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

    public Pelanggan findPelanggan(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pelanggan.class, id);
        } finally {
            em.close();
        }
    }

    public int getPelangganCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pelanggan> rt = cq.from(Pelanggan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
