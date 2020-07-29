package com.fd.helper.dao.mongo.repo;

import com.fd.helper.dao.mongo.entity.Admin;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface AdminRepository extends MongoRepository<Admin, ObjectId> {

    //https://blog.csdn.net/weixin_39214304/article/details/84791953  spring-data规范

    //where name like '%x%'
    List<Admin>  findByNameLike(String name);

    Page<Admin> findByNameLike(String name, Pageable pageable);

    List<Admin>  findByNameLikeAndAge(String name,int age);

    //where sex is null;
    List<Admin>  findBySexIsNull();

    //where sex is not null;
    List<Admin>  findBySexIsNotNull();


    //where age> and age<
    List<Admin>  findByAgeBetween(int a,int b);

}
