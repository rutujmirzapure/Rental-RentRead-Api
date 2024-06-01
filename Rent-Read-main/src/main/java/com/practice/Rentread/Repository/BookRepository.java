package com.practice.Rentread.Repository;

import com.practice.Rentread.DataModels.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
}
