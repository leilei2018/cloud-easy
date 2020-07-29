package com.fd.helper.dao.mongo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ums_admin")
public class Admin {
    @Id
    private ObjectId id;
    private String name;
    private Integer age;
    private Integer sex;
    private String miss;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Admin{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", sex=").append(sex);
        sb.append(", miss='").append(miss).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
