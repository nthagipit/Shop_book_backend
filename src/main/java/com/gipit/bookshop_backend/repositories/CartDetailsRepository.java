package com.gipit.bookshop_backend.repositories;

import com.gipit.bookshop_backend.models.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer> {
    CartDetails findByCartDetailsID(int cartDetailsID);
}
