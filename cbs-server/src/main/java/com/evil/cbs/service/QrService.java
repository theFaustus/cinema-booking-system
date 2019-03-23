package com.evil.cbs.service;

import java.io.File;

public interface QrService {
  File generateQrCodeImage(String content);
}
