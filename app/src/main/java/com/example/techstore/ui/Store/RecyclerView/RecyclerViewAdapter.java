package com.example.techstore.ui.Store.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Product> products;
    private List<Category> categories;
    private List<Order> orders;
    private ProductClickListener productClickListener;
    private int i=0;
    View view;

    ArrayList<Product> orderProducts;
    RecyclerViewAdapter recyclerViewAdapter;
    DbHelper dbHelper;
    RecyclerView recyclerView;

    public RecyclerViewAdapter(Context context, List<Product> products, ProductClickListener productClickListener, int i) {
        this.context = context;
        this.products = products;
        this.productClickListener = productClickListener;
        this.i=i;
    }
    public RecyclerViewAdapter(Context context, List<Category> categories, ProductClickListener productClickListener) {
        this.context = context;
        this.categories = categories;
        this.productClickListener = productClickListener;
    }
    public RecyclerViewAdapter(int i,Context context,List<Order> orders,ProductClickListener productClickListener) {
        this.context = context;
        this.orders = orders;
        this.productClickListener = productClickListener;
        this.i=i;
        orderProducts = new ArrayList<>();
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(i==3)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false);

        else if(i==2) //shopping cart
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);

        else //i==1(products) or categories
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);


        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(i==1) //products
        {
            Product product = products.get(position);
            holder.itemName.setText(product.getName());
            int resourceIdentifier = holder.itemView.getResources().getIdentifier(product.getImage(), "drawable", holder.itemView.getContext().getPackageName());
            holder.productImage.setImageResource(resourceIdentifier);
        }
        else if(i==2) //shopping cart
        {
            Product product = products.get(position);
            holder.cartItemName.setText(product.getName());
            holder.cartItemPrice.setText(product.getPrice()+" EGP");
            holder.cartItemQuantity.setText(String.valueOf(product.getQuantity()));
            int resourceIdentifier = holder.itemView.getResources().getIdentifier(product.getImage(), "drawable", holder.itemView.getContext().getPackageName());
            holder.cartProductImage.setImageResource(resourceIdentifier);

        }
        else if(i==3) //orders
        {
            Order order = orders.get(position);
            holder.orderId.setText(String.valueOf(order.getId()));
            holder.orderDate.setText(order.getDate());
            holder.orderAddress.setText(order.getAddress());
            holder.orderTotalPrice.setText(order.getTotal_price());
            Cursor cursor = dbHelper.returnSubmitedOrders(order.getId());
            orderProducts.clear();
            for (int i=0;i<cursor.getCount();i++)
            {
                orderProducts.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3) ));
                for(int j=1;j<cursor.getInt(9);j++)
                    orderProducts.add(orderProducts.get(i));
                cursor.moveToNext();
            }
            cursor.close();

            recyclerViewAdapter = new RecyclerViewAdapter(context, orderProducts, productClickListener,1);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 20));
            recyclerView.setAdapter(recyclerViewAdapter);


        }
        else //categories
        {
            Category category = categories.get(position);
            holder.itemName.setText(category.getName());
            int resourceIdentifier = holder.itemView.getResources().getIdentifier(category.getImage(), "drawable", holder.itemView.getContext().getPackageName());
            holder.productImage.setImageResource(resourceIdentifier);
        }
    }

    @Override
    public int getItemCount() {
        if(i==0)
            return categories.size();
        else if(i==3)
            return orders.size();
        else
            return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName ;
        ImageView productImage;


        TextView cartItemName , cartItemPrice ,cartItemQuantity;
        ImageView cartProductImage;
        Button deleteFromShoppingCart ,increase,decrease;

        TextView orderId,orderDate,orderAddress,orderTotalPrice;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            if(i==2)
            {
                cartItemName=itemView.findViewById(R.id.cart_item_name);
                cartItemPrice=itemView.findViewById(R.id.cart_item_price);
                cartItemQuantity=itemView.findViewById(R.id.cart_item_quantity);
                deleteFromShoppingCart=itemView.findViewById(R.id.cart_delete);
                increase=itemView.findViewById(R.id.cart_increase_products);
                decrease=itemView.findViewById(R.id.cart_decrease_products);
                cartProductImage=itemView.findViewById(R.id.cart_product_image);
            }
            else if(i==3)
            {
                orderId=itemView.findViewById(R.id.order_id);
                orderDate=itemView.findViewById(R.id.order_date);
                orderAddress=itemView.findViewById(R.id.order_address);
                orderTotalPrice=itemView.findViewById(R.id.order_total_price);
                recyclerView=itemView.findViewById(R.id.recycler_view_item);



            }
            else
            {
                itemName = itemView.findViewById(R.id.item_name);
                productImage=itemView.findViewById(R.id.product_image);
            }
            if(i!=3) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getAdapterPosition() != -1) {
                            productClickListener.onRecyclerViewClick(getAdapterPosition(), i);
                        }
                    }
                });

                if (i == 2) {
                    increase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getAdapterPosition() != -1) {

                                productClickListener.onIncreaseClick(getAdapterPosition());

                            }
                        }
                    });
                    decrease.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getAdapterPosition() != -1) {

                                productClickListener.onDecreaseClick(getAdapterPosition());

                            }
                        }
                    });
                    deleteFromShoppingCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getAdapterPosition() != -1) {

                                productClickListener.onDeleteClick(getAdapterPosition());

                            }
                        }
                    });
                }
            }

        }
    }
}
