package hanium.highwayspring.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRespository extends JpaRepository<BoardEntity, String>{
	// ?1은 메서드의 매개변수의 순서 위치다.
	//@Query("select id, userId, title, done from Todo t where t.userId = ?1")
	List<BoardEntity> findByUserId(String userId);
}
