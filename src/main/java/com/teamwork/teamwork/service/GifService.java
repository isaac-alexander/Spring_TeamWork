package com.teamwork.teamwork.service;

import com.teamwork.teamwork.entity.Gif;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GifService {

    // save gif and upload file
    void saveGif(Gif gif, MultipartFile file);  // create or update GIF

    List<Gif> getAllGifs();    // list all GIFs, newest first

    void deleteGif(Long id);   // delete GIF

    Gif getGifById(Long id);   // get by ID

    void addComment(Long gifId, String comment); // add comment

}
