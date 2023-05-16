package io.musicStreaming.start.controller;

import io.musicStreaming.start.service.FileService;
import io.musicStreaming.start.service.SongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SongController {

	@Autowired
	private SongService songService;
	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public String handleUpload(@RequestParam("audio") MultipartFile audio, @RequestParam("image") MultipartFile image, RedirectAttributes redirect, @RequestParam String title, @RequestParam String artistName) {
		if (audio.isEmpty() || image.isEmpty())
			redirect.addFlashAttribute("message", "Please make sure to upload both audio and image");
		else
			redirect.addFlashAttribute("message", "Thank you for uploading");

		songService.saveSong(title, artistName, image, audio);

		fileService.uploadFileOfKind(audio, "audio");
		fileService.uploadFileOfKind(image, "image");

		return "redirect:/home";
	}

	@PostMapping("/edit/{id}")
	public String handleEdit(@PathVariable Long id, @RequestParam("audio") MultipartFile audio, @RequestParam("image") MultipartFile image, RedirectAttributes redirect, @RequestParam String title, @RequestParam String artistName) {
		songService.updateSong(id, title, artistName, image, audio);

		if(!audio.isEmpty()){
			fileService.uploadFileOfKind(audio, "audio");
		}

		if(!image.isEmpty()){
			fileService.uploadFileOfKind(image, "image");
		}

		return "redirect:/home";
	}

	@GetMapping("/song/{id}")
	public String getSong(Model model, @PathVariable Long id) {
		model.addAttribute("song", songService.findById(id));
		return "play";
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSong(@PathVariable Long id) {
		songService.delete(id);
		return ResponseEntity.ok().body("Song deleted");
	}
}