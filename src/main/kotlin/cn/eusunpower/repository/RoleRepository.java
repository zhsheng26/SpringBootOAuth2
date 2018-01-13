package cn.eusunpower.repository;

import cn.eusunpower.model.entity.RoleEntity;
import cn.eusunpower.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findRoleEntityByName(String name);

    /**
     * 通过用户ID查询此用户的角色(返回的是角色列表)
     */
    @Query("SELECT r FROM RoleEntity r JOIN r.users u WHERE u.id=:id")
    List<RoleEntity> findAllByUserId(@Param("id") String id);

    /**
     * 删除用户的所有角色
     */
    void removeRoleEntitiesByUsersContains(UserEntity user);

    /**
     * 查询用户角色的个数
     */
    @Query("SELECT COUNT(r) FROM RoleEntity r JOIN r.users u WHERE u.id=:id")
    Long countRolesByUserId(@Param("id") String uid);
}
