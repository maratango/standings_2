package org.xcompany.xprojects.standings_2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.xcompany.xprojects.standings_2.entity.UserApp;

import java.util.Optional;

@Repository
public interface UserAppRepository extends CrudRepository<UserApp, Long> {

    Optional<UserApp> findByLoginAndPassword(String login, String password);

    @Query(nativeQuery = true, value = "" +
            "select * from server.fn_checkAndAddUser(:login, :password, null::::text)"
    )
    boolean createNewUser(@Param("login") String login, @Param("password") String password);
}
