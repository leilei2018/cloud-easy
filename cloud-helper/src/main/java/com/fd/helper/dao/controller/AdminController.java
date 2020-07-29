package com.fd.helper.dao.controller;

import com.fd.helper.dao.mongo.entity.Admin;
import com.fd.helper.dao.mongo.repo.AdminRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AdminController implements SmartInitializingSingleton {

    @Autowired
    AdminRepository repo;

    @Override
    public void afterSingletonsInstantiated() {

        insert();
        //update();
        //pageQuery();
        //sort();
        //delete();

        //customer();
    }

    public void insert(){
        Admin s = new Admin();
        s.setId(new ObjectId("5f15394e967aa7401f4802ab"));
        s.setAge(33);
        s.setName("qaab");
       // repo.save(s);

        Admin s2 = new Admin();
        s2.setAge(3131);
        s2.setName("qaab");
        repo.insert(s2);
        //save是直接替换了
        //insert，主键存在，抛出异常
    }

    public void findById(){
        String id = "5f150387dc0c1f7002f8d41a";
        System.out.println(repo.findById(new ObjectId(id)));
    }

    public void pageQuery(){
        Sort.Order name = Sort.Order.desc("name");
        //分页，从0开始
        int pageNo = 1;
        Pageable pageable = PageRequest.of(pageNo-1,10, Sort.by(name));

        ExampleMatcher matcher = ExampleMatcher.matching()
                // .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())  //like '%a%'
                 .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
                 //.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith())  // like 'a%'
                 //.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith())  //like '%a'
                //.withIgnorePaths("age","createTime");
        ;
        Admin admin = new Admin();
        admin.setName("qaab");
        admin.setAge(33);

        //默认根据Example.of的非空值，精确查找，
        //如果需要like，则需要定义matcher
        Example<Admin> example = Example.of(admin, matcher);
        Page<Admin> all = repo.findAll(example, pageable);
        System.out.println(all.getContent());
    }

    public void sort(){
        Sort.Order name = Sort.Order.desc("name");
        Sort.Order age = Sort.Order.asc("age");
        Sort sort = Sort.by(name,age);
        //sort = Sort.unsorted();
        System.out.println(repo.findAll(sort));
    }

    public void delete(){
        Admin entity = new Admin();
        entity.setName("bbb");

        List<Admin> all = repo.findAll(Example.of(entity));

        //只用根据id删除。 （delete(T)垃圾）
        all.forEach(x->repo.deleteById(x.getId()));
    }

    public void update(){

    }

    public void customer(){
        List<Admin> bySexIsNull = repo.findByNameLikeAndAge("aa",33);
        System.out.println(bySexIsNull);
    }

}
