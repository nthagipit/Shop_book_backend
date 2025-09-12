package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.Role;
import com.gipit.bookshop_backend.repositories.RoleRepository;
import com.gipit.bookshop_backend.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
    }
}
