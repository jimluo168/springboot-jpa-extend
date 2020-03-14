package com.bms.oss;

import com.bms.common.config.redis.RedisProperties;
import com.bms.common.domain.Result;
import com.bms.common.exception.ServiceException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static com.bms.common.domain.Result.ok;
import static com.bms.common.exception.ExceptionFactory.OSS_FILE_EMPTY;

/**
 * .
 *
 * @author luojimeng
 * @date 2020/3/13
 */
@RestController
@RequestMapping("/oss")
@RequiredArgsConstructor
@EnableConfigurationProperties(OSSProperties.class)
public class OSSController {
    private final OSSProperties ossProperties;

    @PostMapping("/{dir}")
    public Result<FileInfo> upload(@PathVariable String dir,
                                   MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new ServiceException(OSS_FILE_EMPTY, "文件内容无效");
        }
        String fmtday = DateFormatUtils.format(new Date(), "yyyyMMdd");
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
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        Files.copy(path, response.getOutputStream());
    }

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
}