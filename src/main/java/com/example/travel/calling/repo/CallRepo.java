package com.example.travel.calling.repo;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.calling.entity.Call;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CallRepo extends JpaRepository<Call, Long> {
    boolean existsByCalleeAndStatus(UserEntity callee, Call.Status status);
    Optional<Call> findByCalleeAndStatus(UserEntity callee, Call.Status status);
    boolean existsByCalleeAndCallerAndStatus(UserEntity callee, UserEntity caller, Call.Status status);
    Optional<Call> findByCalleeAndCallerAndStatus(UserEntity callee, UserEntity caller, Call.Status status);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Call> findById(Long id);

    @Query("SELECT DISTINCT c FROM Call c " +
            "WHERE (c.status = 'Connecting' OR c.status = 'OnCall') AND " +
            "(c.caller = :user OR c.callee = :user)")
    Optional<Call> checkConnect(@Param("user") UserEntity user);
//    boolean existsByStatusAndCallee()
}
