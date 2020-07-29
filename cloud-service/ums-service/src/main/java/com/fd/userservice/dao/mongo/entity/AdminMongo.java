package com.fd.userservice.dao.mongo.entity;

import com.fd.userservice.dao.model.entity.Admin;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ConcurrentReferenceHashMap;

import javax.activation.MimetypesFileTypeMap;
import java.util.Map;

@Data
/**
 * 反序列化，默认查找id属性，而不是_id。所以必须定义为id.
 * 而且类型一定是org.bson.types.ObjectId类型，不能long int ..
 */
@Document(collection = "tb_admin")
/**
 * @see MongoDataConfiguration,这两个注解都可以，一个时spring-data公共的，一个时mongodb的
 */
public class AdminMongo {
    //private Long id;
    private ObjectId id;
    private String name;
    private Integer age;
    private Integer sex;
    private String miss;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AdminMongo{");
        sb.append("id=").append(id.toHexString());
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", sex=").append(sex);
        sb.append(", miss='").append(miss).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("com.aop.spring".replaceAll("([a-z]+\\.)","$0/"));
        System.out.println("comaopsprin".replaceAll("(.{3})?","$1/"));
        System.out.println("<a>www</a><a>ggg</a><a>qwe".replaceAll("(<.+>.*</.+>)?","$1"));
        System.out.println("<a>www</a><a>ggg</a><a>qwe".replaceAll("(<.+>.*</.+>)?","$1"));
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

        Map<Class<?>, Admin> CACHE = new ConcurrentReferenceHashMap();
        CACHE.computeIfAbsent(Admin.class,Admin::new);

       // System.out.println("".replaceAll("","/"));
    }


}
