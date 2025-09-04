package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.dto.Image.ImageDTO;
import com.gipit.bookshop_backend.models.Book;
import com.gipit.bookshop_backend.models.Image;
import com.gipit.bookshop_backend.repositories.ImageRepository;
import com.gipit.bookshop_backend.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void saveImage(List<ImageDTO> listImage, Book book) {
        for (ImageDTO imageDTO : listImage) {
            Image image=Image.builder()
                    .imageName(imageDTO.getImageName())
                    .isIcon(imageDTO.isIcon())
                    .dataImage(imageDTO.getDataImage())
                    .link(imageDTO.getLink())
                    .book(book)
                    .build();
            imageRepository.save(image);
        }
    }
}
