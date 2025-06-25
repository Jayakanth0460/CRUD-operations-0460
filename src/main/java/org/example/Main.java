package org.example;
import java.util.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static Scanner sc=new Scanner(System.in);
    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    static MongoDatabase database = mongoClient.getDatabase("companyDB");
    static MongoCollection<Document> collection = database.getCollection("employees");
    static HashMap<Integer,Integer> empIds=new HashMap<>();
    public static void main(String[] args) {
        int k;
        do {
            System.out.println("CRUD OPERATIONS");
            System.out.println("1.Create  2.Read  3.Update 4.Delete 5.Exit");
            System.out.println("please select your choice");
            k = sc.nextInt();
            if(k>5){
                System.out.println("Invalid choice please try again");
               // return;
            }
            switch (k) {
                case 1:
                    insertion();
                    break;
                case 2:
                    printt();
                    break;
                case 3:printt();
                    update();
                    break;
                case 4:
                    delete();
                    break;
                default:
                    break;
            }
        }while(k!=5);
        System.out.println("Exit Successful");
    }
    //CREATE
    public static void insertion() {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter employee id");
        int emp = sc.nextInt();

        empIds.put(emp,1);
        System.out.println("enter name of the employee");
        String name = sc.nextLine();
        System.out.println("enter age of the employee");
        int age=sc.nextInt();
        sc.nextLine();
        System.out.println("enter role");
        String role = sc.nextLine();
        System.out.println("enter salary");
        int sal = sc.nextInt();
        Document emp1 = new Document("empId", emp).append("name", name).append("age",age).append("role", role).append("salary", sal);
        collection.insertOne(emp1);
        System.out.println("inserted successfully");
    }
    //READ
    public static void printt(){
        FindIterable<Document> employees = collection.find();
        for (Document doc : employees) {
            Object o=doc.get("empId");
            int ei=(int)o;
            empIds.put(ei,1);
            System.out.println(doc.toJson());
        }
    }
    //UPDATE
    public static void update(){

        System.out.println("Enter employee id to update");
        int e=sc.nextInt();

        if(!empIds.containsKey(e)){
            System.out.println("this employee does not exist");
            return;
        }
        System.out.println("Choose what you want to update for this empId: "+e);
        System.out.println("1.role 2.salary 3.age");
        int k1=sc.nextInt();

        if(k1==1){
            sc.nextLine();
            System.out.println("enter updated role");

            String r1=sc.nextLine();
            collection.updateOne(eq("empId",e), new Document("$set", new Document("role", r1)));
        }
        else if(k1==2){
            System.out.println("enter new salary");
            int sal1=sc.nextInt();
            collection.updateOne(eq("empId",e), new Document("$set", new Document("salary", sal1)));
        }
        else if(k1==3){
            System.out.println("enter new age of the employee");
            int new_age=sc.nextInt();
            collection.updateOne(eq("empId",e),new Document("$set",new Document("age",new_age)));
        }
        else{
            System.out.println("Invalid choice");
            return;
        }

        System.out.println("updated successfully");
    }
    //DELETE
    public static void delete(){

        System.out.println("enter id to delete");
        int e2=sc.nextInt();
        collection.deleteOne(eq("empId",e2));
        System.out.println("Deleted employee successfully");
    }
}