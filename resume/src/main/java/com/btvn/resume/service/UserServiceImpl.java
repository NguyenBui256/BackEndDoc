package com.btvn.resume.service;

import com.btvn.resume.dao.UserRepository;
import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.NotFoundException;
import com.btvn.resume.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.net.http.HttpHeaders;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final DataSource dataSource;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ObjectMapper objectMapper;

    @NonFinal // inject vao constructor
    protected static final String SIGNER_KEY="yB9rjD+FPPAcft7XY34vX9lffE+nPr2jQUm4r/mvN5kLlY74L6kqINrPPEMv30fO";
    private final ObjectMapper jacksonObjectMapper;

    @Override
    public Page<User> findAll(int size, int page, String sortBy) {
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.min(Math.max(size,1),20), Sort.Direction.ASC, sortBy);
        List<User> userList = userRepository.findAll();
        return new PageImpl<>(userList, pageable, userList.size());
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with the given ID."));
    }

    @Override
    public User findByUsername(String name) {

        return userRepository.findByUsername(name);
    }

    @Override
    public String findSelfInfo(String token) throws ParseException, JOSEException, JsonProcessingException {
//        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

//        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // kiem tra het han
//        if (expiryTime.after(new Date()) && !signedJWT.verify(verifier)) {
//            throw new NotFoundException("Expired or invalid JWT");
//        }
        User userString = (User) signedJWT.getJWTClaimsSet().getClaims().get("information");
        log.error(String.valueOf(userString));
        return signedJWT.getJWTClaimsSet().getStringClaim("infomation");
    }

    @Override
    public User save(User user) {
        User userInDb = findByUsername(user.getUsername());
        if(userInDb != null) {
            throw new NotFoundException("Username already exists.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }


    @Override
    public void update(int id, UserDTO user) {

    }

    @Override
    public void deleteById(int id) {
        Optional<User> project = userRepository.findById(id);
        if (project.isEmpty()) throw new NotFoundException("User not found with the given ID.");
        userRepository.delete(project.get());
    }

    @Override
    public String login(String username, String password) throws JsonProcessingException {
        User userInDb = findByUsername(username);
        if(userInDb == null) {
            throw new NotFoundException("Username already exists.");
        }
        if(!bCryptPasswordEncoder.matches(password, userInDb.getPassword())) {
            throw new NotFoundException("Bad credentials.");
        }
        return generateToken(userInDb);
    }

    @Override
    public void logout() {

    }

    private String generateToken(User user) throws JsonProcessingException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//        String userJson = objectMapper.writeValueAsString(user);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("localhost:8080")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("information", user)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private boolean verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // kiem tra het han

        return expiryTime.after(new Date()) && signedJWT.verify(verifier);
    }
}
