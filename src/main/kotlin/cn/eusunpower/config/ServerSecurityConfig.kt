package cn.eusunpower.config

import cn.eusunpower.model.entity.RoleEntity
import cn.eusunpower.repository.RoleRepository
import cn.eusunpower.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.annotation.Resource
import javax.transaction.Transactional

@Configuration
@EnableWebSecurity
class ServerSecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/oauth/*").permitAll()
                .anyRequest()
                .authenticated()
    }

}

@Transactional
@Service("userDetailService")
class YlUserDetailService : UserDetailsService {
    @Resource
    lateinit var userRepository: UserRepository
    @Resource
    lateinit var roleRepository: RoleRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(account: String): UserDetails {
        val user = userRepository.findUserEntityByAccount(account) ?:
                return org.springframework.security.core.userdetails.User(
                        " ", " ", false, true, true, true,
                        getAuthorities(listOf(roleRepository.findRoleEntityByName("ROLE_USER"))))
        val roles = roleRepository.findAllByUserId(user.id)
        return org.springframework.security.core.userdetails.User(
                user.account, user.password, !user.deleted, true, true,
                true, getAuthorities(roles))
    }

    private fun getAuthorities(
            roles: Collection<RoleEntity>): ArrayList<GrantedAuthority> {
        return getGrantedAuthorities(roles)
    }

    private fun getGrantedAuthorities(roles: Collection<RoleEntity>): ArrayList<GrantedAuthority> {
        val authorities = arrayListOf<GrantedAuthority>()
        roles.forEach {
            authorities.add(SimpleGrantedAuthority(it.name))
        }
        return authorities
    }

}