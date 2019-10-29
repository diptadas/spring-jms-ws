package edu.baylor.ecs.csi5324.repository;

import edu.baylor.ecs.csi5324.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    List<Contest> findByName(String name);
}
