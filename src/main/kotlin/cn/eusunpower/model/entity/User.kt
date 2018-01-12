package cn.eusunpower.model.entity

import cn.eusunpower.model.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "yl_user")
data class UserEntity(
        @Column(nullable = false, unique = true)
        var account: String = "",
        @Column(nullable = false, unique = true)
        var nickname: String = "",
        var password: String = "",
        var mobile: String = ""
) : BaseEntity()

@Entity
@Table(name = "yl_role")
data class RoleEntity(
        @Column(nullable = false, unique = true)
        var name: String = "",
        @ManyToMany
        @JoinTable(name = "user_role",
                joinColumns = [(JoinColumn(name = "userId"))],
                inverseJoinColumns = [(JoinColumn(name = "roleId"))])
        var users: List<UserEntity> = ArrayList()
) : BaseEntity()