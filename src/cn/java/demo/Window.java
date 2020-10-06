/**
 * Project Name:chapter_mongodb02
 * File Name:Window.java
 * Package Name:cn.java.demo
 * Date:2020年7月23日下午4:02:02
 * Copyright (c) 2020, bluemobi All Rights Reserved.
 *
*/

package cn.java.demo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * Description: mongodb测试 <br/>
 * Date: 2020年7月23日 下午4:02:02 <br/>
 * 
 * @author HML
 * @version
 * @see
 */
public class Window {
    private MongoClient client;

    private MongoDatabase database;

    private MongoCollection<Document> books;

    @Before
    public void init() {
        // 创建客户端
        client = new MongoClient();
        // 获取数据库名
        database = client.getDatabase("dt55");
        // 获取集合名
        books = database.getCollection("books");
    }

    @After
    public void Close() {
        // 关闭资源
        client.close();
    }

    /**
     * 
     * Description:查询所有数据 <br/>
     *
     * @author HML
     */
    @Test
    public void test1() {
        // 查询所有数据
        FindIterable<Document> find = books.find();
        for (Document book : find) {
            System.out.println(book);
        }

    }

    /**
     * 
     * Description:查询指定条件数据 <br/>
     *
     * @author HML
     */
    @Test
    public void test2() {
        Document bson = new Document();
        bson.append("bookName", "spring");
        FindIterable<Document> find = books.find(bson);
        for (Document book : find) {
            System.out.println(book);
        }
    }

    /**
     * 
     * Description: 查询指定区间的数据<br/>
     *
     * @author HML
     */
    @Test
    public void test7() {

        Bson bson1 = Filters.gte("price", 29.9);
        Bson bson2 = Filters.lte("price", 69.9);
        Bson bson3 = Filters.and(bson1, bson2);
        FindIterable<Document> find = books.find(bson3);
        for (Document book : find) {
            System.out.println(book);
        }
    }

    /**
     * 
     * Description:查询多条件数据 <br/>
     *
     * @author HML
     */
    @Test
    public void test8() {
        Document document1 = new Document("bookName", "spring");
        Document document2 = new Document("bookName", "oracle");
        Bson bson = Filters.or(document1, document2);
        FindIterable<Document> find = books.find(bson);
        for (Document book : find) {
            System.out.println(book);
        }
    }

    /**
     * 
     * Description:分页查询 <br/>
     *
     * @author HML
     */
    @Test
    public void test9() {
        FindIterable<Document> find = books.find();
        FindIterable<Document> books = find.skip(0).limit(3);
        for (Document book : books) {
            System.out.println(book);
        }
    }

    /**
     * 
     * Description:添加一条数据 <br/>
     *
     * @author HML
     */
    @Test
    public void test3() {
        Document bson = new Document();
        bson.append("bookName", "oracle");
        bson.append("price", 199);
        books.insertOne(bson);
    }

    /**
     * 
     * Description:添加多条数据 <br/>
     *
     * @author HML
     */
    @Test
    public void test6() {
        Document document1 = new Document();
        document1.append("bookName", "jdbc");
        document1.append("price", 29.9);
        Document document2 = new Document();
        document2.append("bookName", "集合");
        document2.append("price", 69.9);
        List<Document> docList = new ArrayList<Document>();
        docList.add(0, document1);
        docList.add(1, document2);
        books.insertMany(docList);
    }

    /**
     * 
     * Description:删除数据 <br/>
     *
     * @author HML
     */
    @Test
    public void test4() {
        Document bson = new Document();
        bson.append("price", 29.9);
        books.deleteOne(bson);
    }

    /**
     * 
     * Description: 更新数据<br/>
     *
     * @author HML
     */
    @Test
    public void test5() {
        Bson bson = Filters.eq("bookName", "集合");
        Document document = new Document();
        document.append("price", 39.9);
        Document document2 = new Document("$set", document);
        books.updateOne(bson, document2);

    }

    /**
     * 
     * Description:多条件修改 <br/>
     *
     * @author HML
     */
    @Test
    public void test10() {
        Bson bson1 = Filters.gte("price", 29.9);
        Bson bson2 = Filters.lte("price", 59.9);
        Bson bson3 = Filters.and(bson1, bson2);
        Document document1 = new Document("bookName", "springmvc");
        Document document2 = new Document("$set", document1);

        books.updateMany(bson3, document2);
    }
}
