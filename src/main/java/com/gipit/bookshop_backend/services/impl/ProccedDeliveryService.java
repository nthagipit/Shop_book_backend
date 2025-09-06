package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.ProccedDelivery;
import com.gipit.bookshop_backend.repositories.ProccedDeliveryRepository;
import com.gipit.bookshop_backend.services.IProccedDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProccedDeliveryService implements IProccedDeliveryService {
    @Autowired
    private ProccedDeliveryRepository proccedDeliveryRepository;

    @Override
    public ProccedDelivery getProccedDeliveryById(int id) {
        return proccedDeliveryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PROCCED_DELIVERY_NOT_FOUND));
    }
}
