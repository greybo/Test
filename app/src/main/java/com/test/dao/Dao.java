package com.test.dao;

import java.util.List;

/**
 * Created by m on 20.09.2017.
 */

public interface Dao<T> {
    void readAll();

    void saveAll(List<T> list);

    void save(T t);
}
