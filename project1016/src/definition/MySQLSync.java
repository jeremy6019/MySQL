package definition;

public class MySQLSync {
/*
  
 ** Windows에서 MySQL설치 
 =>Windows용을 다운로드 받아서 설치 
윈도우즈용이 닷넷 프레임워크를 이용해서 만들어져서 닷넷프레임워크를 설치하고
설치를 해야합니다. 
 
윈도우즈용 MySQL접속도구인 workbench가 같이 설치 
 
설치하다가 실패하는 경우는 닷넷 프레임워크가 설치되어 있지 않거나 방화벽을 해
지하지 못해서 실패하는 경우가 있습니다. 
MySQL은 설치가 되면 3306(기본 포트- 변경 가능)번 포트로 외부와 통신 
 
** MySQL접속 
=>MySQL드라이버를 준비 
드라이버 버전이 너무 오래 되었거나 데이트베이스 버전과 다르면 접속이 안되는 경 
우가 있습니다. 
MySQl은 8.x	버전(이전 버전과 호환이 잘 안됨)과 5.x버전으로 나눕니다. 
아직까지는 MySQL은 5.x버전을 많이 사용합니다. 
=>5.x버전에 접속할 때 8.x버전의 드라이버를 사용하면 안됩니다. 

=>드라이버는 www.mvnrepository.com에서 MySQL로 검색해서 다운로드 

1. 접속 정보 
=> 데이터베이스 서버의 주소 
자기 컴퓨터는 localhost 
자기컴퓨터를 ip로 나타낼때는 127.0.0.1 
학원에서 접속 가능한 서버는 192.168.0.151 

=>포트번호 
기본은 3306
기존에 설치된 상태에서 다른 버전을 설치하면 포트번호는 자동으로 변경됩니다. 

=>데이터베이스 
기본적으로 제공되는 데이터베이스는 	mysql
학원 서버는 user01 - user20

=> 계정 
기본적으로 생성되는 계정은 root(관리자)
학원서버는 user01- user20 

=>비밀번호 
윈도우즈의 경우는 설치할 때 관리자 계정의 비밀번호를 설정 
Mac은 설치할 때 시스템이 해시코드를 이용해서 생성하고 변경해서 사용 
학원서버는 user01 - user20으로 설정 

** Select 
1.where 
=>null인 데이터 검색 
where 컬럼이름 is null; 

=> not 
not을 앞에 붙이면 조건이 반대 
null이 아닌 데이터 is not null
not between A ans B
not in(데이터 나열) 

ex) usertbl테이블에서 birthyear가 1980에서 1989사이가 아닌 데이터 조회 
	컬럼은 name과 birthyear만 조회 
select  name, birthyear
from usertbl 
where birthyear not between 1980 and 1989; 

ex) usertbl테이블에서 addr이 서울과 광주는 아닌 데이터를 조회 
컬름은 name과addr만 조회 
select name, addr 
from usertbl
where arrd not in ('서울','광주');

=>like:부분일치하는 조건을 찾을 때 사용하는 연산자 
_: 하나의 글자와 매칭 
% : 글자 수 상관없이 매칭 

ex) usertbl체이블에서 name의 시작인 김인 데이터의 name과 mdate를 조회 

select name, mdate 
from usertbl
where name like  '김%';

ex) buytbl테이블에서 productname이 한글자인 데이터의 userid와 
productname을 조회 

select userid, productname
from buytbl
where productname like '_';

ex)usertbl에서 name 에 라가 포함된 데이터의 name과 birthyear를 조회 

select name, birthyear
from usertbl
where name like '%라%' ;

=>검색기능을 구현할 때 대다수의 검색시스템이 여러 단어 입력을 지원 
공백을 기준으로 단어를 입력하면 and로 간주 
java mysql형태로 입력하면 java와 mysql2개를 입력한 것으로 간주 
공백을 기준으로 분할해서 and로 검색을 하고 두개 중에 하나만 포함된 것도 검색 
을 해서 뒤에 붙여 줍니다. 

2.그룹화
1)그룹함수 
=> 행단위로 연산하지 않고 전체 생을 가지고 연산한 후 하나의 값으로 리턴하는 함수 
=>count, max, min, avg, sum , var(분산), std(표준편차)
분산이나 표준편차는 데이터의 분포를 확인하기 위해서 사용 
=>그룹함수는 그룹화한 이후에 사용할 수 있습니다. 
select, having, order절에서 사용이 가능 

 =>avg, sum은 null인 데이터가 있으면 무시합니다. 
avg와 sum에서 null인 데이터를 참여시키고자 할 때는 오라클에서는 nvl(
컬럼이름이나 연산식, null일때 사용할 값)을 이용하고 MySQL에서는 IFNULL
(컬럼이름이나 연산식, Null일때 사용할 값)을 이용합니다. 

=>count함수는 null인 데이터를 제외한 데이터의 개수를 리턴 
테이블에서 행의 개수를 파악할 때는 count(*)을 이용 
* 대신에 컬럼이름을 이용하면 컬럼의 값이 null인 경우 제외되기 때문입니다. 

ex)usertbl테이블의 행의 개수를 조회 
=>한 번에 출력하기에는 너무 많은 데이터일 때 데이터의 전체 개수를 보여주거나 
페이징 처리를 할 때 몇개의 페이지를 만들어야 하는지 알고자 할 때 많이 이용  

select count(*)
from usertbl;

ex)buytbl테이블에서 가장 큰 num을 조회 
 =>오라클이나 MySQL등의 데이터베이스에는 일련번호 형태를 제공하는 
 Sequence나 Auto_Increment같은 기능이 있는데 이기능을 이용해서 일련번호를 
 만들면 마지막에 데이터를 삽입하고 삭제하는 작업을 반복하면 중간에 번호가 
 비어있게 되는 경우가 발생합니다. 
 =>일련번호를 가장 큰 번호를 찾아서 +1을 하는 경우가 종종 있습니다. 
 
 select max(num)
 from buytbl;
 
2)group by 
=>select구문에서 데이터를 그룹화할 때 사용하는 절 
where뒤에 작성하고 where 다음에 동작 
=>group by 절에는 컬럼이름이나 연산식을 작성 

 ex)buytbl테이블에서 userid를 중복을 제거하고 조회 
=> select에서 distinct를 이용해도 되지만 group by를 이용해도 됨  
 
 select userid 
 from buytbl 
 group by userid;  
 
 ex)buytbl테이블에서 userid별로 상품(productname)을 구매한 횟수를 조회 

select userid, count(productname)
from buytbl
group by userid;

ex) buytbl테이블에서 userid별로 price와 amount를 곱한 결과의 합계를 조회 

select userid, sum(price*amount) total 
from buytbl 
group by userid;
 
=>group by를 사용할 때 주의할 점은 group by를 사용하게 되면 select절에서는 
group by에 사용한 컬럼과 집계함수만 사용할 수 있습니다. 
=>group by를 사용할 때 select절에는 필수적으로 group by에 사용한 컬럼을 
조회하도록 해주어야 합니다. 
그렇지 않으면 각 데이터가 어떤 항목의 데이터인지 알 수 없습니다. 

select sum(price * amount) 
from buybtl
group by userid;
이런 SQL은 문법적으로 에러는 아니지만 사용은 안함 

elect num, userid, sum(price * amount) 
from buybtl
group by userid;
이런 SQL은 에러 
userid는 그룹화되서 하나만 출력되는데 num은 여러 개가 될 수 있어서 에러 

left함수를 이용하면 컬럼에서 왼쪽부터 문자열을 자를 수 있습니다. 
left(컬럼이름, 개수)

substring 함수: substring(컬럼이름, 시작위치, 길이)

right함수를 이용하면 뒤에서부터 문자열을 자를 수 있습니다. 

ex) usertbl테이블에서 mdate의 앞 4글자로 그룹화해서 데이터의 개수를 조회 

select left(mdate,4),count(*)
from usertbl
group by left(mdate, 4); 

ex) usertbl테이블에서 mdate의 월을 기준으로 그룹화해서 데이터의 개수를 조회 

select substring(mdate,6,2),count(*)
from usertbl
group by substring(mdate, 6,2);   
 
3)having 
=>그룹화한 데이터에 대한 조건을 설정하는 절 
=>group by 가 없으면 사용할 수 없는 절 
=>조건을 설정하는 where절에는 집계함수를 사용할 수 없기 때문에 집계한 데이터의 
조건을 설정할 수 없습니다. 
이런 경우에는 having 에 작성 
=>group by절 다음에 작성하고 group by 다음에 수행 

  ex) buytbl테이블에서 2번 이상 물건을 구매한 사용자의 id를 조회 
 =>buytbl테이블에서 userid가 2번 이상인 데이터의 userid만 조회 
 
 select userid 
 from buytbl
 where count(userid) >=2
 group by userid;
 =>위의 문장은 에러: 집계함수는 group by 이후에 실행되는 절에서만 사용이 가능 
 
 select userid 
 from buytbl
 group by userid
 having count(userid) >= 2;
=> 통계에서 이런 형태를 많이 사용합니다. 


3. 데이터 정렬 
=> 관계형 데이터베이스는 데이터를 저장을 할  때 일반적인 List구조가 아니고 
자신만의 인덱스(B*Tree, Tri등을 이용)를 이용해서 데이터를 저장 
=>테이블의 데이터는 순서대로 저장되어 있지 않아서 2개 이상의 행데이터를 조회
할 때는 원하는 항목으로 정렬을 해서 조회하도록 권장 
=>having절 다음에 order by를 추가해서 정렬할 컬럼이나 연산식을 나열하고 이
절은 6개의 절 중에서 가장 마지막에 수행 

order by 컬럼이름 이나 연산식[asc | desc ] ... 
=>asc는 오름차순 정렬(작은 것 -> 큰 것)을 하고 desc는 내림차순 정렬(큰 것 -> 
작은 것)을 수행합니다.
=> 뒤에 나열을 하면 앞 쪽에 작성한 컬럼이나 연산식의 값이 동일할 때 적용됩니다. 

 ex) usertbl테이블의 데이터를 birthyear를 기준으로 오름차순 정렬해서 userid, 
 name, birthyear를 조회 
  
  select userid, name, birthyear 
  from usertbl 
  order by birthyear asc;

   ex) usertbl테이블의 데이터를 birthyear를 기준으로 오름차순 정렬하고 동일한 값
   일때는 name의 내림차순으로 정렬해서  userid, name, birthyear를 조회 
  
  select userid, name, birthyear 
  from usertbl 
  order by birthyear asc, name desc;
  
4.MySQL에는 마지막에 limit를 추가할 수 있음 
=>limit 조회할 데이터 개수 
=>limit 시작위치, 조회할 데이터 개수 

ex)usertbl테이블에서 데이터를 3개만 조회 
단 userid, name, mdate컬럼만 조회 
select userid, name, mdate 
from usertbl 
limit 3;

ex)usertbl테이블에서 4번째부터 7번째 데이터까지 4개를 조회 
단 userid, name, mdate컬럼만 조회 
=>limit를 사용할 때 2개의 정수를 입력하면 앞쪽의 값은 시작위치이고 뒤쪽의 값은 
데이터 개수입니다. 
=> 시작위치는 0부터 시작 
select userid, name, mdate 
from usertbl 
limit 3,4;

ex)usertbl테이블에서 데이터를 mdate의 내림차순으로 정렬하고 3개만 조회 
단 userid, name, mdate컬럼만 조회 

select userid, name, mdate 
from usertbl 
order by mdate desc
limit 3;
 
=>mysql은 limit가 있어서 정렬한 후에 원하는 개수만큼 조히하는 것이 Oracle에 
비해서 쉽습니다. 
  
정렬한 후 limit에서 앞쪽의 숫자만 변경하면 페이지 단위로 조회하는것을 쉽게 
구현할 수 있습니다.

ex)usertbl테이블에서 mdate의 오름차순으로 정렬해서 3개씩 가져오기 
select *
from usertbl 
order by mdate asc
limit 0,3

=> limit의 자리만 페이지 당 출력개수를 더해주면 됩니다. 

** DDL (Data Definition Language) 
1.테이블 생성  
create[temporary] table 테이블 이름[if not exists] 테이블이름( 
  컬럼이름 자료형 컬럼제약조건,
  ... .
  테이블 제약조건)옵션 설정 
1) temporary:임시 테이블을 생성 
=>현재 접속한 session에서만 사용이 가능한 테이블 

2) if not exists:테이블이 없는 경우에만 생성 

3)옵션
ENGINE = [MyISAM | InnoDB]
MyISAM(Indexed Sequential Access Media):인덱스를 생성해서 데이터를 빠르게 
접근하고자 할 때 사용하는 엔진 

InnoDB:트랜잭션 처리를 하기 위해서 사용하는 엔진 

=>index가 존재하면 검색에는 유리하지만 삽입, 삭제, 갱신 작업에는 불리 

=> Default Charset 인코딩방식 
MySQL은 기본 인코딩이 ISO-Latin-1(ISO-8859-1)이어서 한글 입력이 안됩니다. 
테이블을 만들때 인코딩 설정을 해야하고 프로그래밍 언어에서 MySQL에 접속할 
때도 인코딩 설정을 해주어야 한글을 사용할 수 있습니다. 

=>auto_increment = 시작값 
MySQL은 Sequence가 없는 대신에 auto_increment로 일련번호를 생성하는데 
이때 시작번호 입니다. 

4)자료형 
=>숫자: int(integer), float, double 
=>문자: char(크기), varchar(크기), text(긴 문자열 - 65536자까지), longtext
=>날짜: date(날짜만), datetime(날짜와 시간-초단위), timestamp(날짜와 시간 - 
미세한 초까지) 
=>바이트 배열(파일의 내용) - blob(65KB), longblob(4GB)  
=>xml,json등의 데이터 타입도 존재 

2. 제약조건 
1) not null: 필수입력 
=>null데이터 저장은 하나의 비트를 추가해서 null여부를 표시하는 방법을 사용 

2) unique:중복 될수 없다 
=>unique로 설정하면 데이터는 중복될 수 없고 인덱스가 자동 생성됩니다. 

3)primary key: not null 이고 unique 
=>테이블 내에서 1번만 설정 가능 
=> 2개 이상의 컬럼을 가지고 primary key를 설정할 때는 컬럼 제약조건으로 설정
하면 안되고 테이블 제약조건으로 설정해야 합니다. 

4)check: 특정한 값만을 갖는 범주형이나 범위내의 데이터만 저장할 수 있음 

5)foreign key: 다른 테이블의 데이터를 참조하기 위한 제약조건 
=> 컬럼제약조건으로 설정할 때는 references다른 테이블이름(컬럼이름)
=> 테이블 제약 조건으로 설정할 때는 foreign key(컬럼이름) references다른 테이블
이름(컬럼이름) 
=>설정할 때 뒤에 옵션 설정 가능 
옵션을 설정하지 않으면 참조하는 테이블에서는 참조 당하는 테이블에 데이터가 
존재하면 삭제할 수 없습니다. 

같이 삭제되도록 할 때는 on delete cascade 
삭제 될 때 null이 되도록 할 때는 on delete set null 

=>foreign key로 참조되는 컬럼은 다른 테이블에서 primary key이거나 unique속성을
가져야 합니다. 

6)default 
=>기본값 설정에 이용 

7) auto_increment 
=> 일련번호로 자동으로 1씩 증가하고록 해주는 옵션 
=> 이옵션이 설정되면 primary key이거나 unique이어야 합니다. 

ex) 테이블을 생성 
번호 - 일련번호 , 기본키 
이름- 문자 한글포함 20자리 : 크기가 변경 , 필수 
주소 - 문자 한글 포함 100자리 : 크기가 변경 
전화번호 - 문자 20자리 : 크기가 변경되지 않음 
이메일 - 문자 100자리 : 변경자체가 안됨, 필수  
생일 - 날짜  

=> 이 테이블의 데이터는 변경이 자주 일어납니다. 
일련번호는 1부터 시작 
한글이 포함되어 있습니다.

 create table contact( 
   num int auto_increment ,
   name char(60) not null, 
   addr text, 
   phone varchar(20), 
   email varchar(100)not null, 
   birthday date,
   primary key(num))engine=innodb auto_increment=1 default charset=utf8;
   
=>engine 은 삽입,삭제,수정작업이 많을 때는 innodb
조회를 많이 할 때는 myisam 
=>문자의 자료형을 만들 때 크기 변경이 있을때는 char가 좋고 크기 변경이 없을 
때는 varchar가 좋습니다. 


3. 테이블 수정 
1)테이블에 컬럼 추가 
alter table 테이블 이름 
add 컬럼이름 자료형 제약조건[first | after] [컬럼이름]; 

ex) 앞에 만든 테이블에 나이컬럼을 추가 
 나이컬럼은 정수 
alter table contact 
add age int after name;
  
=>확인은 접속도구인 경우는 왼쪽창에서 자신의 스키마를 확인 
=> 접속도구가 아닌 경우에는 select * from 테이블이름; 

2)컬럼 삭제 
alter table 테이블 이름 
drop 컬럼이름 나열;
=>테이블이나 컬럼 또는 데이터를 삭제할 때 분명히 존재하는 데이터를 삭제하는데 
문제가 발생하면 다른 테이블에 외래키로 설정되지 않았는지 확인해봐야 합니다. 

 ex)앞에서 만든 테이블의 나이컬럼 삭제 

alter table contact 
drop age;

3)컬럼 변경 
alter table 테이블이름 
change 이전컬럼이름 새로운컬럼이름 자료형; 

=>이미 데이터가 존재하는 경우 자릿수를 줄이면 데이터가 손실될 수 있습니다. 

ex) 전화번호(phone)컬럼의 이름을 mobile로 자료형은 varchar(11)로 수정 
 alter table contact 
 change phone mobile varchar(11);  
  
4)컬럼의 자료형 변경 
alter table 테이블이름 
modify 컬럼이름 새로운자료형; 

5)테이블 이름 변경 
alter table 원래테이블이름 rename 새로운테이블이름; 

6)auto_increment 의 새로운 값 설정 
alter table 테이블이름 auto_increment=새로운 시작값; 

4.테이블 삭제 
drop table 테이블 이름; 

ex)방금 전에 만든 테이블을 삭제 
drop table contact;

5. 오라클과 MySQl의 SQL이 가장 크게 다른 부분이 DDL부분입니다. 
SELECT, INSERT, DELETE, UPDATE에서는 날짜형식을 사용하는 부분을
제외하면 크게 차이는 없습니다. 
테이블을 생성하는 부분은 되도록이면 E-R모델링 도구를 이용하는 것이 좋습니다. 

 ** DML구문 
=>데이터를 추가하고 갱신하고 삭제하는 언어 
1.데이터 추가 - insert 
insert into 테이블 이름(컬럼이름 나열)
values(값을 나열); 

=>컬럼이름을 생략할 수 있는데 이때는 테이블에 만들어진 컬럼의 순서대로 전부 
값을 나열해야 합니다. 
=>auto_increment가 설정된 컬럼이나 not null이나 primary key가 설정된 컬럼을 
제외하고는 생략하고 삽입이 가능합니다. 
=>외래키는 다른 테이블에 존재하는 값이나 null로 삽입해야 합니다.
다른 테이블에 존재하지 않는 값을 삽입하면 에러 

ex1)usertbl테이블에 데이터를 1개 삽입 
userid는 jerome94 
name 은 김기범 
birthyear 는 1975 
addr은 서울 
mobile 은 01012341234 
mdate 1975년 10월 8일('1975-10-08')로 삽입 -날짜 

insert into usertbl(userid, name,birthyear, addr, mobile,mdate)
values('jerome94','김기범',1975,'서울','01012341234','1975-10-08');

현재 날짜 및 시간은 now() 

ex)buytbl테이블에 데이터를 삽입 
num은 auto_increment 
userid 는 jerome94
productname 은 과일 
groupname 은 식료품 
price 는 100 
amount는 20으로 삽입 
 
insert into buytbl(userid, productname,groupname, price, amount)
values('jerome94','과일','식료품',100,20); 

=>삽입에 실패하는 경우 
- 컬럼이름이나 테이블이름을 틀린 경우 
에러메세지는 뷰나 테이블이 없다고 나오거나 컬럼이름이 없다고 

- 제약 조건 위반 
메세지는 제약조건 위반이라고 출력 
not null이나 primary key가 설정된 컬럼의 값을 입력하지 않은 경우 
unique나 primary key가 설정된 컬럼의 값이 중복된 경우 
foreign key가 설정된 컬럼의 값이 참조하는 테이블에 없는 경우 
check제약조건이 설정된 컬럼의 값이 안 맞는 경우입니다. 

 -컬럼의 자료형과 맞지 않는 데이터를 삽입하는 경우 
 
=> 프로그래밍 언어에서 데이터베이스 수행하는 코드가 에러 나면 메세지는 데이터
베이스에서 보여지는 메세지가 그대로 출력됩니다. 

2)데이터 수정 
update테이블이름 
set 수정할 내용 
[where 조건];
=>where거 없으면 테이블의 모든 데이터를 수정 
=>수정할 내용은 컬럼이름=값 또는 연산식, 컬럼이름=값 또는 연산식... 
=>조건은 대부분의 경우는 기본키를 가지고 조건을 생성 

ex) usertbl테이블에서 userid가 iyou인 데이터의 name 을 이지은 으로 수정 
update usertbl 
set name = '이지은'
where  userid = 'iyou';   

=>에러는 없는데 수정이 안되는 경우이면 조건에 맞는 데이터가 없는 것입니다. 

3)데이터 삭제 
delete from 테이블 이름 
[where 조건];
=>조건은 생략이 가능한데 생략하면 테이블의 모든 데이터가 삭제 
=>외래키 옵션에 따라 삭제가 되기도 하고 안될 수 도 있으면 지워질 때 하위 테이블의
데이터도 지워지거나 수정될 수 있습니다. 

ex) usertbl테이블에서 userid가 jerome94인 데이터를 삭제 
delete from usertbl 
where userid='jerome94';

** TCL(Transaction Control Language) 
1.Transaction 
=> 한번에 이루어져야 하는 논리적인 작업단위 
=> transaction은 All or Nothing의 성질을 가짐 
전부 완료되거나 아니면 하나도 수행되지 않아야 합니다. 
=> 실제 업무에서는 중요한 개념 
대다수의 업무는 하나의 작업만 수행하는 경우는 드물기 때문입니다. 

2.프로그램에서 Transaction사용방법에 따른 분류 
1)auto-commit
=>하나의 SQL문장이 성공적으로 수행되면 자동으로 commit 
Java는 기본적으로 Auto-commit  

2)manual-commit 
=>SQL문장이 성공적으로 수행되다라도 직접 commit을 호출해야하는 경우

3.트랜잭션 관련 명령어 
1)commit:현재까지 수행한 작업을 원본에 반영 

2)savepoint이름:rollback할 수 있는 위치를 설정 

3)rollback [to 세이브포인트 이름]:작업을 취소하는 명령어인데 세이브포인트가 
생략되면 트랜잭션이 생성된 시점으로 rollback되고 세이브포인트 이름을 입력하면
세이브포인트까지 rollback됩니다. 

4.트랜잭션이 생성되는 시점 
=>첫번째 DML(insert,update,delete)문장이 실행될 때 

5.트랜잭션이 소멸되는 시점 
=>commit되는 경우:명시적으로 commit을 호출하거나 DDL(create, alter,
drop)이나 DCL(grant,revoke)문장이 성공적으로 수행된 경우 그리고 접속 프로그램 
이 정상적으로 완료된 경우 
 
=>rollback되는 경우: 명시적으로 rollback을 호출하거나 접속프로그램이 비정상적으로  
종료되는 경우 

6.Java에서는 트랜잭션을 Connection 객체가 관리 
setAutoCommit메소드를 이용해서 관리방법을 설정하고 commit()과 rollback()
메소드를 이용해서 동작을 시킵니다. 
 
** join
=>2개의 테이블을 합쳐서 하나의 테이블을 만드는 연산중의 하나 
=>2개의 테이블을 합쳐서 하나의 테이블을 만드는 또다른 연산으로는 집합연산이 
있습니다. 

1.집합(SET)연산  
=>테이블 구조가 같은 경우에만 사용이 가능
=>UNION(합집합), INTERSECT(교집합), MINUS(차집합) 
=>MySQL에서 UNION을 할 때는 기본적으로 중복을 제거하고 리턴하는데 
UNION ALL을 하게되면 중복된 데이터도 각각 리턴 
=>MySQL에서는 교집합은 JOIN을 이용해서 수행 
=>MySQL에서 차집합은 Sub Query를 이용해서 수행 

2.Catesian Product - Cross JOIN 
=>from절에 테이블 이름을 2개를 입력하면 Cross Join발생 
=>양쪽 테이블에 있는 데이터들의 모든 조합을 만들어 내는 것 
=>Cross JOIN은 많은 리소스를 사용하기 때문에 속도가 느립니다. 
=>CrossJOIN의 결과는 컬럼의 개수는 양쪽 컬럼의 개수의 합이 되고 행의 개수는 
양쪽행 개수의 곱이 됩니다.

ex) usertbl테이블과 buytbl테이블의 cross join 
select * 
from usertbl, buytbl; 

3.Equi Join
=>양쪽 테이블에 동일한 의미를 갖는 컬럼의 값이 동일한 데이터만 조합하는 JOIN 
=>컬럼의 이름은 상관이 없고 컬럼의 자료형이 일치해야 합니다. 

select*
from 테이블이름1, 테이블이름2 
where 테이블이름1.컬럼이름 = 테이블이름2. 컬럼이름 

select * 
from 테이블이름1 JOIN 테이블이름2 
on 테이블이름1.컬럼이름 = 테이블이름2.컬럼이름; 

테이블이름에 별명을 부여할 수 있는데 이 때는 한 칸 공백을 두고 다른 이름을 작성
하거나 as 다음에 다른이름을 기재하면 됩니다. 
별명을 만들면 모든 절에서 별명을 사용해야 합니다. 

ex)usertbl테이블과 buytbl테이블에는 외래키로 설정된 userid컬럼이 공통으로 존재합니다.
userid컬럼을 가지고 equijoin을 수행 

select * 
from usertbl, buytbl
where usertbl.userid = buytbl.userid;

select * 
from usertbl u, buytbl b
where u.userid = b.userid;
=>프로그래밍에서 alias를 설정하는 개념은 대부분 이름을 변경하는 것입니다. 
  
select * 
from usertbl u join buytbl b
on u.userid = b.userid; 
=> 이방식을 권장하는 데 그 이유는 join조건과 검색조건을 구분하기 위해서 입니다.  
=> InnerJOIN을 하게되면 양쪽에 모두 존재하는 13개의 ㄷ이터만 조회 

4.Non Equi JOIN 
=>JOIN하는 컬럼을 비교할 때 = 가 아닌 것을 이용하면 Non Equi JOIN 이라고 합니다.

5.Inner JOIN
=>Equi JOIN 과 비슷한 의미인데 JOIN을 구분하기 위해서 사용하는 용어 
=>양쪽 테이블에 모두 존재하는 데이터만 JOIN 

6.Outer JOIN 
=>한 쪽에만 존재하는 데이터도 JOIN에 참여시키는 것으로 한쪽에만 존재하는 데이터는
다른 테이블에 존재하는 컬럼의 값을 Null로 만듭니다. 
1)LEFT OUTER JOIN 
=>왼쪽 테이블에만 존재하는 데이터도 JOIN에 참여 

select * 
from 테이블이름1 LEFT JOIN 테이블이름2 
on 테이블이름1.컬럼이름 = 테이블이름2.컬럼이름; 
=> usertbl에 있지만 buytbl에는 없는 2개의 데이터를 추가해서 15개의 데이터 조회 

2)RIGHT OUTER JOIN 
=>오른쪽 테이블에만 존재하는 데이터도 JOIN에 참여 

 select * 
from 테이블이름1 RIGHT JOIN 테이블이름2 
on 테이블이름1.컬럼이름 = 테이블이름2.컬럼이름; 
 
3)FULL OUTER JOIN 
=>어느 한쪽에만 존재하는 데이터도 JOIN에 참여 
=>MySQL에서는 이기능을 제공하지 않음 
=>UNION연산을 이용해서 FULL OUTER JOIN을 수행  

 select * 
from usertbl LEFT join buytbl 
on usertbl.userid = buytbl.userid;

UNION

select * 
from usertbl RIGHT join buytbl 
on usertbl.userid = buytbl.userid;
 
7.SELF JOIN
=>하나의 테이블을 가지고 JOIN 
=>동일한 테이블 이름을 2번 사용하기 때문에 반드시 별명을 만들어서 사용 
=> 하나의 테이블에 동일한 의미를 갖는 컬럼이 2개이상 존재할 때만 사용이 가능 

이런 경우의 테이블 
=>회원 ID 친ID 이름으로 구성된 테이블 

=>관계형 데이터베이스의 단점 2가지 
데이터가 많아지면 분할해서 저장해야 하는데 JOIN을 수행해야해서 조회속도가 
느려진다. 

테이블을 만들고 그 테이블 구조를 기억해서 데이터를 삽입하기 때문에 테이블구조 
가 변경되어야 하면 기존 데이터를 전부 수정해야 합니다. 

 ** SUB QUERY 
 =>SQL의 하나의 절안에 다른 SQL(select구문)이 존재하는 경우 
 => 서브쿼리는 반드시 괄호안에 작성해야하고 메인쿼리가 수행되기 전에 1번만 수행 
 
 1.종류 
 1) 단일행 서브쿼리
 =>서브쿼리의 결과가 하나의 값으로 나오는 경우 
 =>이 경우에는 단일행 연산자를 이용해 비교해야 합니다. 
 >,>=,<,<=,=,<>(!=) 
 
 2) 다중행 서브쿼리
 => 서브쿼리의 결과가 2개 이상의 값으로 나오는 경우 
 =>이 경우에는 단일 행 연산자를 사용하면 에러가 발생 
 =>다중행 연산자 
 in  : 포함된 
 not in : 포함되지 않은 
 any : 부등호 연산자와 함꼐 써서 하나라도 만족하면 
 all  : 부등호 연산자와 함께 써서 모두 만족하면 
 
 =>any나 all은 max나 min을 이용해서 작성할 수 있습니다. 
 
2. select절에 출력되는 컬럼들이 2개의 테이블을 조합해야하는 경우에는 
join을 해야하고 하나의 테이블에 있는 컬럼들만 출력할 때는 sub query를 이용
모든 sub query는 join을 이용해서 가능합니다. 
동일한 작업을 sub query로 할 수 있고 join으로도 할 수 있는 경우에는 
sub query로 해야 합니다. 

ex) buytbl테이블에서 물건을 2번이상 구매한 (userid가 2번이상 나온)사람의
usertbl테이블의 userid와 name을 조회 

  
 
 
  
  
  
  
  
  
  
 
 */
}
