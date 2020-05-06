package com.curacel.vehiclelisting.config;

import com.curacel.vehiclelisting.SpringApplicationContext;
import com.curacel.vehiclelisting.model.Users;
import com.curacel.vehiclelisting.pojo.LoginDetailsPojo;
import com.curacel.vehiclelisting.pojo.UserProfileResponsePojo;
import com.curacel.vehiclelisting.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Gson gson = new Gson();
    private final AuthenticationManager manager;

    public AuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {

        try {
            // contentType = req.getHeader("Accept");
            LoginDetailsPojo creds = new ObjectMapper().readValue(req.getInputStream(), LoginDetailsPojo.class);

            if (creds == null) {
                throw new UsernameNotFoundException("Invalid username up/password");

            }
            List<GrantedAuthority> auth = new ArrayList<>();

            return manager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), auth));
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType(MediaType.ALL_VALUE);
            try {
                Map m = new HashMap();
                m.put("code", -1);
                m.put("message", "unsuccessful try, invalid username or password \n");
                String str = gson.toJson(m);
                PrintWriter pr = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                pr.write(str);
            } catch (IOException ex1) {
                return null;
            }
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException, SignatureException {

        String userName = ((User) auth.getPrincipal()).getUsername();

        String token = Jwts.builder().setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.getSecret()).compact();

        UsersRepository userLoginRepo = (UsersRepository) SpringApplicationContext.getBean("usersRepository");

        Users user = userLoginRepo.findByUsername(userName).get();

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);


        Map m = new HashMap();
        m.put("code", 0);
        m.put("message", "Login successful");
        m.put("token", SecurityConstants.TOKEN_PREFIX + token);

        List<String> roles = new ArrayList<>();


        UserProfileResponsePojo userp = new ModelMapper().map(user, UserProfileResponsePojo.class);

        m.put("user", userp);
        String str = gson.toJson(m);
        PrintWriter pr = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        pr.write(str);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.ALL_VALUE);
        Map m = new HashMap();
        m.put("code", -1);
        m.put("message", "usuccessful, invalid username or password");
        String str = gson.toJson(m);
        PrintWriter pr = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        pr.write(str);
    }
}
