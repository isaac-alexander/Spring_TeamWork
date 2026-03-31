package com.teamwork.teamwork.service.impl;

import com.teamwork.teamwork.entity.Gif;
import com.teamwork.teamwork.repository.GifRepository;
import com.teamwork.teamwork.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GifServiceImpl implements GifService {

    @Autowired
    private GifRepository gifRepository;

    @Override
    public void saveGif(Gif gif) {
        if (gif.getCreatedAt() == null) {
            gif.setCreatedAt(LocalDateTime.now());
        }
        if (gif.getComments() == null) {
            gif.setComments(new ArrayList<>());
        }
        gifRepository.save(gif);
    }

    @Override
    public List<Gif> getAllGifs() {
        return gifRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public void deleteGif(Long id) {
        gifRepository.deleteById(id);
    }

    @Override
    public Gif getGifById(Long id) {
        return gifRepository.findById(id).orElse(null);
    }

    @Override
    public void addComment(Long gifId, String comment) {
        Gif gif = gifRepository.findById(gifId).orElse(null);
        if (gif != null) {
            if (gif.getComments() == null) {
                gif.setComments(new ArrayList<>());
            }
            gif.getComments().add(comment);
            gifRepository.save(gif);
        }
    }
}
