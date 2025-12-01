package latihan.gradle.api.config;

// 1. Import Repository dan Model Anda
import latihan.gradle.api.repository.UserRepository;

// 2. Import Spring Security

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import latihan.gradle.api.model.Users;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Izinkan POST ke /users (Daftar User Baru)
                .requestMatchers(HttpMethod.POST, "/users").permitAll() 
                // Sisanya wajib login
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            // A. Cari user di database (pakai User model kita)
            Users userKita = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan: " + username));

            // B. Konversi ke User Spring Security
            return User.builder()
                .username(userKita.getUsername())
                .password(userKita.getPassword())
                .roles(userKita.getRole().name()) // Ambil role (ADMIN/USER)
                .build();
        };
    }

    // PENTING: Bean ini wajib ada agar password bisa dicocokkan
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}