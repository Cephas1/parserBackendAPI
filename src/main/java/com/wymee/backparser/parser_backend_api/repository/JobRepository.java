package com.wymee.backparser.parser_backend_api.repository;

import com.wymee.backparser.parser_backend_api.model.Job;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface JobRepository extends JpaRepository<Job, Long> {

//    public ResponseEntity<Optional<Job>> getByReff

      @Query(value = "SELECT * FROM Jobs WHERE title = ?1 OR description = ?1 OR company = ?1", nativeQuery = true)
      public List<Job> findAllByReff(String reff);

}
