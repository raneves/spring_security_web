package med.voll.web_application.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //classe de configuracao do spring e ele quem gerencia as configuracoes
@EnableWebSecurity
public class ConfiguracoesSeguranca {
	
	//@Bean 
	//public UserDetailsService dadosUsuariosCadastrados(){
   //     UserDetails usuario1 = User.builder()
    //            .username("joao@email.com")
    //            .password("{noop}joao123")//o prefixo {noop} é usado para indicar que a senha não deve ser criptografada ou codificada de nenhuma forma.
   //             .build();
   //     UserDetails usuario2 = User.builder()
   //             .username("maria@email.com")
  //              .password("{noop}maria123")
  //              .build();
  //      return new InMemoryUserDetailsManager(usuario1, usuario2);
  //  }


	
	//a senha eh joao123, e criptografada ficou update usuarios set senha = '$2y$10$GE/j8ZVFApRU8Av7Bq4sO3/PbSYWon2MG2oscE2B7nxPwCEGI2yu' where id=1;
    //foi utilizado o site https://bcrypt.online/ para criptografar a senha joao@123
	
	
	@Bean
    public PasswordEncoder codificadorSenha(){
		//login joao@email.com
		//senha: joao123
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
		return http
	            .authorizeHttpRequests(req -> {
	                    req.requestMatchers("/css/**", "/js/**", "/assets/**").permitAll();
	                    req.anyRequest().authenticated();
	                })
	            .formLogin(form -> form.loginPage("/login")
	                    .defaultSuccessUrl("/")
	                    .permitAll())
	            .logout(logout -> logout
	                    .logoutSuccessUrl("/login?logout")
	                    .permitAll())
	            .rememberMe(rememberMe -> rememberMe.key("lembrarDeMim").alwaysRemember(true))
	            .csrf(Customizer.withDefaults())
	            .build();
	}
}
