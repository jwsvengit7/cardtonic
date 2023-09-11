package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.token.JwtToken;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Integer> {
    @Query(value = """
      select t from JwtToken t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<JwtToken> findAllValidTokenByUser(Long id);

    Optional<JwtToken> findByToken(String token);



    JwtToken findByUser(User user);

    @Query(value = "DELETE FROM jwttoken WHERE id =?1",nativeQuery = true)
    JwtToken deleteToken(Long id);

}
