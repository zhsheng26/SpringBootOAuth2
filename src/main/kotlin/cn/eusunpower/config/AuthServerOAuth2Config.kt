package cn.eusunpower.config

import cn.eusunpower.model.entity.UserEntity
import cn.eusunpower.repository.UserRepository
import cn.eusunpower.support.YClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableResourceServer
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(YClient.CLIENT_ID)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.anonymous().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
    }

}

@Configuration
@EnableAuthorizationServer
class AuthServerOAuth2Config : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var redisConnectionFactory: RedisConnectionFactory

    @Autowired
    @Qualifier("userDetailsService")
    lateinit var userDetailsService: UserDetailsService

    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    /**
    clientId：（必须的）用来标识客户的Id。
    secret：（需要值得信任的客户端）客户端安全码，如果有的话。
    scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
    authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
    authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     */
    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient(YClient.CLIENT_ID)
                .secret(YClient.CLIENT_PW)
                .scopes("get", "post", "delete", "put", "all")
                .authorizedGrantTypes("password", "refresh_token")
//                .accessTokenValiditySeconds(30)
//                .refreshTokenValiditySeconds(2592000) // 30 days
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
    }


    @Bean
    fun tokenStore(): TokenStore {
        return RedisTokenStore(redisConnectionFactory)
    }
}

@Component
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }

}

interface IAuthenticationFacade {
    fun getAuthentication(): Authentication
    fun getCurrentUser(): UserEntity
}

@Component
class AuthenticationFacade : IAuthenticationFacade {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

    override fun getCurrentUser(): UserEntity {
        val account = getAuthentication().name
        return userRepository.findUserEntityByAccount(account)
    }
}