package io.musicStreaming.start.controller;

import io.musicStreaming.start.model.Song;
import io.musicStreaming.start.model.dto.UserDataTransferObject;
import io.musicStreaming.start.repository.UserRepository;
import io.musicStreaming.start.service.FileService;
import io.musicStreaming.start.service.SongService;
import io.musicStreaming.start.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SongService songService;

    @GetMapping("/home")
    public String home(Model model, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "0") int page) {
        Page<Song> songs = songService.songsList(size, page);
        model.addAttribute("songs", songs.getContent());
        model.addAttribute("totalPages", songs.getTotalPages() - 1);
        model.addAttribute("size", songs.getSize());
        model.addAttribute("number", songs.getNumber());
        return "home";
    }

    @GetMapping("/")
    public String redirectContextPath() {
        return "redirect:/home";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @GetMapping("/edit/{id}")
    public String editSong(Model model, @PathVariable Long id) {
        model.addAttribute("song", songService.findById(id));
        return "edit";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new UserDataTransferObject());
        return "register";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
