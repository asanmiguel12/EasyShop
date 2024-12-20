package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    ShoppingCart updateCart(int userId, int productId, int i);

    ShoppingCart addProductToCart(int userId, int id);


    void clearCart (int userId);


}
