package com.fd.userservice.controller;

import com.fd.common.util.encry.MD5Util;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.internal.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import static com.mongodb.client.model.Filters.eq;

@Slf4j
public class MongoGridfsController {

    static MongoClient mongoClient = MongoClients.create("mongodb://192.168.21.134:27017");
    static MongoDatabase database = mongoClient.getDatabase("gridfs");

    final static String bucketName = "files";

    @GetMapping("/picScan")
    public ResponseEntity picScan(){

        HttpHeaders heads = new HttpHeaders();
        heads.add("Content-Type","image/jpg");

        return ResponseEntity
                .ok()
                .headers(heads)
                .body(new FileSystemResource(""));

    }

    public void uploadFromStream(){
        //http://mongodb.github.io/mongo-java-driver/4.0/driver/tutorials/gridfs/
       //GridFS will automatically create indexes on the files and chunks collections on first upload of data into the GridFS bucket.


        //创建一个块，也就是文件上传的一个块，默认就是fs
        //实际上就是创建两个文件存储的表， 默认fs.files 和  fs.chunks
        //如果指定了files， 就会创建files.files 和  files.chunks 两个表
       GridFSBucket gridFSBucket = GridFSBuckets.create(database, "files");

        try {
            InputStream streamToUploadFrom = new FileInputStream(new File("f:/ffdowload/DingTalk_v5.0.15.26.exe"));
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(358400) //350kb
                    .metadata(new Document("type", "presentation"));
            ObjectId fileId = gridFSBucket.uploadFromStream("DingTalk_v5.0.15.26", streamToUploadFrom, options);
        } catch (FileNotFoundException e){
            // handle exception
        }

    }

    public void uploadOpenStream(){
        //http://mongodb.github.io/mongo-java-driver/4.0/driver/tutorials/gridfs/
        //GridFS will automatically create indexes on the files and chunks collections on first upload of data into the GridFS bucket.

        //创建一个块，也就是文件上传的一个块，默认就是fs
        //实际上就是创建两个文件存储的表， 默认fs.files 和  fs.chunks
        //如果指定了files， 就会创建files.files 和  files.chunks 两个表
        MongoCollection<Document> fsTable = database.getCollection(bucketName + ".files");
        try {
            /*InputStream inputStream = new FileInputStream(new File("f:/ffdowload/DingTalk_v5.0.15.26.exe"));
            byte[] data = StreamUtil.streamBytes(inputStream,false);
            String md5 = HexUtils.toHex( MD5Util.md5(data));*/

            byte[] data = Files.readAllBytes(new File("f:/ffdowload/DingTalk_v5.0.15.26.exe").toPath());
            String md5 = HexUtils.toHex( MD5Util.md5(data));
            Document doc = fsTable.find(eq("md5", md5))
                    .projection(new BasicDBObject("_id", 1))
                    .first();
            if (doc!=null){
                System.out.println("文件已存在，file主键id:"+doc.getObjectId("_id"));
            }else{
                GridFSBucket gridFSBucket = GridFSBuckets.create(database, bucketName);
                GridFSUploadOptions options = new GridFSUploadOptions()
                        .chunkSizeBytes(358400) //350kb
                        .metadata(new Document("type", "presentation2"));
                GridFSUploadStream uploadStream = gridFSBucket.openUploadStream("DingTalk_v5.0.15.26", options);
                uploadStream.write(data);
                uploadStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download(){
        GridFSBucket gridFSBucket = GridFSBuckets.create(database, "files");
        OutputStream output = null;
        try {
            output = new FileOutputStream(new File("g:/temp.exe"));
            gridFSBucket.downloadToStream(new ObjectId("5f11642aaeddc8493bcac82e"),output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void rename(){
        GridFSBucket gridFSBucket = GridFSBuckets.create(database, "files");
        gridFSBucket.rename(new ObjectId("5f11642aaeddc8493bcac82e"), "mongodbTutorial");
    }
    public void del(){
        GridFSBucket gridFSBucket = GridFSBuckets.create(database, "files");
        gridFSBucket.delete(new ObjectId("5f11642aaeddc8493bcac82e"));
    }

    public void findFiles(){
        GridFSBucket gridFSBucket = GridFSBuckets.create(database, "files");
        GridFSFindIterable gridFSFiles = gridFSBucket.find(eq("metadata.type","presentation"));
        for(GridFSFile fs:gridFSFiles){
            System.out.println(fs);
        }
    }

    public static void main(String[] args) {
        MongoGridfsController mongoController = new MongoGridfsController();
        mongoController.uploadOpenStream();
    }


}
