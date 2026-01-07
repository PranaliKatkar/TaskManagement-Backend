package com.Verzat.VerzatTechno.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Verzat.VerzatTechno.Entity.Folder;

@Repository
public interface FolderRepo extends JpaRepository<Folder, Long> {
    Optional<Folder> findByNameIgnoreCase(String name);
}
