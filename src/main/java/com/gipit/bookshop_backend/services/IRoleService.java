package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.models.Role;

public interface IRoleService {
    Role findRoleById(int id);
    Role findRoleByRoleName(String roleName);
}
