package com.example.techstore.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper{

    public static String DBName="techStore";
    SQLiteDatabase techStore;


    public DbHelper(Context context)
    {
        super(context,DBName,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*----------------------------------------------create----------------------------------------------*/
        db.execSQL("create table customers (id integer primary key Autoincrement ," +
                "name text not null," +
                "email text not null ," +
                "password text not null, " +
                "birth_date text not null ," +
                "phone_number text not null," +
                "gender text," +
                "jop text)");

        db.execSQL("create table categories (id integer primary key Autoincrement , " +
                "name text not null," +
                "image text not null)");

        db.execSQL("create table products (id integer primary key Autoincrement , " +
                "name text not null , " +
                "price text not null , " +
                "image text not null , " +
                "quantity integer ," +
                "categoryId integer not null ," +
                "best_seller integer not null, " +
                "FOREIGN KEY(categoryId) References categories(id) )");

        db.execSQL("create table orders (id integer primary key Autoincrement , " +
                "date text not null , " +
                "user_id integer not null , " +
                "address text not null , " +
                "total_price text not null , " +
                "submit text not null," +
                "FOREIGN KEY(user_id) References customers(id) )");

        db.execSQL("create table order_details (order_id integer not null , " +
                "product_id integer not null , " +
                "quantity integer not null , " +
                "Primary Key (order_id,product_id)," +
                "FOREIGN KEY(order_id) References orders(id) ," +
                "FOREIGN KEY(product_id) References products(id) )");


        /*----------------------------------------------Insert----------------------------------------------*/
        db.execSQL("INSERT INTO categories (name,image) VALUES ('laptops','laptop1')");
        db.execSQL("INSERT INTO categories (name,image) VALUES ('phone','mobile')");
        db.execSQL("INSERT INTO categories (name,image) VALUES ('screen','monitor')");


        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Dell_Inspiron','15999','dell_inspiron',100,1,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('HP_Pavilion','24989','hp',100,1,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Lenovo_IdeaPad_130','5550','lenovo',100,1,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Lenovo_Legion_Y730','25690','legion',100,1,1)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Cherry_ZE97B','3999','cherry_300',100,1,0)");

        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('IPhone11','14399','iphone11',100,2,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('OppO_Reno_3','6100','oppo_reno',100,2,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('IPhone6S','7399','iphone6s',100,2,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Xiaomi_Redmi_4A','1899','xiaomi_1',100,2,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Honor_9X_Pro','4485','honor_9x',100,2,1)");

        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Samsung_4K_50In','6299','samsung_smart_tv',100,3,1)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('Toshiba_4k_65In','11900','toshiba_4k_65in',100,3,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('LG_UHD_50In','6689','lg_uhd',100,3,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('JAC_150AS','4299','jac_150as',100,3,0)");
        db.execSQL("INSERT INTO products (name,price,image,quantity,categoryId,best_seller) VALUES ('LG_OLED_65In','63999','lg_oled',100,3,0)");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists products");
        onCreate(db);

    }

    /*-----------------------------------------------user table functions-----------------------------------------------*/
    public void signUp( String userName, String email, String password, String BirthDate, String phone) {
        techStore = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name", userName);
        row.put("email", email);
        row.put("password", password);
        row.put("birth_date", BirthDate);
        row.put("phone_number", phone);
        row.put("gender", "");
        row.put("jop", "");
        techStore.insert("customers", null, row);
        techStore.close();
    }
    public String ValidateUser(String email, String password, String phone, int status) {
        String validation = "Not Valid";
        boolean isEmailFound = false;
        boolean isPasswordFound = false;
        boolean isPhoneFound=false;
        techStore= this.getReadableDatabase();
        String[] data = {email};
        Cursor cursor = techStore.rawQuery("SELECT email,password,phone_number FROM customers WHERE email= ?", data);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            if (cursor.getString(0).equalsIgnoreCase(email))
                isEmailFound = true;
            if (cursor.getString(1).equalsIgnoreCase(password))
                isPasswordFound = true;
            if (cursor.getString(2).equalsIgnoreCase(phone))
                isPhoneFound = true;
        }
        cursor.close();
        techStore.close();
        if(status==1) {
            if (isEmailFound == true && isPasswordFound == true)
                validation = "Valid";
            else if (isEmailFound == true && isPasswordFound == false)
                validation = "Wrong Password";
        }
        else if(status==2) {
            if (isEmailFound == true && isPhoneFound == true)
                validation = "Valid for Recover";
            else if (isEmailFound == true && isPhoneFound == false)
                validation = "Wrong Phone Number";
        }

        return validation;
    }

    public  Cursor getUserByEmail(String email)
    {
        techStore = this.getReadableDatabase();
        String[] data = {email};
        Cursor cursor = techStore.rawQuery("SELECT * FROM customers WHERE email= ?", data);
        if(cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }
    public boolean checkemail(String email) {
        boolean isFound=false;
        techStore= this.getReadableDatabase();
        Cursor cursor = techStore.rawQuery("SELECT * FROM customers WHERE email= '"+email+"'", null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            isFound=true;
        }
        cursor.close();
        techStore.close();
        return isFound;
    }

    public void RecoverPassword(String email, String password) {
        ContentValues row = new ContentValues();
        row.put("password", password);
        techStore = getWritableDatabase();
        techStore.update("customers", row, "email" + "='" + email + "'", null);
        techStore.close();
    }

    public void editAccount(String name ,String oldEmail,String email ,String password,String birthDate,String phone,String gender,String jop) {
        techStore = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name", name);
        row.put("email", email);
        row.put("password", password);
        row.put("birth_date", birthDate);
        row.put("phone_number", phone);
        row.put("gender", gender);
        row.put("jop", jop);
        techStore.update("customers", row, "email" + "='" + oldEmail + "'", null);
        techStore.close();

    }

    /*------------------------------------------------------------------------------------------------------------------*/

    /*-----------------------------------------------products table functions-----------------------------------------------*/
    public Cursor returnProductsByCategory(int Category) {
        techStore = getReadableDatabase();
        String[] data={String.valueOf(Category)};
        Cursor curs = techStore.rawQuery("SELECT * FROM products WHERE categoryId =? ",data);
        if (curs!=null)
            curs.moveToFirst();
        techStore.close();
        return curs;
    }
    public Cursor returnBestSeller() {
        techStore = getReadableDatabase();
        Cursor curs = techStore.rawQuery("SELECT * ,max(best_seller) FROM products group by categoryId ;",null);
        if (curs!=null)
            curs.moveToFirst();
        techStore.close();
        return curs;
    }
    public Cursor returnProductById(int id) {
        techStore = getReadableDatabase();
        String[] data={String.valueOf(id)};
        Cursor curs = techStore.rawQuery("SELECT * FROM products WHERE id =? ",data);
        if (curs!=null)
            curs.moveToFirst();
        techStore.close();
        return curs;
    }
    public Cursor SearchByName(String Name) {
        techStore = getReadableDatabase();
        Cursor curs = techStore.rawQuery("SELECT * FROM products WHERE name Like '%" + Name + "%'", null);
        if (curs!=null) {
            curs.moveToFirst();
        }
        techStore.close();
        return curs;
    }
    /*----------------------------------------------------------------------------------------------------------------------*/
    /*-----------------------------------------------categories table functions-----------------------------------------------*/
    public Cursor returnCategories() {
        techStore = getReadableDatabase();
        Cursor curs = techStore.rawQuery("SELECT * FROM categories ",null);
        if (curs!=null)
            curs.moveToFirst();
        techStore.close();
        return curs;
    }
    /*------------------------------------------------------------------------------------------------------------------------*/
    /*-----------------------------------------------Order table functions-----------------------------------------------*/
    public boolean AddItem(int id,String email) {
        boolean isFound=false;
        Cursor cursor= getUserByEmail(email);
        int userId=cursor.getInt(0);
        cursor.close();
        int orderId=checkOrder(userId);
        if(orderId==-1)
        {
            addOrder(userId);
            orderId=checkOrder(userId);
        }
        if(!checkItem(id,orderId)) {
            techStore = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("order_id", orderId);
            values.put("product_id", id);
            values.put("quantity", 1);
            techStore.insert("order_details", null, values);
            techStore.close();
        }
        else
            isFound=true;
        return isFound;

    }
    public int checkOrder(int id) {
        int orderId=-1;
        techStore= this.getReadableDatabase();
        Cursor cursor = techStore.rawQuery("SELECT * FROM orders WHERE user_id= "+id+" and submit='waiting'", null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            orderId = cursor.getInt(0);
        }
        cursor.close();
        techStore.close();
        return orderId;
    }

    public void addOrder(int id)
    {
        techStore=getWritableDatabase();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        ContentValues values = new ContentValues();
        values.put("date", currentDate);
        values.put("user_id", id);
        values.put("address", "");
        values.put("total_price", "0");
        values.put("submit", "waiting");
        techStore.insert("orders", null, values);
        techStore.close();
    }

    public boolean checkItem(int id, int orderId) {
        boolean isFound=false;
        techStore= this.getReadableDatabase();
        Cursor cursor = techStore.rawQuery("SELECT * FROM order_details WHERE product_id= "+id+" and order_id= "+orderId, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            isFound=true;
        }
        cursor.close();
        techStore.close();
        return isFound;
    }

    public Cursor returnCartItems(String email) {
        Cursor cursor= getUserByEmail(email);
        int userId=cursor.getInt(0);
        cursor.close();
        int orderId=checkOrder(userId);
        Cursor curs = null;
        if(orderId!=-1)
        {
            techStore = getReadableDatabase();
            curs = techStore.rawQuery("SELECT * FROM products Inner join order_details on order_details.product_id = products.id   WHERE order_details.order_id = "+orderId,null );
            if (curs!=null)
                curs.moveToFirst();
            techStore.close();
        }

        return curs;
    }
    public Cursor returnSubmitedOrders(int orderId) {

        techStore = getReadableDatabase();
        Cursor curs = techStore.rawQuery("SELECT * FROM products Inner join order_details  on order_details.product_id = products.id  Inner join orders  on order_details.order_id = orders.id WHERE orders.submit = 'done' and orders.id="+orderId+"",null );
        if (curs!=null)
            curs.moveToFirst();
        techStore.close();
        return curs;
    }
    public Cursor returnWaitingOrders(int orderId) {

        techStore = getReadableDatabase();
        Cursor curs = techStore.rawQuery("SELECT * FROM products Inner join order_details  on order_details.product_id = products.id  Inner join orders  on order_details.order_id = orders.id WHERE orders.submit = 'waiting' and orders.id="+orderId+"",null );
        if (curs!=null)
            curs.moveToFirst();
        techStore.close();
        return curs;
    }

    public void increaseQuantity(int id,int ordrId,int quantity)
    {
        techStore=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity+1);
        techStore.update("order_details",values, "order_id= "+ordrId+" and product_id= "+id,null);
        techStore.close();
    }
    public void decreaseQuantity(int id,int ordrId,int quantity)
    {
        techStore=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity-1);
        techStore.update("order_details",values, "order_id= "+ordrId+" and product_id= "+id,null);
        techStore.close();
    }
    public void deleteItem(int id,int ordrId)
    {
        techStore = getWritableDatabase();
        techStore.delete("order_details","order_id = " +ordrId + " and product_id= "+id, null);
        techStore.close();
    }

    public Cursor returnOrders(String email) {
        Cursor cursor= getUserByEmail(email);
        int userId=cursor.getInt(0);
        cursor.close();
        techStore = getReadableDatabase();
        Cursor curs = techStore.rawQuery("SELECT * FROM orders where user_id ="+userId,null);
        if (curs!=null)
            curs.moveToFirst();
        techStore.close();
        return curs;
    }
    public void submit(int id,String address,double totalPrice)
    {

        bestSellerUpdate(id);
        techStore=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("address",address);
        values.put("total_price", String.valueOf(totalPrice));
        values.put("submit","done");
        techStore.update("orders", values,"id= "+id,null );
        techStore.close();
    }
    public void bestSellerUpdate(int orderId)
    {
        Cursor cursor=returnWaitingOrders(orderId);
        techStore=getWritableDatabase();
        ContentValues values;
        for(int i=0;i<cursor.getCount();i++)
        {
            values= new ContentValues();
            values.put("quantity",(cursor.getInt(4)-cursor.getInt(9)));
            values.put("best_seller",(cursor.getInt(6)+cursor.getInt(9)));
            techStore.update("products", values,"id= "+cursor.getInt(0),null );
            cursor.moveToNext();
        }


        techStore.close();
    }
    /*-------------------------------------------------------------------------------------------------------------------*/

}
