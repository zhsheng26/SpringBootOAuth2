package cn.eusunpower.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.http.HttpMethod
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthServerOAuth2Config : AuthorizationServerConfigurerAdapter() {

    @Autowired
    var authenticationManager: AuthenticationManager? = null
    @Autowired
    var redisConnectionFactory: RedisConnectionFactory? = null
    @Autowired
    private val env: Environment? = null

    @Throws(Exception::class)
    override fun configure(
            oauthServer: AuthorizationServerSecurityConfigurer?) {
        oauthServer!!
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource())
                .withClient("clientIdPassword")
                .secret("secret")
                .authorizedGrantTypes(
                        "password", "authorization_code", "refresh_token")
                .scopes("read","write")
                .accessTokenValiditySeconds(30)
                .refreshTokenValiditySeconds(2592000) // 30 days
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager)
    }


    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env!!.getProperty("jdbc.driverClassName"))
        dataSource.url = env!!.getProperty("jdbc.url")
        dataSource.username = env!!.getProperty("jdbc.user")
        dataSource.password = env!!.getProperty("jdbc.pass")
        return dataSource
    }

    @Bean
    fun tokenStore(): TokenStore {
        return RedisTokenStore(redisConnectionFactory)
    }
}