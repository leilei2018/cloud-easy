package com.fd.helper.dao.es.repo;

import com.fd.helper.dao.es.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

//@see @EnableElasticsearchRepositories("")

public interface BookRepository extends ElasticsearchCrudRepository<Book,String> {
}
