package com.gdsdxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.entity.Album;
import com.gdsdxy.mapper.AlbumMapper;
import com.gdsdxy.service.IAlbumService;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements IAlbumService {
}
