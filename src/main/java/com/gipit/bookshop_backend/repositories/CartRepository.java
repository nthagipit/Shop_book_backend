package com.gipit.bookshop_backend.repositories;

import com.gipit.bookshop_backend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser_UserID(int userID);
}
