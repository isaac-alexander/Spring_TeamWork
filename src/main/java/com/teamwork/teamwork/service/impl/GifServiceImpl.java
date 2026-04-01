package com.teamwork.teamwork.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.teamwork.teamwork.entity.Gif;
import com.teamwork.teamwork.repository.GifRepository;
import com.teamwork.teamwork.service.GifService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GifServiceImpl implements GifService {

    // repository - talks to database final means it won't change
    private final GifRepository gifRepository;

    // cloudinary - handles image upload
    private final Cloudinary cloudinary;

    public GifServiceImpl(GifRepository gifRepository, Cloudinary cloudinary) {
        this.gifRepository = gifRepository;
        this.cloudinary = cloudinary;
    }

    //  create gif / upload
    @Override
    public void saveGif(Gif gif, MultipartFile file) {

        try {
            // upload file to cloudinary
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(), // convert file to bytes
                    ObjectUtils.asMap(
                            "resource_type", "auto", // supports gif/image
                            "folder", "gifs" // store inside gifs folder
                    )
            );

            // get URL from cloudinary
            gif.setImageUrl((String) uploadResult.get("secure_url"));

            // get public ID (used for delete)
            gif.setPublicId((String) uploadResult.get("public_id"));

            // set time created
            gif.setCreatedAt(LocalDateTime.now());

            // save to database
            gifRepository.save(gif);

        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }


    //  get all gif
    @Override
    public List<Gif> getAllGifs() {
        // Calls repository method
        // SELECT * FROM gif ORDER BY created_at DESC
        return gifRepository.findAllByOrderByCreatedAtDesc();
    }

    // delete gif
    @Override
    public void deleteGif(Long id) {

        //finds gif in database
        Gif gif = gifRepository.findById(id).orElse(null);

        // If gif exists
        if (gif != null) {
            try {
                // delete image from cloudinary using publicId
                cloudinary.uploader().destroy(
                        gif.getPublicId(), // unique cloudinary id
                        ObjectUtils.asMap("resource_type", "auto")
                );
            } catch (Exception ignored) {
            }
            // delete gif record from database
            gifRepository.deleteById(id);
        }
    }

    // get single gif by id
    @Override
    public Gif getGifById(Long id) {

        // findById if not found - return null
        return gifRepository.findById(id).orElse(null);
    }

    // add comment to gif
    @Override
    public void addComment(Long gifId, String comment) {

        // find gif using id
        Gif gif = gifRepository.findById(gifId).orElse(null);

        // If gif exists
        if (gif != null) {

            // If comments list is null - create new list
            if (gif.getComments() == null) {
                gif.setComments(new ArrayList<>());
            }

            // add new comment to list
            gif.getComments().add(comment);

            // save updated gif back to database
            gifRepository.save(gif);
        }
    }
}
