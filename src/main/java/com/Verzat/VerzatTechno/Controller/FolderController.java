package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Entity.Folder;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.FolderRepo;
import com.Verzat.VerzatTechno.Repository.UserRepo;
import com.Verzat.VerzatTechno.Service.FolderService;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/user/{email}")
    public List<Folder> getFoldersByUser(@PathVariable String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return folderRepo.findByUser(user);
    }

    @PostMapping
    public Folder createFolder(@RequestBody Folder folder) {

        if (folder.getName() == null || folder.getName().trim().isEmpty()) {
            throw new RuntimeException("Folder name required");
        }

        if (folder.getUser() == null || folder.getUser().getEmail() == null) {
            throw new RuntimeException("User email required");
        }

        User user = userRepo.findByEmail(folder.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        folder.setUser(user);
        return folderRepo.save(folder);
    }

    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
    }
}
