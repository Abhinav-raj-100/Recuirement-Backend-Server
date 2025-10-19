package com.backend.recuirement.service;


import com.backend.recuirement.entities.Profile;
import com.backend.recuirement.entities.User;
import com.backend.recuirement.repository.ProfileRepository;
import com.backend.recuirement.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class UploadResumeService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);


    @Value("${resume.parser.api.url}")
    private String resumeParserApiUrl;

    @Value("${resume.parser.api.key}")
    private String resumeParserApiKey;



    private boolean isValidResumeFile(MultipartFile file)
    {
        if(file == null || file.isEmpty())
        {
            return false;
        }

        String fileName = file.getOriginalFilename();

        if(fileName==null)
        {
            return false;
        }

        String lowerName = fileName.toLowerCase();
        return lowerName.endsWith(".pdf") || lowerName.endsWith(".docx");
    }

    public String uploadResumeAsync(MultipartFile multipartFile,String email)
    {
        if(!isValidResumeFile(multipartFile))
        {
            return "Invalid file type . Only PDF and DOCX are allowed";
        }

        Optional<User> user = this.userRepository.findByEmail(email);

        if(user.isEmpty())
        {
            return "User not found with this email : "+email;
        }

        User getUser = user.get();

        Profile profile = this.profileRepository.findByApplicantId(getUser.getId()).orElse(new Profile());

        profile.setApplicant(getUser);
        profile.setResumeUrl("/uploads/"+multipartFile.getOriginalFilename());

        this.profileRepository.save(profile);

        executorService.submit(()->parseResume(multipartFile,profile));

        return "Resume uploaded Successfully. Parsing in background";
    }

    private void parseResume(MultipartFile file, Profile profile) {
        try {
            // Determine content type based on file extension
            MediaType mediaType;
            String filename = file.getOriginalFilename();
            if (filename == null) {
                log.warn("File name is null for user {}", profile.getApplicant().getEmail());
                return;
            }

            filename = filename.toLowerCase();
            if (filename.endsWith(".pdf")) {
                mediaType = MediaType.APPLICATION_PDF;
            } else if (filename.endsWith(".docx") || filename.endsWith(".doc")) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            } else {
                log.warn("Unsupported file type for user {}: {}", profile.getApplicant().getEmail(), filename);
                return;
            }

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.set("apikey", resumeParserApiKey);

            // Create HTTP entity with file bytes
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

            // Send POST request to resume parser API
            ResponseEntity<Map> response = restTemplate.exchange(
                    resumeParserApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Process response
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> parsed = response.getBody();

                profile.setSkills(listToString(parsed.get("skills")));
                profile.setExperience(listToString(parsed.get("experience")));
                profile.setEducation(listToString(parsed.get("education")));
                profile.setPhone(listToString(parsed.get("phone")));

                profileRepository.save(profile);
                log.info("Resume parsed successfully for user {}", profile.getApplicant().getEmail());
            } else {
                log.warn("Resume parsing failed for user {}: {}", profile.getApplicant().getEmail(),
                        response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Exception during resume parsing for user {}: {}", profile.getApplicant().getEmail(),
                    e.getMessage(), e);
        }
    }

    private String listToString(Object value) {
        if (value == null) return null;

        if (value instanceof String str) {
            return str;
        }

        if (value instanceof Iterable<?> iterable) {
            StringBuilder sb = new StringBuilder();
            for (Object item : iterable) {
                if (!sb.isEmpty()) sb.append(", ");
                sb.append(item != null ? item.toString() : "");
            }
            return sb.toString();
        }

        // Handle arrays as well
        if (value.getClass().isArray()) {
            Object[] arr = (Object[]) value;
            return String.join(", ", Arrays.stream(arr)
                    .map(obj -> obj != null ? obj.toString() : "")
                    .toArray(String[]::new));
        }

        return value.toString();
    }




}
