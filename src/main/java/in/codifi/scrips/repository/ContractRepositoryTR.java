package in.codifi.scrips.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.codifi.scrips.entity.primary.ContractEntityTR;

public interface ContractRepositoryTR extends JpaRepository<ContractEntityTR, Long> {

}
