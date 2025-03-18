package com.example.vacancy_search.controllers;

import com.example.vacancy_search.domain.Resume;
import com.example.vacancy_search.services.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Optional;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @GetMapping("/create")
    public String resume(Model model) {
        model.addAttribute("resume", new Resume());
        return "resume";
    }

    @PostMapping("/create")
    public String createResume(@RequestParam("position") String position,
                               @RequestParam("description") String description,
                               @RequestParam("workExperience") Double workExperience,
                               @RequestPart("file") MultipartFile file,
                               Model model) throws IOException {
        Resume resume = Resume.builder()
                .position(position)
                .description(description)
                .workExperience(workExperience)
                .build();

        if (!file.isEmpty()) {
            resume.setFile(file.getBytes()); // Сохраняем файл в базу данных
            resume.setFileName(file.getOriginalFilename()); // Сохраняем исходное имя файла
        } else {
            model.addAttribute("fileError", "Файл не может быть пустым");
            return "resume";
        }

        resumeService.save(resume);
        return "redirect:/user/profile";
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Optional<Resume> optionalResume = resumeService.findById(id);

        if (!optionalResume.isPresent() || optionalResume.get().getFile() == null) {
            return ResponseEntity.notFound().build();
        }

        Resume resume = optionalResume.get();

        // Определяем тип содержимого (MIME type) по имени файла
        String mimeType = URLConnection.guessContentTypeFromName(resume.getFileName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resume.getFile());
    }
    @GetMapping("/watch")
    public String resumeWatch(Model model) {
        model.addAttribute("resume", new Resume());
        return "resumeWatch";
    }

}
