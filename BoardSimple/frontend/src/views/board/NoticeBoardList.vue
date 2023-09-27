<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>공지사항</h1>
      </div>
    </div>

    <div>
      <SearchBar :categoryList="categoryList"
                 :totalListCount="totalListCount"
                 :parentCurrentPage="this.boardSearchCondition.currentPage"
                 @search="loadBoardList"/>
      <ListTable :boardList="boardList"
                 :alertList="alertList"
                 @showDetail="moveToDetail"/>
      <BoardPagination :totalListCount="totalListCount"
                  :pageSize="this.boardSearchCondition.pageSize"
                  @pageChange="changePage"/>
    </div>
  </div>
</template>

<script>
import {getNoticeBoardList} from "@/api/BoardAxios";
import ListTable from "@/components/board/ListTable.vue";
import SearchBar from "@/components/board/SearchBar.vue";
import BoardPagination from "@/components/board/BoardPagination.vue";

export default {
  name:'NoticeBoardList',
  components: {
    BoardPagination,
    SearchBar,
    ListTable,
  },
  data() {
    return {
      totalListCount: undefined, // 서버에서 받아오는 총 게시물 수
      boardList: [], // 게시글 리스트
      alertList: [], // 알림글 리스트
      categoryList: [], // 카테고리명 리스트

      boardSearchCondition: {
        // 검색, 정렬 변수
        type: undefined,
        fromData: undefined,
        toDate: undefined,
        search: undefined,
        categoryId: '',
        orderStandard: 'date',
        orderSequence: 0,
        // qna 변수
        myQnaOnly: undefined,

        // 페이징 변수
        currentPage: 1,
        pageSize: 30,
        totalListCount: undefined
      }
    };
  },
  created() {
    this.loadBoardList()
  },
  methods: {
    async loadBoardList(boardSearchCondition) {

      // null이 아닌경우 검색 조건을 적용합니다.
      if (boardSearchCondition != undefined) {
        this.boardSearchCondition = boardSearchCondition;
        console.log("updated boardSearchCondition", this.boardSearchCondition)
      }

      // 검색 요청 및 결과를 업데이트 합니다.
      await getNoticeBoardList(this.boardSearchCondition)
          .then(({boardList, alertList, categoryList, totalListCount}) => {
                this.boardList = boardList;
                this.alertList = alertList;
                this.categoryList = categoryList;
                this.totalListCount = totalListCount;
              }
          )
    },
    changePage(newPage) {
      // 페이지 변화를 적용합니다.
      this.boardSearchCondition.currentPage = newPage;
      this.loadBoardList(this.boardSearchCondition)
    },
    moveToDetail(seq) {
      this.$router.push({
        name: 'NoticeBoardDetail',
        params: { seq: seq },
      })
    }
  }
};
</script>
