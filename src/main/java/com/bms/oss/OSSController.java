package com.bms.oss;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static com.bms.common.domain.Result.ok;

/**
 * OSS文件对象管理.
 *
 * @author luojimeng
 * @date 2020/3/13
 */
@RestController
@RequestMapping("/oss")
@RequiredArgsConstructor
@EnableConfigurationProperties(OSSProperties.class)
public class OSSController {
    private static final Logger logger = LoggerFactory.getLogger(OSSController.class);
    private final OSSProperties ossProperties;

    @RequiresAuthentication
    @PostMapping("/{dir}")
    public Result<FileInfo> upload(@PathVariable String dir,
                                   MultipartFile file,
                                   HttpServletRequest request) throws IOException {
        if (request.getContentType().contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            String text = request.getParameter("text");
            String suffix = request.getParameter("suffix");

            String md5 = DigestUtils.md5Hex(text.getBytes());
            String sha1 = DigestUtils.sha1Hex(text.getBytes());

            String fmtday = DateFormatUtils.format(new Date(), Constant.DATE_FORMAT_YYYYMMDD);
            Path path = Paths.get(ossProperties.getRepo(), dir, fmtday);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path dest = Paths.get(path.toString(), md5 + suffix);
            FileInfo info = new FileInfo();
            info.setFilename(dest.toString().replace(ossProperties.getRepo(), ""));
            info.setOriginalname("");
            info.setMd5(md5);
            info.setSha1(sha1);
            info.setMimetype("text/" + dir);
            info.setSize(text.length());

            Files.write(dest, text.getBytes());
            return ok(info);
        }
        if (request.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            if (file.isEmpty()) {
                throw ErrorCodes.build(ErrorCodes.OSS_FILE_EMPTY);
            }
            String fmtday = DateFormatUtils.format(new Date(), Constant.DATE_FORMAT_YYYYMMDD);
            Path path = Paths.get(ossProperties.getRepo(), dir, fmtday);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String md5 = DigestUtils.md5Hex(file.getBytes());
            String sha1 = DigestUtils.sha1Hex(file.getBytes());

            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            Path dest = Paths.get(path.toString(), md5 + suffix);
            FileInfo info = new FileInfo();
            info.setFilename(dest.toString().replace(ossProperties.getRepo(), ""));
            info.setOriginalname(file.getOriginalFilename());
            info.setMd5(md5);
            info.setSha1(sha1);
            info.setMimetype(file.getContentType());
            info.setSize(file.getSize());
            file.transferTo(dest);
            return ok(info);
        }
        throw ErrorCodes.build(ErrorCodes.OSS_CONTENT_TYPE_UNSUPPORTED);
    }

    @GetMapping("/{dir}/{fmtday}/{filename}")
    public void get(@PathVariable String dir,
                    @PathVariable String fmtday,
                    @PathVariable String filename,
                    HttpServletResponse response) throws IOException {
        Path path = Paths.get(ossProperties.getRepo(), dir, fmtday, filename);
        if (!Files.exists(path)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String contentType = getContentType(path);
        response.setHeader("content-type", contentType);
        response.setContentType(contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (StringUtils.equals(contentType, MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        }
        Files.copy(path, response.getOutputStream());
    }

    @RequiresAuthentication
    @DeleteMapping("/{dir}/{fmtday}/{filename}")
    public Result<Void> delete(@PathVariable String dir,
                               @PathVariable String fmtday,
                               @PathVariable String filename,
                               HttpServletResponse response) throws IOException {
        Path path = Paths.get(ossProperties.getRepo(), dir, fmtday, filename);
        if (!Files.exists(path)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        Files.delete(path);
        return ok();
    }

    @Data
    private static class FileInfo {
        private String mimetype;
        private String filename;
        private String originalname;
        private long size;
        private String sha1;
        private String md5;
    }

    public static String getContentType(Path path) {
        try {
            String type = Files.probeContentType(path);
            logger.info("path:{} type:{}", path, type);
            if (type == null) {
                MimetypesFileTypeMap map = new MimetypesFileTypeMap(path.toString());
                type = map.getContentType(path.toFile());
                logger.info("path:{} type:{}", path, type);
                return type;
            }
        } catch (IOException e) {
            logger.error("get filename:" + path + " content type error", e);
        }
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}
