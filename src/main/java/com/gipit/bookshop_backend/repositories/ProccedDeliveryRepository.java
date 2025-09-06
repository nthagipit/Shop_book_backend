package com.gipit.bookshop_backend.repositories;

import com.gipit.bookshop_backend.models.ProccedDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProccedDeliveryRepository extends JpaRepository<ProccedDelivery, Integer> {
}
