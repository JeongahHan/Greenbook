# Greenbook
<br>
<br>
<p align="center"><img alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/46a013eb-80f6-47b7-8b7d-95169828b08c"></p>
<br>
<br>

# I.프로젝트 정보
<br>
<br>

**1. 소개**
> 사용자들끼리 '책'을 사고 팔수 있는, 온라인 중고 책 거래 플랫폼입니다.
<br>
<br>

**2. 제작기간**
> 기간 : 2023.08.17 ~ 2023.08.31 (2주)
<br>
<br>

**3. 주요기능**
>+ 중고거래 게시판 : 책 구매 or 판매 <br>
>    + 관심상품 찜 기능
>    + 구매요청 기능
>    + 댓글 작성 기능<br>
>+ 독서후기 공유 게시판 <br>
>    + 자유로운 글쓰기 및 댓글 작성 기능
>    + 댓글 內 좋아요 버튼 기능<br>
>+ 외부 서점의 베스트셀러 연동으로, 매일 무슨 책이 인기있는지 확인
>+ 통합검색 시, 중고나라게시판 內 매물 & 독서후기게시판 內 후기 동시 확인
>+ 유저끼리 쪽지 보내기/받기 기능
<br>
<br>

**4.개발 환경**
<br>
<br>

- 스프링부트 프레임워크
- html,css,javascript,JQuery
- Oracle
  
<img width="1010" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/1c2a6edb-f912-4f7c-b3fc-f8192d662bc9">


<br>
<br>

**5. ERD**

![image](https://github.com/JeongahHan/Greenbook/assets/142190043/c9578c10-e833-4bb9-adf8-19b50ff569e5)


<br>
<br>

**6. 참여인원 및 역할분담**
>|이름|구현기능|
>|---|---|
>|한정아<br>(본인)|<img width="850" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/a61fe489-6f33-4cc1-bacf-4a49d3ddfed3">|
>|이유나|<img width="800" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/5d9fa025-5bc6-4b54-8a24-99dc95a2baf8">|
>|최명훈|<img width="800" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/de54c6eb-0a32-4296-bc9d-e345dc83b343">|
>|유재욱|<img width="800" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/a5cebb3a-e3ae-4626-8d4f-a87bb4a1608e">|
>|송슬기|<img width="800" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/e175d855-31da-47d9-9fb4-20da78e76207">|

<br>
<br>
<br>
<br>

# II.구현결과


내 기능
-----------
**1.전체적인 front구성**
<p align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/cb2a56af-c834-48ed-ab43-ef526aeb98c3">
</p>
<br>
<br>
<br>
<br>

**2.메인 내 찜 많은 상품 Top20 기능 구현**
<div align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/8fc3b1e2-0b42-43f4-b62e-7eb84d115679">
</div>
<br>
<br>
<br>
<br>

**3.외부 베스트셀러 API가져오기**
<p align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/be150ba3-ea71-4671-ab00-65077f53fbf2">
</p>
<br>
<br>
<br>
<br>

**4.독서후기게시판 제작**
+ 게시판 글 작성 / 읽기 / 수정 / 삭제
+ 게시판 내 (제목,내용,작성자) 분류하여 검색 기능 구현
<br>
<p align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/832844cb-7968-4217-b6e4-534741ef4e6e">
</p>
<br>
<br>
<br>
<br>

**5.댓글 / 좋아요 기능구현**
+ 댓글 작성 / 읽기 / 수정 / 삭제
+ 댓글 좋아요 / 좋아요 취소
+ 유저한테 쪽지보내기 기능 연결 
<br>
<p align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/eb69fd7c-993e-4e3f-9d84-dd9acf77c6f3">
</p>
<br>
<br>
<br>
<br>

**6. 모든 글 통합검색**
+ 통합검색 내 필터기능구현 (중고책방 게시판 글  / 독서노트 게시판 글)
+ 검색 항목 없을 시 , 이전으로 돌아가기
<br>
<p align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/647bbc31-453d-49bb-b559-9f18b3a9f269">
</p>
<p align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/9c3b2250-c965-43aa-802f-0a55ddcf74a6">
</p>
<br>
<br>
<br>
<br>

**7. 회사소개**
+ 스크롤에 반응하는 소개 페이지 제작
<br>
<p align-="center">
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/b51ecb18-fc0b-4504-a17c-8398458e252e">
</p>
<br>
<br>
<br>
<br>
---------------

<br>
<br>


![image](https://github.com/JeongahHan/Greenbook/assets/142190043/3a928ae3-6a3f-4523-89c9-2d5bcc5251c2)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/21e20433-5870-4120-9937-7e62d280f6b1)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/6dabfd7c-81c3-411d-91f6-4892a3d2f7c9)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/b08c03f9-38aa-4fb7-a149-1475fbc403e3)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/f1d108f7-7a74-4206-9e01-3b3aea58d43c)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/acc23756-9d6c-4e15-97a9-6f188fbbbfb3)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/6c2a8297-2fc4-4a36-af32-bdbe411afdd2)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/f0fd8b88-04f5-4e1a-8e95-77ccbbb728ee)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/54177703-98e1-449d-8845-992cbc1bf130)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/620cfb22-cfe2-4eda-96e2-8c8eb127ff67)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/a9f81162-7463-4198-8704-8d364b7080a3)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/56d8cd04-e8d1-40c9-9f2b-1e8bfb170b8c)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/426d06c7-354d-470c-8e6a-08412b29f9d2)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/56a803f0-728f-479b-86af-00b622836bea)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/512e01e0-c096-42d4-9415-dc7b372266b7)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/0692c9a8-a4a4-4524-bf04-c412835c6aad)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/08f6821b-c867-4efb-abb6-9611c73368eb)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/e2c0c15a-c7f1-4194-bc27-80f8693d8a64)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/83ca47ed-8958-44fe-83f0-208fc51d9310)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/66a96a3c-3ef7-4ee5-8b7b-592597812e35)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/0eedfeb7-3e61-4ea6-8482-36e2b14e9a80)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/9b524b19-63c1-4f15-82b1-37eb5b6b0a05)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/5112b4eb-6780-4040-b5fd-86b798143674)
<br>
![image](https://github.com/JeongahHan/Greenbook/assets/142190043/5dbdfae4-eb6d-4eb3-9302-2ddd178f3ab8)
<br>
<br>















