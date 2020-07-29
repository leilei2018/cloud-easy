package com.fd.helper.dao.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "tb_book")
@Data
public class Book {
    //
    @Id
    private String id;

}
