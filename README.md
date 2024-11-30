work test
걷기 기능 테스트 및 죽지 않는 백그라운드활용 해보기


git 참고
1. 현재 브랜치 확인
   
   터미널에서 아래 명령어를 입력해 현재 브랜치를 확인
```
    git branch
```
3. 브랜치 전환
   
   만약 브랜치가 존재하면 아래 명령어로 전환

     git checkout main(예시)
   
   브랜치가 없으면 생성 후 전환
   
   git checkout -b main(예시)
   
5. 브랜치 병합하기
   
   main 브랜치로 전환한 상태에서 아래 명령어 실행 master(예시) -> main(예시)
   
   git merge master(예시)
   
   병합후 원격 저장소에 push
   
   git push origin main(예시)
   
7. 브랜치 정리
   
   로컬 저장소에서 삭제

   git branch -d master
   
   원격 저장소에 삭제
   
   git push origin --delete master
   
   만약 삭제하려는 브랜치가 병합되지 않았거나, 번경 사항이 남아있다면 -d대신 -D옵션을 사용해 강제 삭제
   
9. 원격 브랜치 확인
    
   git branch -r

   ### 커밋 기록 확인
   
   ## **1. 로컬 저장소 git Log 보기**
   
   + 상단 매뉴에서 VSC > Git > Show Git Log를 클릭

