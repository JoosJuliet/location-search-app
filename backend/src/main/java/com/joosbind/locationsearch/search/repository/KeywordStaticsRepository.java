package com.joosbind.locationsearch.search.repository;

import com.joosbind.locationsearch.search.entity.KeywordStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface KeywordStaticsRepository extends JpaRepository<KeywordStatistics, Long> {
    Optional<KeywordStatistics> findBySearchword(String keyword);
}
