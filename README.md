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
>|한정아<br>(본인)|<img width="650" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/a61fe489-6f33-4cc1-bacf-4a49d3ddfed3">|
>|이유나|<img width="650" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/5d9fa025-5bc6-4b54-8a24-99dc95a2baf8">|
>|최명훈|<img width="650" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/de54c6eb-0a32-4296-bc9d-e345dc83b343">|
>|유재욱|<img width="650" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/a5cebb3a-e3ae-4626-8d4f-a87bb4a1608e">|
>|송슬기|<img width="650" alt="image" src="https://github.com/JeongahHan/Greenbook/assets/142190043/e175d855-31da-47d9-9fb4-20da78e76207">|

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
  <img src="https://github.com/JeongahHan/Greenbook/assets/142190043/c24d6bbd-4ee8-45f6-9425-69c08a6a4812">
</p>
<br>
<br>
<br>
<br>










