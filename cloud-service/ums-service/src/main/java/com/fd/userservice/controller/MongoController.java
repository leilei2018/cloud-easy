package com.fd.userservice.controller;

import com.fd.userservice.dao.mongo.entity.AdminMongo;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoCollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
@Slf4j
public class MongoController {

    static MongoClient mongoClient = MongoClients.create("mongodb://192.168.21.134:27017");
    static MongoDatabase database = mongoClient.getDatabase("test");

    static MongoCollection<AdminMongo> docTab = database.getCollection("ums_admin", AdminMongo.class);
    static{
        //添加mongodb的自动编码解码，让数据直接转换为对象，操作对象




        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .register("com.fd.userservice.dao.model.entity")

                        //.register(AdminMongo.class)

                        //.register(PropertyCodecProvider.)
                        //.automatic(true)
                        .build()));

      //  database.withCodecRegistry(pojoCodecRegistry);
        docTab =  docTab.withCodecRegistry(pojoCodecRegistry);

        //PropertyCodecRegistry pcr = new PropertyCodecRegistry();

    }


    public void gridfs(){
        //http://mongodb.github.io/mongo-java-driver/4.0/driver/tutorials/gridfs/
       //GridFS will automatically create indexes on the files and chunks collections on first upload of data into the GridFS bucket.
       database = mongoClient.getDatabase("gridfs");
       GridFSBucket gridFSBucket = GridFSBuckets.create(database, "files");


        try {
            InputStream streamToUploadFrom = new FileInputStream(new File("/tmp/mongodb-tutorial.pdf"));
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(358400)
                    .metadata(new Document("type", "presentation"));
            ObjectId fileId = gridFSBucket.uploadFromStream("mongodb-tutorial", streamToUploadFrom, options);
        } catch (FileNotFoundException e){
            // handle exception
        }

    }


    public void insert(){
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
        AdminMongo bean = new AdminMongo();
        bean.setName("qwer");
        bean.setAge(10);
        bean.setSex(20);
        bean.setMiss("qqwer");

        docTab.insertOne(bean);
    }
    public void insertMany(){
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }
        //docTab.insertMany(documents);

        
    }
    
    public void count(){
        long l = docTab.countDocuments();
    }


    public void findFirst(){
        //Document first = docTab.find().first();
    }
    
    public void list1(){
        /*for (Document cur : docTab.find()) {
            System.out.println(cur.toJson());
        }*/
        String content = "haha";
        //where name like '%haha%'
        Pattern pattern = Pattern.compile("^.*" + content+ ".*$", Pattern.CASE_INSENSITIVE);

        //where name like 'haha%'
        Pattern pattern2 = Pattern.compile("^" + content+ ".*$", Pattern.CASE_INSENSITIVE);

        //基于reg来解决like
        Bson regex = regex("name", pattern);
        for (AdminMongo cur : docTab.find(regex)) {
            System.out.println(cur);
        }
    }
    public void listWithCursor(){
        /*MongoCursor<Document> cursor = docTab.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }*/
    }

    public void findCondition(){
        Bson name = eq("age", 100);

        //where age>=10 and age<=20
        and(gte("age",10),lte("age",20));



        docTab.find().filter(name);
    }

    public void delete(){
        docTab.deleteOne(eq("_id",new ObjectId("5f112e42ed2d15495c5e9e38")));
    }
    public void update(){
        combine(set("name","qaz"),set("age",1)); // set name='' and age=1
        docTab.updateOne(eq("_id",new ObjectId("5f112eabed2d15495c5e9e46")),set("name","qaz"));


    }

    public void replace(){
        AdminMongo adminMongo = new AdminMongo();
        adminMongo.setName("haha123");
        docTab.replaceOne(eq("_id",new ObjectId("5f112eabed2d15495c5e9e46")),adminMongo);
    }

    public static void main(String[] args) {
        //MongoController mongoController = new MongoController();
        //mongoController.insert();
        //mongoController.replace();
       // mongoController.list1();
      //  LockSupport.park();;

        System.out.println( MongoCollectionUtils.getPreferredCollectionName(MongoController.class));


    }


}
