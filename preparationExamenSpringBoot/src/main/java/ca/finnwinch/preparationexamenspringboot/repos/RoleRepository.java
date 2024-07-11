package ca.finnwinch.preparationexamenspringboot.repos;

import ca.finnwinch.preparationexamenspringboot.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {
    public Role findRoleByName(String name);
}