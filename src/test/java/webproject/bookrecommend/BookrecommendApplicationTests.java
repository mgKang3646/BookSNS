package webproject.bookrecommend;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import webproject.bookrecommend.entity.Hello;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BookrecommendApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		Hello hello = new Hello();
		em.persist(hello);

		Hello findHello = em.find(Hello.class, hello.getId());

		Assertions.assertThat(findHello).isEqualTo(hello);
		Assertions.assertThat(findHello.getId()).isEqualTo(hello.getId());

	}

}
