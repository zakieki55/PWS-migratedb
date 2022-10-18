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
public class PegawaiJpaController implements Serializable {

    public PegawaiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("atimmovies_Atimmovies_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pegawai pegawai) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Pelanggan pelangganOrphanCheck = pegawai.getPelanggan();
        if (pelangganOrphanCheck != null) {
            Pegawai oldPegawaiOfPelanggan = pelangganOrphanCheck.getPegawai();
            if (oldPegawaiOfPelanggan != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pelanggan " + pelangganOrphanCheck + " already has an item of type Pegawai whose pelanggan column cannot be null. Please make another selection for the pelanggan field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelanggan pelanggan = pegawai.getPelanggan();
            if (pelanggan != null) {
                pelanggan = em.getReference(pelanggan.getClass(), pelanggan.getIdPelanggan());
                pegawai.setPelanggan(pelanggan);
            }
            em.persist(pegawai);
            if (pelanggan != null) {
                pelanggan.setPegawai(pegawai);
                pelanggan = em.merge(pelanggan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPegawai(pegawai.getIdPegawai()) != null) {
                throw new PreexistingEntityException("Pegawai " + pegawai + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pegawai pegawai) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai persistentPegawai = em.find(Pegawai.class, pegawai.getIdPegawai());
            Pelanggan pelangganOld = persistentPegawai.getPelanggan();
            Pelanggan pelangganNew = pegawai.getPelanggan();
            List<String> illegalOrphanMessages = null;
            if (pelangganNew != null && !pelangganNew.equals(pelangganOld)) {
                Pegawai oldPegawaiOfPelanggan = pelangganNew.getPegawai();
                if (oldPegawaiOfPelanggan != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pelanggan " + pelangganNew + " already has an item of type Pegawai whose pelanggan column cannot be null. Please make another selection for the pelanggan field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pelangganNew != null) {
                pelangganNew = em.getReference(pelangganNew.getClass(), pelangganNew.getIdPelanggan());
                pegawai.setPelanggan(pelangganNew);
            }
            pegawai = em.merge(pegawai);
            if (pelangganOld != null && !pelangganOld.equals(pelangganNew)) {
                pelangganOld.setPegawai(null);
                pelangganOld = em.merge(pelangganOld);
            }
            if (pelangganNew != null && !pelangganNew.equals(pelangganOld)) {
                pelangganNew.setPegawai(pegawai);
                pelangganNew = em.merge(pelangganNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pegawai.getIdPegawai();
                if (findPegawai(id) == null) {
                    throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai pegawai;
            try {
                pegawai = em.getReference(Pegawai.class, id);
                pegawai.getIdPegawai();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.", enfe);
            }
            Pelanggan pelanggan = pegawai.getPelanggan();
            if (pelanggan != null) {
                pelanggan.setPegawai(null);
                pelanggan = em.merge(pelanggan);
            }
            em.remove(pegawai);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pegawai> findPegawaiEntities() {
        return findPegawaiEntities(true, -1, -1);
    }

    public List<Pegawai> findPegawaiEntities(int maxResults, int firstResult) {
        return findPegawaiEntities(false, maxResults, firstResult);
    }

    private List<Pegawai> findPegawaiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pegawai.class));
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

    public Pegawai findPegawai(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pegawai.class, id);
        } finally {
            em.close();
        }
    }

    public int getPegawaiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pegawai> rt = cq.from(Pegawai.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
