package com.cookswp.milkstore.repository.post;

import com.cookswp.milkstore.pojo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.visibility = true")
    List<Post> findAllVisibility();

    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.visibility = true")
    Post findByIDAndVisibility(@Param("id") int id);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN false ELSE true END FROM Post p WHERE p.title = :title")
    boolean titleMustBeUnique(@Param("title") String title);

}
