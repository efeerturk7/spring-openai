package repository;

import com.efeerturk.spring_openai.chat.enum1.SecurityReviewState;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReviewStateRepository extends ReactiveCrudRepository<SecurityReviewState,Long> {
}
