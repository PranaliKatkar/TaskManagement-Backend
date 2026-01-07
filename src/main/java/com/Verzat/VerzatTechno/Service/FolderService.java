package com.Verzat.VerzatTechno.Service;

import com.Verzat.VerzatTechno.Repository.FolderRepo;
import com.Verzat.VerzatTechno.Repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderService {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private TaskRepo taskRepo;

    public void deleteFolder(Long folderId) {
        if (!folderRepo.existsById(folderId)) {
            throw new RuntimeException("Folder not found");
        }

        taskRepo.deleteByFolder_Id(folderId);

        folderRepo.deleteById(folderId);
    }
}
