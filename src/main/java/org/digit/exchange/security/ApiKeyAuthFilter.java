// package org.digit.exchange.security;

// import java.io.IOException;

// import org.digit.exchange.service.OrganisationService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import org.digit.exchange.constant.Error;

// @Component
// public class ApiKeyAuthFilter extends OncePerRequestFilter {

//     @Autowired
//     private OrganisationService organisationService;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         String apiKey = request.getHeader("Authorization");
//         if (apiKey == null || !organisationService.isValidApiKey(apiKey)) {
//             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Error.INVALID_API_KEY);
//             return;
//         }
//         filterChain.doFilter(request, response);
//     }
// }

