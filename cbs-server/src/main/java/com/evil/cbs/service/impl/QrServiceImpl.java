package com.evil.cbs.service.impl;

import com.evil.cbs.service.QrService;
import com.evil.cbs.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrServiceImpl implements QrService {

    private final ServletContext servletContext;
    private final FileUtil fileUtil;

    @Override
    public File generateQrCodeImage(String content) {
        File qrFile = fileUtil.createTempFile();
        ByteArrayOutputStream stream = QRCode.from(content)
                .withSize(200, 200)
                .to(ImageType.PNG)
                .stream();
        writeTo(stream, qrFile.getAbsolutePath());
        log.info("qrFile.getAbsolutePath() - " + qrFile.getAbsolutePath());
        String destination = null;
        destination = getClass().getResource("/images").getPath().replace("file:/", "");
        log.info("destination - " + destination);
        return moveFile(qrFile, destination);
    }

    private File moveFile(File qrFile, String destination) {
        try {
            log.info("qrFile - " + qrFile);
            String filePath = destination + "/" + qrFile.getName();
            log.info("destination - " + destination);
            log.info("qrFile.getName() - " + qrFile.getName());
            log.info("filePath - " + filePath);
            if (!Files.exists(Paths.get(filePath)))
                Files.createDirectories(Paths.get(filePath).getParent());
            Files.move(qrFile.toPath(), Paths.get(filePath));
            qrFile.delete();
            return new File(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeTo(ByteArrayOutputStream stream, String filePath) {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            out.write(stream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
