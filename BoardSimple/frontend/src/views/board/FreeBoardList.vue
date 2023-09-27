<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>자유게시판</h1>
      </div>
    </div>

    <div>
      <SearchBar :categoryList="categoryList"
                 :parentCurrentPage="this.boardSearchCondition.currentPage"
                 @search="changeSearch"/>
      <RegisterButton @goToWrite="moveToWrite"
                      v-if="isAuthenticated"/>
      <ListTable :boardList="boardList"
                 @showDetail="moveToDetail"/>
      <BoardPagination :totalListCount="this.totalListCount"
                       :pageSize="this.boardSearchCondition.pageSize"
                       :parentCurrentPage="this.boardSearchCondition.currentPage"
                       @pageChange="changePage"/>
    </div>
  </div>
</template>

<script>
import {getFreeBoardList} from "@/api/BoardAxios";
import ListTable from "@/components/board/ListTable.vue";
import RegisterButton from "@/components/board/RegisterButton.vue";
import SearchBar from "@/components/board/SearchBar.vue";
import BoardPagination from "@/components/board/BoardPagination.vue";

export default {
  name: 'FreeBoardList',
  components: {
    RegisterButton,
    BoardPagination,
    ListTable,
    SearchBar
  },
  data() {
    return {
      boardList: [], // 게시글 리스트
      categoryList: {}, // 카테고리명 리스트
      totalListCount: undefined, // 총 게시물 수 (검색조건에 상응하는)

      // 검색 조건
      boardSearchCondition: {
        // 검색, 정렬 변수
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
        pageSize: 10,
      },


    };
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    }
  },
  async created() {
    // 현재 라우터의 query 파라미터 가져오기
    const queryParam = this.$route.query.boardSearchCondition;
    console.log('queryParam:', queryParam);

    if (queryParam) {
      try {
        this.boardSearchCondition = JSON.parse(queryParam);
        await this.loadBoardList(this.boardSearchCondition);
      } catch (error) {
        console.log("error 발생:", error);
      }
    } else {
      await this.loadBoardList(this.boardSearchCondition);
    }
  },
  async beforeRouteUpdate(to, from, next) {
    console.log("beforeRouteUpdate() 훅 시행")
    const queryParam = to.query.boardSearchCondition;
    console.log('queryParam:', queryParam);

    // 필요한 로직 수행
    if (queryParam) {
      // JSON 문자열을 JavaScript 객체로 변환 (예: {"key": "value"})
      try {
        this.boardSearchCondition = JSON.parse(queryParam);
        await this.loadBoardList(this.boardSearchCondition);
      } catch (error) {
        console.log("error 발생:", error);
      }
    } else {
      await this.loadBoardList(this.boardSearchCondition);
    }

    next();
  },

  methods: {
    /**
     * 검색 조건에 맞춰 문의글 리스트를 조회합니다.
     * @param boardSearchCondition 검색 조건
     */
    async loadBoardList(boardSearchCondition) {

      // null이 아닌경우 검색 조건을 적용합니다.
      if (boardSearchCondition != undefined) {
        this.boardSearchCondition = boardSearchCondition;
        console.log("updated boardSearchCondition", this.boardSearchCondition)
      }

      // 검색 요청 및 결과를 업데이트 합니다.
      await getFreeBoardList(boardSearchCondition)
          .then(({boardList, totalListCount, categoryList}) => {
                this.boardList = boardList;
                this.totalListCount = totalListCount;
                this.categoryList = categoryList;

                console.log("totalListCount", this.totalListCount)
                console.log("pageSize", this.boardSearchCondition.pageSize)
                console.log("categoryList", this.categoryList)
              }
          )
    },
    changeSearch(boardSearchCondition) {
      this.boardSearchCondition = boardSearchCondition;
      this.$router.push({
        name: 'FreeBoardList',
        query: {boardSearchCondition: JSON.stringify(this.boardSearchCondition)}
      })
    },
    /**
     * 페이지 변화를 적용합니다.
     * @param newPage
     */
    changePage(newPage) {
      // 페이지 변화를 적용합니다.
      this.boardSearchCondition.currentPage = newPage;
      console.log("변경된 page:", this.boardSearchCondition.currentPage);
      this.$router.push({
        name: 'FreeBoardList',
        query: {boardSearchCondition: JSON.stringify(this.boardSearchCondition)}
      })
    },
    /**
     * 상세 페이지로 이동합니다.
     * @param seq 게시글 번호
     */
    moveToDetail(seq) {
      this.$router.push({
        name: 'FreeBoardDetail',
        params: {seq: seq},
        query: {boardSearchCondition: JSON.stringify(this.boardSearchCondition)}
      })
    },
    /**
     * 새로운 게시글 작성 페이지로 이동합니다.
     */
    moveToWrite() {
      this.$router.push({name: 'FreeBoardWrite'})
    }
  }
};
</script>
