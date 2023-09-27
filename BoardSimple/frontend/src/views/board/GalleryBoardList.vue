<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>갤러리</h1>
      </div>
    </div>

    <div>
      <SearchBar :categoryList="categoryList"
                 :totalListCount="totalListCount"
                 :parentCurrentPage="this.boardSearchCondition.currentPage"
                 @search="loadBoardList"/>
      <RegisterButton @goToWrite="moveToWrite"
                      v-if="isAuthenticated"/>
      <GalleryListTable :galleryList="galleryList"
                        @showDetail="moveToDetail"/>
      <BoardPagination :totalListCount="totalListCount"
                  :pageSize="this.boardSearchCondition.pageSize"
                  @pageChange="changePage"/>
    </div>
  </div>
</template>

<script>
import {getGalleryBoardList} from "@/api/BoardAxios";
import RegisterButton from "@/components/board/RegisterButton.vue";
import SearchBar from "@/components/board/SearchBar.vue";
import GalleryListTable from "@/components/board/GalleryListTable.vue";
import BoardPagination from "@/components/board/BoardPagination.vue";

export default {
  name: 'GalleryBoardList',
  components: {
    BoardPagination,
    SearchBar,
    GalleryListTable,
    RegisterButton
  },
  data() {
    return {
      totalListCount: undefined, // 서버에서 받아오는 총 게시물 수
      galleryList: [], // 게시글 리스트
      categoryList: {}, // 카테고리명 리스트

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
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    }
  },
  created() {
    this.loadBoardList()
  },
  methods: {
    loadBoardList(boardSearchCond) {

      // null이 아닌경우 검색 조건을 적용합니다.
      if (boardSearchCond != undefined) {
        this.boardSearchCondition = boardSearchCond;
        console.log("updated boardSearchCondition", this.boardSearchCondition)
      }

      // 검색 요청 및 결과를 업데이트 합니다.
      getGalleryBoardList(boardSearchCond)
          .then(({galleryList, totalListCount, categoryList}) => {
                this.galleryList = galleryList;
                this.totalListCount = totalListCount;
                this.categoryList = categoryList;

                console.log("galleryList", this.galleryList)
                console.log("totalListCount", this.totalListCount)
                console.log("categoryList", this.categoryList)

              }
          )
    },
    changePage(newPage) {
      // 페이지 변화를 적용합니다.
      console.log("currentPage:", newPage)
      this.currentPage = newPage;
      this.loadBoardList(this.boardSearchCondition)
    },
    moveToDetail(seq) {
      this.$router.push({
        name: 'GalleryBoardDetail',
        params: {seq: seq},
      })
    },
    moveToWrite() {
      this.$router.push({name: 'GalleryBoardWrite'})
    }
  }
};
</script>
