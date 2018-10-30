package ua.pp.ssenko.stories.config

import org.springframework.beans.factory.annotation.Configurable
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


/**
 * Modifying or overriding the default spring boot security.
 */
@Configurable
@EnableWebSecurity
@EnableOAuth2Sso
@Order(1000)
class OAuthSecurityConfig(
        private val oauth2ClientContext: OAuth2ClientContext,
        private val authorizationCodeResourceDetails: AuthorizationCodeResourceDetails,
        private val resourceServerProperties: ResourceServerProperties
) : WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        super.configure(web)
        //web.ignoring().antMatchers("/", "/ui");
    }

    override fun configure(http: HttpSecurity) {

        http
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/**", "/**.html", "/**.js", "/**.css").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .addFilterAt(filter(), BasicAuthenticationFilter::class.java)
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().exceptionHandling()
                .defaultAuthenticationEntryPointFor(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        AntPathRequestMatcher("/api/**"));
    }

    private fun filter(): OAuth2ClientAuthenticationProcessingFilter {
        val oAuth2Filter = OAuth2ClientAuthenticationProcessingFilter("/google/login")
        val oAuth2RestTemplate = OAuth2RestTemplate(authorizationCodeResourceDetails, oauth2ClientContext)
        oAuth2Filter.setRestTemplate(oAuth2RestTemplate)
        oAuth2Filter.setTokenServices(UserInfoTokenServices(resourceServerProperties.userInfoUri,
                resourceServerProperties.clientId))
        return oAuth2Filter
    }

}
