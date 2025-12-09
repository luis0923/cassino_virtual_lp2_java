package interfaces;

import java.util.List;

public interface CRUD<T> {

    void create(T entidade) throws Exception;

    List<T> read();

    void update(T entidade) throws Exception;

    void delete(T entidade) throws Exception;
}
