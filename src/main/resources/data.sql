insert into users(id, join_date, name, password, ssn) values (90001, now(), 'User1', 'test111', '920913-1111111');
insert into users(id, join_date, name, password, ssn) values (90002, now(), 'User2', 'test222', '920913-1111111');
insert into users(id, join_date, name, password, ssn) values (90003, now(), 'User3', 'test333', '920913-1111111');

insert into post(description, user_id) values('my first post', 90001);
insert into post(description, user_id) values('my second post', 90001);
