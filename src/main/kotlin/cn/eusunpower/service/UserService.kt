package cn.eusunpower.service

import cn.eusunpower.model.entity.UserEntity
import cn.eusunpower.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun getUserEntityByAccount(account: String): UserEntity? {
        return userRepository.findUserEntityByAccount(account)
    }

    fun getUserEntityByUid(uid: String): UserEntity? = userRepository.findOne(uid)
}