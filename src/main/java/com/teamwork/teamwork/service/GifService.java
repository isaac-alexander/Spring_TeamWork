package com.teamwork.teamwork.service;

import com.teamwork.teamwork.entity.Gif;

import java.util.List;

public interface GifService {

    void saveGif(Gif gif);

    List<Gif> getAllGifs();
}
