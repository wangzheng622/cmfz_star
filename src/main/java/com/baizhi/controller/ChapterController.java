package com.baizhi.controller;


import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import it.sauronsoftware.jave.Encoder;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("chapter")
@RestController
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(Integer page,Integer rows,String albumId){
        return chapterService.selectChapterByAlbumId(page,rows,albumId);

    }

    @RequestMapping("edit")
    public Map<String,Object> edit(Chapter chapter,String oper){
        Map<String, Object> map = new HashMap<>();
        try {
            if("add".equals(oper)){
                //            tianjia
                String id = chapterService.add(chapter);
                map.put("status",true);
                map.put("message",id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile name,String id,String albumId,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try{
            File file = new File(request.getServletContext().getRealPath("album/music"),name.getOriginalFilename());
            name.transferTo(file);

            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setName(name.getOriginalFilename());

            BigDecimal size = new BigDecimal(name.getSize());
            BigDecimal mod = new BigDecimal(1024);
            BigDecimal realSize = size.divide(mod).divide(mod).setScale(2,BigDecimal.ROUND_HALF_UP);
            chapter.setSize(realSize+"MB");

            Encoder encoder = new Encoder();
            long duration = encoder.getInfo(file).getDuration();
            chapter.setDuration(duration/1000/60+":"+duration/1000%60);
            chapterService.edit(chapter);

            Album album =albumService.selectOne(albumId);
            album.setCount(album.getCount()+1);
            albumService.edit(album);
            map.put("status",true);

        }catch(Exception e){
            e.printStackTrace();
            map.put("status",false);

        }
        return map;
    }

}
