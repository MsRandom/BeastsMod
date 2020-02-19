package random.beasts.api.main;

import java.util.ArrayList;
import java.util.List;

public final class BeastsRegistry<T> {
    private final List<T> list = new ArrayList<>();

    public final void add(T value) {
        list.add(value);
    }

    @SuppressWarnings("unused")
    public final List<T> get(Object defaultResolvable) {
        return get();
    }

    public final List<T> get() {
        return list;
    }
}
