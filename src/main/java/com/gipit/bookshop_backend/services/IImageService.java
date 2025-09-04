package com.gipit.bookshop_backend.services;

import com.gipit.bookshop_backend.dto.Image.ImageDTO;
import com.gipit.bookshop_backend.models.Book;

import java.util.List;

public interface IImageService {
    public void saveImage(List<ImageDTO> listImage, Book book);
}
