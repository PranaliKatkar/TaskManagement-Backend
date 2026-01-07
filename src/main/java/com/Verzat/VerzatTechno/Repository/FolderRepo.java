package com.Verzat.VerzatTechno.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Verzat.VerzatTechno.Entity.Folder;
import com.Verzat.VerzatTechno.Entity.User;

@Repository
public interface FolderRepo extends JpaRepository<Folder, Long> {
    Optional<Folder> findByNameIgnoreCase(String name);

	List<Folder> findByUser(User user);
}
