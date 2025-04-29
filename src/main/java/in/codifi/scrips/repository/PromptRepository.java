package in.codifi.scrips.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.codifi.scrips.entity.primary.PromptEntity;

public interface PromptRepository extends JpaRepository<PromptEntity, Long> {

}
