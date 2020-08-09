package com.dbc;

import com.dbc.InF.CartItemDBCInF;
import com.dbc.InF.UserDBCInF;
import com.jdbc.JDBCConnection;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.model.User;
import com.service.CartService;
import com.service.InF.CartInF;
import com.service.InF.ProductInF;
import com.service.ProductService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemDBC extends JDBCConnection implements CartItemDBCInF {
    CartInF cartService = new CartService();
    ProductInF productService = new ProductService();
    UserDBCInF userDBC = new UserDBC();

    @Override
    public void insert(CartItem cartItem) {
        String sql = "INSERT INTO CartItem(id, quanty, unitPrice, cat_id, pro_id) VALUES (?,?,?,?,?);";
        Connection con = super.getJDBCConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cartItem.getId());
            ps.setInt(2, cartItem.getQuantity());
            ps.setLong(3, cartItem.getUnitPrice());
            ps.setString(4, cartItem.getCart().getId());
            ps.setInt(5, cartItem.getProduct().getId());

            ps.executeUpdate();

//			ResultSet generatedKeys = ps.getGeneratedKeys();
//			if (generatedKeys.next()) {
//				int id = generatedKeys.getInt(1);
//				cartItem.setId(id);
//			}


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(CartItem cartItem) {
        String sql = "UPDATE CartItem SET cat_id = ?, pro_id = ?, quanty = ?, unitPrice=? WHERE id = ?;";
        Connection con = super.getJDBCConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cartItem.getCart().getId());
            ps.setInt(2, cartItem.getProduct().getId());
            ps.setInt(3, cartItem.getQuantity());
            ps.setLong(4, cartItem.getUnitPrice());
            ps.setString(5, cartItem.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM CartItem WHERE id = ?;";
        Connection con = super.getJDBCConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CartItem get(String name) {
        return null;
    }

    @Override
    public CartItem get(int id) {
        String sql = "SELECT CartItem.id,CartItem.quanty,CartItem.unitPrice,cart.u_id,cart.buyDate,product.name,product.price " +
                "FROM CartItem INNER JOIN Cart ON CartItem.cart_id = cart.id " +
                "INNER JOIN Product ON CartItem.pro_id = Product.id " +
                "WHERE CartItem.id = ?;";
        Connection con = super.getJDBCConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = userDBC.get(rs.getInt("u_id"));

                Cart cart = new Cart();
                cart.setBuyer(user);
                cart.setBuyDate(rs.getDate("buyDate"));

                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setPrice(rs.getLong("price"));

                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(rs.getInt("quanty"));
                cartItem.setUnitPrice(rs.getLong("unitPrice"));


                return cartItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartItem> getAll() {
        List<CartItem> cartItemList = new ArrayList<>();
        String sql = "SELECT CartItem.id, CartItem.quanty, CartItem.unitPrice, cart.u_id, cart.buyDate, product.name, product.price " +
                "FROM CartItem " +
                "INNER JOIN Cart " +
                "ON CartItem.cat_id = Cart.id " +
                "INNER JOIN Product " +
                "ON CartItem.pro_id = Product.id ;";
        Connection con = super.getJDBCConnection();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = userDBC.get(rs.getInt("u_id"));

                Cart cart = new Cart();
                cart.setBuyer(user);
                cart.setBuyDate(rs.getDate("buyDate"));

                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setPrice(rs.getLong("price"));

                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getString("id"));
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(rs.getInt("quanty"));
                cartItem.setUnitPrice(rs.getLong("unitPrice"));

                cartItemList.add(cartItem);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItemList;
    }

    @Override
    public List<CartItem> search(String name) {
        return null;
    }
}
