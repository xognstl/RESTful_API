package kr.co.xognstl.myrestfulservice.repository;

import kr.co.xognstl.myrestfulservice.bean.Post;
import kr.co.xognstl.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
