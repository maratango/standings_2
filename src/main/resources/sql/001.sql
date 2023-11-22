--1.1.(+) пользователи
create table user_app (
id		    int not null,
login		varchar(100) not null,
password	varchar(100) not null,
email		varchar(100),
create_date	timestamp not null
);

--1.2.(+) ключ для user
ALTER TABLE user_app
    ADD CONSTRAINT userAppPK PRIMARY KEY (id);

--1.3.(+) сиквенс для генерации id пользователей
CREATE SEQUENCE user_app_sequence INCREMENT 1 START 1 no CYCLE;

--1.4.(+) функция по добавлению нового пользователя
CREATE OR REPLACE function server.fn_checkAndAddUser(p_login text, p_password text, p_email text)
	RETURNS boolean
	LANGUAGE plpgsql
as $function$
begin
	lock table server.user_app in ACCESS EXCLUSIVE mode;
	if exists(select 1 from server.user_app where upper(login) = upper(p_login))
		then
			return false;
else
			insert into server.user_app values(nextval('server.user_app_sequence'), p_login, p_password, p_email, now());
return true;
end if;
end;
$function$;


--2.1.(+) результаты матчей
create table game (
id     	        int not null,
tournament_id	int not null,
home_team   	varchar(100) not null,
home_goal   	int not null,
guest_team  	varchar(100) not null,
guest_goal  	int not null,
create_date		timestamp not null
);

--2.2.(+) ключ для game
ALTER TABLE game
    ADD CONSTRAINT gamePK PRIMARY KEY (id);

--2.3.(+) сиквенс для генерации id игр
CREATE SEQUENCE game_sequence INCREMENT 1 START 1 no CYCLE;


--2.4.(+) вторичный ключ для game и связь с tournament, настройка каскадного удаления
ALTER TABLE game
    ADD CONSTRAINT tournamentFK FOREIGN KEY (tournament_id) REFERENCES tournament(id) ON DELETE CASCADE;


--3.1.(+) турниры
create table tournament (
id	            int not null,
user_app_id		int not null,
name			varchar(100) not null,
create_date		timestamp not null
);

--3.2.(+) ключ для турниры
ALTER TABLE tournament
    ADD CONSTRAINT tournamentPK PRIMARY KEY (id);

--3.3.(+) сиквенс для генерации id турнира
CREATE SEQUENCE tournament_sequence INCREMENT 1 START 1 no CYCLE;

--3.4.(+) вторичный ключ для tournament и связь с user, настройка каскадного удаления
ALTER TABLE tournament
    ADD CONSTRAINT userAppFK FOREIGN KEY (user_app_id) REFERENCES user_app(id) ON DELETE CASCADE;

--3.5.(+) функция по добавлению нового Турнира пользователя
CREATE OR REPLACE function server.fn_checkandaddtournament(p_userAppId bigint, p_newTurnName text)
	RETURNS boolean
	LANGUAGE plpgsql
as $function$
begin
	lock table server.tournament in ACCESS EXCLUSIVE mode;
	if exists(select 1 from server.tournament where
	    user_app_id = p_userAppId
		and upper(name) = upper(p_newTurnName))
	then
		return false;
else
		insert into server.tournament values(nextval('server.tournament_sequence'), p_userAppId, p_newTurnName, now());
return true;
end if;
end;
$function$;


--4.1.(+) Функция, которая по games считает турнирную таблицу по играм турнира
CREATE OR REPLACE function fn_turnStandings(p_tournId bigint)
	RETURNS TABLE(teamName varchar, points numeric, gameCount numeric, winCount numeric, drawCount numeric, lossCount numeric,
		goalsScoredCount numeric, goalsСoncededCount numeric, goalsDifferenceBetween numeric)
	LANGUAGE plpgsql
as $function$
begin
RETURN QUERY
    with
	turnGames as
		(select * from server.game where tournament_id = p_tournId),
	teams as
	    (select foo.team from
	        (select home_team as team
	        from turnGames
	        group by home_team
	        union all
	        select guest_team as team
	        from turnGames
	        group by guest_team) as foo
	    group by foo.team),
	winers as
	    (select foo2.team, sum(foo2.count) as count from
	        (select home_team as team, count(*) as count
	        from turnGames where
	        home_goal > guest_goal
	        group by home_team
	        union all
	        select guest_team as team, count(*) as count
	        from turnGames where
	        home_goal < guest_goal
	        group by guest_team) as foo2
	    group by foo2.team),
	drawers as
	    (select foo3.team, sum(foo3.count) as count from
	        (select home_team as team, count(*) as count
	        from turnGames where
	        home_goal = guest_goal
	        group by home_team
	        union all
	        select guest_team as team, count(*) as count
	        from turnGames where
	        home_goal = guest_goal
	        group by guest_team) as foo3
	    group by foo3.team),
	losers as
	    (select foo4.team, sum(foo4.count) as count from
	        (select home_team as team, count(*) as count
	        from turnGames where
	        home_goal < guest_goal
	        group by home_team
	        union all
	        select guest_team as team, count(*) as count
	        from turnGames where
	        home_goal > guest_goal
	        group by guest_team) as foo4
	    group by foo4.team),
	goals_scored as
	    (select foo5.team, sum(foo5.count) as count from
	        (select home_team as team, sum(home_goal) as count
	        from turnGames
	        group by home_team
	        union all
	        select guest_team as team, sum(guest_goal) as count
	        from turnGames
	        group by guest_team) as foo5
	    group by foo5.team),
	goals_conceded as
	    (select foo6.team, sum(foo6.count) as count from
	        (select home_team as team, sum(guest_goal) as count
	        from turnGames
	        group by home_team
	        union all
	        select guest_team as team, sum(home_goal) as count
	        from turnGames
	        group by guest_team) as foo6
	    group by foo6.team)
select
    t.team as teamName,
    (COALESCE(w.count, 0) * 3 + COALESCE(d.count, 0)) as points,
    (COALESCE(w.count,0) + COALESCE(d.count, 0) + COALESCE(l.count,0)) as gameCount,
    COALESCE(w.count,0) as winCount,
    COALESCE(d.count,0) as drawCount,
    COALESCE(l.count,0) as lossCount,
    COALESCE(gs.count,0) as goalsScoredCount,
    COALESCE(gc.count,0) as goalsСoncededCount,
    (COALESCE(gs.count,0) - COALESCE(gc.count,0)) as goalsDifferenceBetween
from teams t
         left join winers w on t.team=w.team
         left join drawers d on t.team=d.team
         left join losers l on t.team=l.team
         left join goals_scored gs on t.team=gs.team
         left join goals_conceded gc on t.team=gc.team
order by points desc, goalsDifferenceBetween desc;
return;
end;
$function$
;
