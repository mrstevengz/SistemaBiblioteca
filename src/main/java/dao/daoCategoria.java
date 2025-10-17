package dao;

import entities.Categoria;
import jakarta.persistence.EntityManager;

public class daoCategoria implements ICRUD<Categoria>{
    private final EntityManager em;
    public daoCategoria(EntityManager em) {
        this.em = em;
    }
    @Override
    public Categoria guardar(Categoria categoria) {
        if (categoria != null) {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
            return categoria;
        }
        return null;
    }

    @Override
    public Categoria buscarPorId(int id) {
        return em.find(Categoria.class, id);
    }

    @Override
    public Categoria actualizar(int id, Categoria categoria) {
        Categoria categoriaId = em.find(Categoria.class, id);
        if (categoriaId != null) {
            em.getTransaction().begin();
            Categoria categoriaActualizada = em.merge(categoria);
            em.getTransaction().commit();
            return categoriaActualizada;
        }
        return null;
    }
}
