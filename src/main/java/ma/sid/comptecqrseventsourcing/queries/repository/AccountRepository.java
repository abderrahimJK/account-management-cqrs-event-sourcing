package ma.sid.comptecqrseventsourcing.queries.repository;

import ma.sid.comptecqrseventsourcing.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
