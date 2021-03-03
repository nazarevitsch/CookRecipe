package com.bida.testtask.repository;

import com.bida.testtask.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

    List<Recipe> findAll();

    Recipe getById(Long id);

    @Modifying
    @Transactional
    @Query(value = "update recipes\n" +
            "set image_link = :link\n" +
            "where id = :id",
            nativeQuery = true)
    void setImageLinkById(Long id, String link);
}
