package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.entity.Album;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAlbumService extends IService<Album> {
}
