package com.kev.styleupphysiospine.cart;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface CartDao {

    @Insert
    public void addToCart(Cart cart);

    @Query("SELECT * FROM MyCart")
    public List<Cart> getData();

    @Query("SELECT EXISTS (SELECT 1 FROM mycart WHERE id=:id)")
    public int isAddToCart(int id);

    @Query("select COUNT (*) from MyCart")
    int countCart();

    @Query("DELETE FROM MyCart WHERE id=:id ")
    int deleteItem(int id);

}
