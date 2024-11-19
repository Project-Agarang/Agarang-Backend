package org.ku.agarangproject.global.s3.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.ku.agarangproject.global.common.exception.exception.BusinessException;
import org.ku.agarangproject.global.common.exception.exception.FileException;
import org.ku.agarangproject.global.common.model.dto.BaseResponseStatus;
import org.ku.agarangproject.global.s3.dto.S3File;
import org.ku.agarangproject.global.s3.enums.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class S3FileUtil {

    @Value("${aws.s3.upload.tempPath}")
    private String tempPath;

    public S3File convert(MultipartFile file) {
        ContentType contentType = getContentType(file)
                .orElseThrow(() -> new BusinessException(BaseResponseStatus.INVALID_FILE_EXTENSION));
        try {
            return S3File.builder()
                    .filename(createCleanedFilename(file.getOriginalFilename(), contentType))
                    .contentType(contentType)
                    .contentLength(file.getSize())
                    .bytes(file.getBytes())
                    .build();
        } catch (IOException e) {
            throw new FileException(BaseResponseStatus.FAIL_FILE_READ);
        }
    }

    private Path createDirectory(String path) throws IOException {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        return directory;
    }

    public void delete(Path target) {
        if (target != null && Files.exists(target)) {
            try {
                Files.delete(target);
            } catch (IOException e) {
                log.error("로컬 파일 삭제에 실패했습니다.");
            }
        }
    }

    public S3File downloadAudioUrl(String urlString) {
        Path target = null;
        try {
            Path directory = createDirectory(tempPath);
            String filename = createFilename("download.mp3");

            target = directory.resolve(filename); // ./temp/uuid_download.mp3

            URL url = new URL(urlString);
            Files.copy(url.openStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return S3File.builder()
                    .filename(ContentType.MP3.getPath() + filename)
                    .contentType(ContentType.MP3)
                    .contentLength(Files.size(target))
                    .bytes(Files.readAllBytes(target))
                    .build();
        } catch (IOException e) {
            throw new FileException(BaseResponseStatus.FAIL_FILE_READ);
        } finally {
            delete(target);
        }
    }

    private Optional<ContentType> getContentType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return ContentType.of(extension);
    }

    private String createCleanedFilename(String filename, ContentType contentType) {
        // 모든 (/), (\), ( ) -> (_) 로 대체
        String cleanedFilename = filename.replaceAll("[/\\\\\\s]+", "_");
        return contentType.getPath() + createFilename(cleanedFilename);
    }

    private String createFilename(String filename) {
        return UUID.randomUUID() + "_" + filename;
    }
}
