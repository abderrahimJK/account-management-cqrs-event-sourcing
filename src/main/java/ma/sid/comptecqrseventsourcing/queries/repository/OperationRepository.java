package ma.sid.comptecqrseventsourcing.queries.repository;

import ma.sid.comptecqrseventsourcing.queries.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
