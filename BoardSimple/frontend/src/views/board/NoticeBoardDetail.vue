<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>

  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>공지사항</h1>
      </div>
    </div>
    <div>
      <BoardDetail :board="board"/>
      <DetailButtons @moveToList="moveToList"/>
    </div>
  </div>
</template>

<script>
import {getNoticeBoardDetail} from "@/api/BoardAxios";
import BoardDetail from "@/components/board/BoardBody.vue";
import DetailButtons from "@/components/board/DetailButtons.vue";

export default {
  name: 'NoticeBoardDetail',
  components: {
    DetailButtons,
    BoardDetail,
  },
  data() {
    return {
      board: {}, // 게시글
      boardSearchCondition: {} // 검색 조건
    }
  },
  created() {
    const seq = this.$route.params.seq;
    this.loadBoard(seq)
  },
  methods: {
    //todo
    async loadBoard(seq) {
      await getNoticeBoardDetail(seq)
          .then((board) => {
            this.board = board
            console.log("넘겨 받은 board:", this.board);
          })
    },
    moveToList() {
      this.$router.push({
        name: 'NoticeBoardList'
      })
    }
  },
}
</script>

