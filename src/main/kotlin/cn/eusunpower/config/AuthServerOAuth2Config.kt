package cn.eusunpower.config

import cn.eusunpower.support.YClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
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


@Configuration
@EnableAuthorizationServer
class AuthServerOAuth2Config : AuthorizationServerConfigurerAdapter() {

    @Autowired
    var authenticationManager: AuthenticationManager? = null
    @Autowired
    var redisConnectionFactory: RedisConnectionFactory? = null

    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer
                .allowFormAuthenticationForClients()
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
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
                .scopes("select")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(30)
                .refreshTokenValiditySeconds(2592000) // 30 days
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                .authenticationManager(authenticationManager)
    }


    @Bean
    fun tokenStore(): TokenStore {
        return RedisTokenStore(redisConnectionFactory)
    }

}
//
//@Configuration
//@EnableResourceServer
//class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {
//    override fun configure(resources: ResourceServerSecurityConfigurer?) {
//        resources!!.resourceId(YClient.CLIENT_ID).stateless(true)
//    }
//
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                .requestMatchers().anyRequest()
//                .and()
//                .anonymous()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/product/**").access("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
//                .antMatchers("/order/**").authenticated()//配置order访问控制，必须认证过后才可以访问
//    }
//}