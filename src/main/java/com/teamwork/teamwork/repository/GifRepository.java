package com.teamwork.teamwork.repository;

import com.teamwork.teamwork.entity.Gif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GifRepository extends JpaRepository<Gif, Long> {

    List<Gif> findAllByOrderByCreatedAtDesc();

}
