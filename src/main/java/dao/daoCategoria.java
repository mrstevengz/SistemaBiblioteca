package dao;

import entities.Categoria;
import jakarta.persistence.EntityManager;

public class daoCategoria implements ICRUD<Categoria>{
    private final EntityManager em;
    public CategoriaDAO(EntityManager em) {
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
}
