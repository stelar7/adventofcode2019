package utils.sources;

import java.util.List;

public interface SourceSupplier<T>
{
    List<T> getDataSource();
}
