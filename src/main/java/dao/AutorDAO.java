package dao;

import entities.Autor;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AutorDAO implements ICRUD<Autor> {
    private EntityManager em;
    public AutorDAO(EntityManager em) {
        this.em = em;
    }
    @Override
    public Autor guardar(Autor autor) {
        if(autor != null){
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
            return autor;
        }
        return autor;
    }

    @Override
    public Autor actualizar(int id, Autor autor) {
        Autor autorid = em.find(Autor.class, id);
        if(autorid != null){
            em.getTransaction().begin();
            Autor autorActualizado = em.merge(autor);
            em.getTransaction().commit();
            return autorActualizado;
        }
        return null;
    }

    @Override
    public void eliminar(int id) {
        Autor autor = em.find(Autor.class, id);
        if(autor != null){
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
        }
    }

    @Override
    public Autor buscarPorId(int id) {
        return em.find(Autor.class, id);
    }

    @Override
    public List<Autor> listar() {
        List<Autor> lista = em.createQuery("from Autor", Autor.class).getResultList();
        return lista;
    }
}

