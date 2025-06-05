package merisalu.kmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import merisalu.kmdb.model.Actor;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findByNameContainingIgnoreCase(String name);
}