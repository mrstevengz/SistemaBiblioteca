package dao;

import entities.Libro;
import jakarta.persistence.EntityManager;

import java.util.List;

public class daoLibro implements ICRUD<Libro> {
    private final EntityManager em;
    public daoLibro(EntityManager em) {
        this.em = em;
    }
    @Override
    public Libro guardar(Libro libro) {
        if(libro != null){
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            return libro;
        }
        return null;
    }
    @Override
    public Libro buscarPorId(int id) {
        return em.find(Libro.class, id);
    }

    @Override
    public Libro actualizar(int id, Libro libro) {
        Libro libroId = em.find(Libro.class, id);
        if(libroId != null){
            em.getTransaction().begin();
            Libro libroActualizado = em.merge(libro);
            em.getTransaction().commit();
            return libroActualizado;
        }
        return null;
    }

    @Override
    public void eliminar(int id) {
        Libro libro = em.find(Libro.class, id);
        if (libro != null) {
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Libro> listar() {
        List<Libro> lista = em.createQuery("from Libro", Libro.class).getResultList();
        return lista;
    }

}
