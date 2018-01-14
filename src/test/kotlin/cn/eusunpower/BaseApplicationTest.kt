package cn.eusunpower

import cn.eusunpower.model.entity.RoleEntity
import cn.eusunpower.model.entity.UserEntity
import cn.eusunpower.repository.RoleRepository
import cn.eusunpower.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class BaseApplicationTest {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var passwordEncode: PasswordEncoder

    @Test
    fun contextLoads() {
        val userEntity = UserEntity(account = "zhsheng",
                nickname = "晟",
                password = passwordEncode.encode("123456"),
                mobile = "13688888888"
        )
//        userRepository.save(userEntity)
        val account = userRepository.findUserEntityByAccount("zhsheng")

        val roleEntity = RoleEntity()
        roleEntity.name = "ROLE_USER"
        roleEntity.users = roleEntity.users.plus(account)
        roleRepository.save(roleEntity)

    }

}
