package com.example.Complaints.Management.System.core.infrastructure.Repository;

import com.example.Complaints.Management.System.core.domain.entities.Complaint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint,Long> {
    // Get the oldest complaint’s creation date (for default cursor)
    @Query("SELECT MIN(c.creationDate) FROM Complaint c")
    Optional<Date> findOldestCreationDate();

    @Query("SELECT c FROM Complaint c WHERE c.user.id = :userId " +
            "AND c.creationDate > :lastCursor " +
            "AND (:currentStatus IS NULL OR c.currentStatus = :currentStatus) " +
            "ORDER BY c.creationDate ASC")
    Slice<Complaint> findNextComplaints(
            @Param("userId") Long userId,
            @Param("currentStatus") String currentStatus,
            @Param("lastCursor") Date lastCursor,
            Pageable pageable);


    @Query(value = """
    SELECT * FROM complaint 
    WHERE user_id = :userId 
    AND ( :currentStatus IS NULL OR current_status = :currentStatus )
    AND creation_date > :lastCursor
    ORDER BY creation_date ASC
    LIMIT :pageSize 
""", nativeQuery = true)
    List<Complaint> findUserNextComplaintsNative(
            @Param("userId") Long userId,
            @Param("currentStatus") String currentStatus,
            @Param("lastCursor") Date lastCursor,
            @Param("pageSize") int pageSize);

    @Query(value = """
    SELECT * FROM complaint 
    WHERE admin_id = :userId 
    AND ( :currentStatus IS NULL OR current_status = :currentStatus )
    AND creation_date > :lastCursor
    ORDER BY creation_date ASC
    LIMIT :pageSize 
""", nativeQuery = true)
    List<Complaint> findAdminNextComplaintsNative(
            @Param("userId") Long userId,
            @Param("currentStatus") String currentStatus,
            @Param("lastCursor") Date lastCursor,
            @Param("pageSize") int pageSize);

    @Query(value = """
    SELECT * FROM (
        SELECT * FROM complaint 
        WHERE user_id = :userId 
        AND ( :currentStatus IS NULL OR current_status = :currentStatus )
        AND creation_date < :lastCursor
        ORDER BY creation_date DESC
        LIMIT :pageSize
    ) AS subquery
    ORDER BY creation_date ASC
""", nativeQuery = true)
    List<Complaint> findUserPrevComplaintsNative(
            @Param("userId") Long userId,
            @Param("currentStatus") String currentStatus,
            @Param("lastCursor") Date lastCursor,
            @Param("pageSize") int pageSize);


    @Query(value = """
    SELECT * FROM (
        SELECT * FROM complaint 
        WHERE Admin_id = :userId 
        AND ( :currentStatus IS NULL OR current_status = :currentStatus )
        AND creation_date < :lastCursor
        ORDER BY creation_date DESC
        LIMIT :pageSize
    ) AS subquery
    ORDER BY creation_date ASC
""", nativeQuery = true)
    List<Complaint> findAdminPrevComplaintsNative(
            @Param("userId") Long userId,
            @Param("currentStatus") String currentStatus,
            @Param("lastCursor") Date lastCursor,
            @Param("pageSize") int pageSize);


}
