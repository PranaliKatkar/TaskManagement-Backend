package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Entity.Folder;
import com.Verzat.VerzatTechno.Repository.FolderRepo;
import com.Verzat.VerzatTechno.Service.FolderService;

@RestController
@RequestMapping("/api/folders")
@CrossOrigin(origins = "https://task-management-frontend-jk35.onrender.com")
public class FolderController {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private FolderService folderService;

    // ✅ Get all folders
    @GetMapping
    public List<Folder> getAllFolders() {
        return folderRepo.findAll();
    }

    // ✅ Create folder
    @PostMapping
    public Folder createFolder(@RequestBody Folder folder) {

        if (folder == null || folder.getName() == null || folder.getName().trim().isEmpty()) {
            throw new RuntimeException("Folder name is required");
        }

        return folderRepo.save(folder);
    }

    // ✅ Delete folder and its tasks
    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
    }
}
