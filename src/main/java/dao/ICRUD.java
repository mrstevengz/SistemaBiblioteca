package dao;

import java.util.List;

public interface ICRUD<T> {
    T guardar(T t);
    T actualizar(int id,T t);
    void eliminar(int id);
    T buscarPorId(int id);
    List<T> listar();

}
