package io.github.elishaSword.library.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, V> {
	public void insert(V value);
	
	public Optional<V> getById(K key);
	public List<V> getAll();
	
	public void update(V value);
	
	public void delete(V value);
	public void deleteById(K key);
}
