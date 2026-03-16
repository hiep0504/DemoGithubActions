package com.example.PhamDinhHiep.controller;

import com.example.PhamDinhHiep.model.Profile;
import com.example.PhamDinhHiep.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.util.List;

@Controller
public class homeController {

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Profile> list = profileRepository.findAll();
        if (list.isEmpty()) {
            Profile p = new Profile(
                    "Phạm Đình Hiệp",
                    "hiep542004s@gmail.com",
                    "Xin chào, tôi là Phạm Đình Hiệp, một sinh viên đam mê công nghệ và lập trình. Tôi yêu thích việc khám phá các công nghệ mới và luôn cố gắng học hỏi để phát triển kỹ năng của mình. Ngoài ra, tôi cũng rất thích tham gia vào các dự án mã nguồn mở và chia sẻ kiến thức với cộng đồng.",
                    "Backend Developer",
                    "Java, Node.js",
                    "MySQL",
                    "Website hồ sơ cá nhân có thể chỉnh sửa thông tin"
            );
            profileRepository.save(p);
            list = profileRepository.findAll();
        }
        Profile profile = list.get(0);
        boolean changed = false;
        if (profile.getPosition() == null || profile.getPosition().isBlank()) {
            profile.setPosition("Backend Developer");
            changed = true;
        }
        if (profile.getLanguages() == null || profile.getLanguages().isBlank()) {
            profile.setLanguages("Java, Node.js");
            changed = true;
        }
        if (profile.getDatabaseName() == null || profile.getDatabaseName().isBlank()) {
            profile.setDatabaseName("MySQL");
            changed = true;
        }
        if (profile.getProject() == null || profile.getProject().isBlank()) {
            profile.setProject("Website hồ sơ cá nhân có thể chỉnh sửa thông tin");
            changed = true;
        }
        if (changed) {
            profile = profileRepository.save(profile);
        }
        model.addAttribute("profile", profile);
        return "home";
    }

    @GetMapping("/profile/edit")
    public String editForm(Model model) {
        List<Profile> list = profileRepository.findAll();
        Profile profile;
        if (list.isEmpty()) {
            profile = new Profile();
        } else {
            profile = list.get(0);
        }
        model.addAttribute("profile", profile);
        return "edit";
    }

    @PostMapping("/profile/save")
    public String saveProfile(@ModelAttribute Profile profile) {
        profileRepository.save(profile);
        return "redirect:/";
    }

    // --- JSON API endpoints ---
    @GetMapping("/api/profile")
    public @ResponseBody ResponseEntity<Profile> apiGetProfile() {
        List<Profile> list = profileRepository.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(list.get(0));
    }

    @PostMapping("/api/profile")
    public @ResponseBody ResponseEntity<Profile> apiSaveProfile(@RequestBody Profile profile) {
        Profile saved = profileRepository.save(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/api/profile")
    public @ResponseBody ResponseEntity<Void> apiDeleteProfile() {
        profileRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}