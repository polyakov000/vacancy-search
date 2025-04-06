package com.example.vacancy_search.controllers;

import com.example.vacancy_search.config.SecurityUtils;
import com.example.vacancy_search.config.UserDetailsService;
import com.example.vacancy_search.domain.Candidate;
import com.example.vacancy_search.domain.Resume;
import com.example.vacancy_search.domain.User;
import com.example.vacancy_search.services.ResumeService;
import com.example.vacancy_search.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    ResumeService resumeService;
    @Autowired
    UserService userService;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @GetMapping("/create")
    public String resume(Model model) {
        model.addAttribute("resume", new Resume());
        model.addAttribute("user",userService.findByUsername(SecurityUtils.getCurrentUsername()));
        return "resume";
    }

    @PostMapping("/create")
    public String createResume(@RequestParam("position") String position,
                               @RequestParam("description") String description,
                               @RequestParam("workExperience") Double workExperience,
                               @RequestPart("file") MultipartFile file,
                               Model model) throws IOException {
        Candidate candidate = (Candidate) userService.findByUsername(SecurityUtils.getCurrentUsername());
        Resume resume = Resume.builder()
                .position(position)
                .description(description)
                .workExperience(workExperience)
                .candidate(candidate)
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
    @Transactional
    public String resumeWatch(Model model) {
        Candidate candidate = (Candidate) userService.findByUsername(SecurityUtils.getCurrentUsername());
        List<Resume> resume = resumeService.findAllByCandidate(candidate);
        User user = userService.findByUsername(SecurityUtils.getCurrentUsername());
        model.addAttribute("resumes", resume);
        model.addAttribute("user",user);
        return "resumeWatch";
    }
    @GetMapping("/delete/{id}")
    public String resumeDelete( @PathVariable Long id){
        resumeService.deleteById(id);
        return "redirect:/resume/watch";
    }

    @PostMapping("/edit")
    public String resumeEdit(@RequestParam("id") Long id,
                             @RequestParam("position") String position,
                             @RequestParam("description") String description,
                             @RequestParam("workExperience") Double workExperience,
                             @RequestParam(value = "file", required = false) MultipartFile file) {
        Resume existingResume = resumeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Резюме не найдено"));

        // Обновляем существующую сущность
        existingResume.setPosition(position);
        existingResume.setDescription(description);
        existingResume.setWorkExperience(workExperience);

        // Если файл был загружен, обновляем его
        if (file != null && !file.isEmpty()) {
            try {
                existingResume.setFile(file.getBytes());
                existingResume.setFileName(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при обработке файла", e);
            }
        }

        // Сохраняем изменения
        resumeService.save(existingResume);

        return "redirect:/resume/watch";
    }


}
