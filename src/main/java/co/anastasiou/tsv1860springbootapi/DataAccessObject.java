package co.anastasiou.tsv1860springbootapi;

import java.util.List;

public interface DataAccessObject {
    public abstract PlayerDTO getById(int id);
    public abstract List<PlayerDTO> getAll();
    public abstract PlayerDTO update(PlayerDTO dto);
    public abstract void create(PlayerDTO dto);
    public abstract void delete(int id);
}
