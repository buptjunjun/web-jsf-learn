package org.easyGoingCrawler.DAO;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.Mongo;
import com.mongodb.MongoException;


/**s
 * Java Spring Data & MongoDB Example
 * 
 */
public class DAOMongo {
	private static final Log log = LogFactory.getLog(DAOMongo.class);

    public static void main(String[] args) throws UnknownHostException, MongoException {

    	//MongoTemplate,  test is the db name.
        MongoOperations mongoOps = new MongoTemplate(new Mongo(""), "test");
        for(int i = 0; i < 3; i++)        
        {
        	Person obj = new Person("Joe", 39);
        	mongoOps.insert(obj);		
        }
        	
      
        
        Query q = new Query(where("urls").in("aaa"));
        q.sort().on("date", Order.ASCENDING);
        q.limit(10);
        List<Person> lp = mongoOps.find(q, Person.class);
        for(Person p1: lp)
        {
        	System.out.println(p1);
        }
        
//         Person p =  mongoOps.findOne(new Query(), Person.class);
//         System.out.println(p);
       // mongoOps.upsert(new Query(where("name").is("Joe")), new Update().set("name", "junjun"), Person.class);
       // mongoOps.dropCollection("person");
    }
}