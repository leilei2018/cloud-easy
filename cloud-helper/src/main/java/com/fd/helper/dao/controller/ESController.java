package com.fd.helper.dao.controller;

import com.fd.helper.dao.es.entity.Book;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

public class ESController {
    ElasticsearchRestTemplate t;
    public void index(Book book){
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(book.getId())
                .withObject(book)
                .build();
        String documentId = t.index(indexQuery);
    }

    public Book findById( Long id) {
        Book person = t
                .queryForObject(GetQuery.getById(id.toString()), Book.class);
        return person;
    }

}
