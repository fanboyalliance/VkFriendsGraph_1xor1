package core.interfaces;

import org.jetbrains.annotations.NotNull;

public interface IKruskal<T> {
    void findMaxTree(@NotNull IGraph<T> graph);
}
