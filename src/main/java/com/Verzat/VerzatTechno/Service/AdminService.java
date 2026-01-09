package com.Verzat.VerzatTechno.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Verzat.VerzatTechno.Entity.Folder;
import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.AlertRepository;
import com.Verzat.VerzatTechno.Repository.FolderRepo;
import com.Verzat.VerzatTechno.Repository.TaskRepo;
import com.Verzat.VerzatTechno.Repository.UserRepo;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AlertRepository alertRepo;

    @Transactional
    public void deleteUserCompletely(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1️⃣ Get user folders
        List<Folder> folders = folderRepo.findByUser(user);

        // 2️⃣ Delete tasks + alerts
        for (Folder folder : folders) {

            List<Task> tasks = taskRepo.findByFolder(folder);

            for (Task task : tasks) {
                alertRepo.deleteByTaskId(task.getId());
            }

            taskRepo.deleteAll(tasks);
        }

        // 3️⃣ Delete folders
        folderRepo.deleteAll(folders);

        // 4️⃣ Delete user
        userRepo.delete(user);
    }
}
