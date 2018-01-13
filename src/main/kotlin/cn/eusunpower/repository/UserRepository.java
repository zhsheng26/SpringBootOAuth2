package cn.eusunpower.repository;

import cn.eusunpower.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findUserEntityByAccount(String account);
}
