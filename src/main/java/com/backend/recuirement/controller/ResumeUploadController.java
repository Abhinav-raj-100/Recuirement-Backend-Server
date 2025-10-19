package com.backend.recuirement.controller;

import com.backend.recuirement.service.UploadResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload-resume")
@PreAuthorize("hasRole('APPLICANT')")
public class ResumeUploadController {

    @Autowired
    private UploadResumeService uploadResumeService;

    @PostMapping("/")
    public ResponseEntity<String> uploadResume(@RequestParam("file")MultipartFile file,
                                               Authentication authentication)
    {
        String email = authentication.getName();
        String response = this.uploadResumeService.uploadResumeAsync(file,email);
        return ResponseEntity.ok(response);
    }
}
