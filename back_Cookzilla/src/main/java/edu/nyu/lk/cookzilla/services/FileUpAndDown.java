package edu.nyu.lk.cookzilla.services;

import edu.nyu.lk.cookzilla.utils.ImagesProperties;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUpAndDown {

    @Autowired
    private ImagesProperties imagesProperties;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, Object> uploadPicture(MultipartFile file) throws Exception{
        try {
            Map<String, Object> resMap = new HashMap<>();
            String[] IMAGE_TYPE = imagesProperties.getImageType().split(",");
            String path = null;
            boolean flag = false;
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                resMap.put("result", "success");
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                // 获得文件类型
                String fileType = file.getContentType();
                // 获得文件后缀名称
                String imageName = fileType.substring(fileType.indexOf("/") + 1);
                // 原名称
                String oldFileName = file.getOriginalFilename();
                // 新名称
                String newFileName = uuid + "." + imageName;
                // 年月日文件夹
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String basedir = sdf.format(new Date());
//                String path2 = GetResource.class.getClassLoader().getResource("static/images").getPath();
//                imagesProperties.setUpPath(path2);
                // 进行压缩(大于4M)
                if (file.getSize() > Long.valueOf(imagesProperties.getFileSize())) {
                    // 重新生成
                    String newUUID = UUID.randomUUID().toString().replaceAll("-", "");
                    newFileName = newUUID + "." + imageName;
//                    path = imagesProperties.getUpPath() + "/" + basedir + "/" + newUUID + "." + imageName;
                    path = imagesProperties.getUpPath() + "\\" + basedir + "\\" + newUUID + "." + imageName;
//                    path = path2 + "/" + basedir + "/" + newUUID + "." + imageName;
                    logger.info(path);
                    // 如果目录不存在则创建目录
                    File oldFile = new File(path);
                    if (!oldFile.exists()) {
                        oldFile.mkdirs();
                    }
                    file.transferTo(oldFile);
                    // 压缩图片
                    Thumbnails.of(oldFile).scale(Double.valueOf(imagesProperties.getScaleRatio())).toFile(path);
                    // 显示路径
                    resMap.put("path", "\\" + basedir + "\\" + newUUID + "." + imageName);
                } else {
                    path = imagesProperties.getUpPath() + "\\" + basedir + "\\" + uuid + "." + imageName;
//                    path = path2 + "/" + basedir + "/" + uuid + "." + imageName;
                    logger.info(path);
                    // 如果目录不存在则创建目录
                    File uploadFile = new File(path);
                    if (!uploadFile.exists()) {
                        uploadFile.mkdirs();
                    }
                    file.transferTo(uploadFile);
                    // 显示路径
                    resMap.put("path", "\\" + basedir + "\\" + uuid + "." + imageName);
                }
                logger.info("old file"+ oldFileName);
                logger.info("new file"+ newFileName);
                resMap.put("oldFileName", oldFileName);
                resMap.put("newFileName", newFileName);
                resMap.put("fileSize", file.getSize());
            } else {
                resMap.put("result", "failed");
            }
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

}
