package abstracts;

import interfaces.CRUD;
import interfaces.Identificavel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T extends Identificavel> implements CRUD<T> {

    protected final List<T> registros = new ArrayList<>();

    @Override
    public void create(T entidade) throws Exception {
        if (entidade.getId() == 0) {
            entidade.setId(registros.size() + 1);
        }
        registros.add(entidade);
    }

    @Override
    public List<T> read() {
        return registros;
    }

    @Override
    public void update(T entidade) throws Exception {
        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getId() == entidade.getId()) {
                registros.set(i, entidade);
                return;
            }
        }
        throw new Exception("Registro não encontrado para atualização.");
    }

    @Override
    public void delete(T entidade) throws Exception {
        boolean removido = registros.removeIf(item -> item.getId() == entidade.getId());

        if (!removido) {
            throw new Exception("Registro não encontrado para remoção.");
        }
    }
}
