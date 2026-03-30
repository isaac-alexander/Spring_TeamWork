package com.teamwork.teamwork.service.impl;

import com.teamwork.teamwork.entity.Gif;
import com.teamwork.teamwork.repository.GifRepository;
import com.teamwork.teamwork.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GifServiceImpl implements GifService {

    @Autowired
    private GifRepository gifRepository;

    @Override
    public void saveGif(Gif gif) {
        gif.setCreatedAt(LocalDateTime.now());
        gifRepository.save(gif);
    }

    @Override
    public List<Gif> getAllGifs() {
        return gifRepository.findAllByOrderByCreatedAtDesc();
    }
}
