package com.gdsdxy.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.gdsdxy.dto.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static com.gdsdxy.constants.SystemConstants.UPLOAD_IMAGE_PATH;

public class UploadUtils {
    /**
     * 保存文件
     * @param file 上传文件
     * @param savePath 保存路径
     * @return ResultVo
     */
    public static ResultVo saveFile(MultipartFile file, String savePath) {
        try {
            // 判断文件是否为空
            if (file.isEmpty()) {
                return ResultVo.fail("文件未上传！");
            }

            // 获取文件原始名称以及后缀名
            String originalFilename = file.getOriginalFilename();
            String fileName = createNewFileName(originalFilename, savePath);

            // 保存文件
            file.transferTo(new File(UPLOAD_IMAGE_PATH, fileName));
            // 返回文件保存路径
            return ResultVo.ok(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            // 失败，返回错误信息
            return ResultVo.fail("文件上传失败！");
        }
    }

    /**
     * 删除文件
     * @param fileName 文件名称
     * @return ResultVo
     */
    public static ResultVo deleteFile(String fileName) {
        File file = new File(UPLOAD_IMAGE_PATH, fileName);
        if (file.isDirectory()) {
            return ResultVo.fail("错误的文件名称!");
        }
        FileUtil.del(file);
        return ResultVo.ok();
    }

    /**
     * 获取图片
     * @param fileName 文件名称
     * @param response 响应头
     */
    public static void getImage(String fileName, HttpServletResponse response) {
        if (StrUtil.isBlank(fileName)) {
            return;
        }


        File imageFile = new File(UPLOAD_IMAGE_PATH, fileName);
        if (imageFile.exists()) {
            FileInputStream fis = null;
            OutputStream os = null;
            try {
                fis = new FileInputStream(imageFile);
                os = response.getOutputStream();
                int count = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                    os.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static String createNewFileName(String originalFilename, String savePath) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        // 生成目录
        String name = UUID.randomUUID().toString();
        int hash = name.hashCode();
        int d1 = hash & 0xF;
        int d2 = (hash >> 4) & 0xF;
        // 判断目录是否存在
        File dir = new File(UPLOAD_IMAGE_PATH, StrUtil.format(savePath + "/{}/{}", d1, d2));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成文件名
        return StrUtil.format(savePath +"/{}/{}/{}.{}", d1, d2, name, suffix);
    }
}
