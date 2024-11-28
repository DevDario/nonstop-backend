package ao.com.non_stop.nonstopplatformapi.infra.security;

import ao.com.non_stop.nonstopplatformapi.domain.actors.User;
import ao.com.non_stop.nonstopplatformapi.repositories.UsersRepository;
import ao.com.non_stop.nonstopplatformapi.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = this.extractToken(request);

            if (token != null) {
                String email = this.tokenService.extractEmail(token);
                User currrentUser = this.usersRepository.findByEmail(email).orElseThrow();

                if(this.tokenService.isTokenValid(token,currrentUser)) {
                    UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails((new WebAuthenticationDetailsSource().buildDetails(request)));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.substring(7);
    }
}
