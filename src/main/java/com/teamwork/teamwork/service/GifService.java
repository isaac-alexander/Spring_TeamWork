package com.teamwork.teamwork.service;

import com.teamwork.teamwork.entity.Gif;

import java.util.List;

public interface GifService {

    void saveGif(Gif gif);  // create or update GIF

    List<Gif> getAllGifs();    // list all GIFs, newest first

    void deleteGif(Long id);   // delete GIF

    Gif getGifById(Long id);   // get by ID

    void addComment(Long gifId, String comment); // add comment
}
