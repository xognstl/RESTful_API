package kr.co.xognstl.myrestfulservice.dao;

import kr.co.xognstl.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component // service 어노테이션 써도되지만 dao도 같이 쓸거라 이거로했음
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date(), "password1", "111111-1111111"));
        users.add(new User(2, "Xognstl", new Date(), "password2", "111111-2222222"));
        users.add(new User(3, "Alice", new Date(), "password3", "111111-3333333"));
    }

    public List<User> findAll() {   // user 전체 조회
        return users;
    }

    public User save(User user) {   // user 등록
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        if (user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }

    public User findOne(int id) {   // user id로 조회
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id) {    // user 삭제
        // 컬렉션에 있는 데이터 타입 중 리스트 형태를 iterator 로 바꿀 수 있다.
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null; // 데이터가 없을 때 null 값 반환
    }
}
