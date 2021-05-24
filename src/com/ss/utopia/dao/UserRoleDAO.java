package com.ss.utopia.dao;

import com.ss.utopia.entity.Route;
import com.ss.utopia.entity.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO extends BaseDAO<UserRole>{


    public UserRoleDAO(Connection conn) {
        super(conn);
    }

    public void addUserRole(UserRole role) throws ClassNotFoundException, SQLException {
        save("INSERT into user_role (name) values (?)",
                new Object[] { role.getRoleName() });
    }
    public void updateUserRole(UserRole role) throws ClassNotFoundException, SQLException {
        save("UPDATE user_role set name = ? where id = ?", new Object[] {
                role.getRoleName(), role.getId() });
    }
    public void deleteRole(UserRole role) throws ClassNotFoundException, SQLException {
        save("delete from user_role where id = ?", new Object[] { role.getId() });
    }
    public List<UserRole> readAllRoles() throws ClassNotFoundException, SQLException {
        return read("select * from user_role", null);
    }

    @Override
    public List<UserRole> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<UserRole> roles = new ArrayList<>();
        while (rs.next()) {
            UserRole role = new UserRole();
            role.setId(rs.getInt("id"));
            role.setRoleName(rs.getString("name"));
            roles.add(role);
        }
        return roles;
    }
}
