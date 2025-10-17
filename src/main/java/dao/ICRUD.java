package dao;

import java.util.List;

public interface ICRUD<T> {
    T guardar(T t);
    T actualizar(int id,T t);
    void eliminar(int id);

}
