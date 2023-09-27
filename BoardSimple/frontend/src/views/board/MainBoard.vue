<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>
  <div class="container">
    <div class="row d-flex justify-content-center">
      <div class="col-md-6">
        <MiniBoard :board-list="noticeBoardList"
                   :alert-list="alertList"
                   :board-title="'공지사항'"
                   :path="'/board/notices'"></MiniBoard>
      </div>
      <div class="col-md-6">
        <MiniBoard :board-list="freeBoardList"
                   :board-title="'자유 게시판'"
                   :path="'/board/frees'"></MiniBoard>
      </div>
      <div class="col-md-6">
        <MiniGalleryBoard  :gallery-list="galleryList"
                   :board-title="'갤러리'"
                   :path="'/board/galleries'"></MiniGalleryBoard>
      </div>
      <div class="col-md-6">
        <MiniQna :qna-list="qnaList"
                 :qna-title="'문의글'"
                 :path="'/qnas'"></MiniQna>
      </div>
    </div>

  </div>

</template>
<script>
import {getMainFreeBoardList, getMainGalleryBoardList, getMainNoticeBoardList} from "@/api/BoardAxios";
import MiniBoard from "@/components/board/MiniBoard.vue";
import {getMainQnaList} from "@/api/QnaAxios";
import MiniQna from "@/components/qna/MiniQna.vue";
import MiniGalleryBoard from "@/components/board/MiniGalleryBoard.vue";

export default {
  name: 'MainBoard',
  components: {MiniGalleryBoard, MiniQna, MiniBoard},
  created() {
    this.loadMainBoard(5)
    console.log("created")
  },
  mounted() {
    console.log("mounted")
  },
  data() {
    return {
      noticeBoardList: [], // 공지사항 리스트
      alertList: [], // 알람글 리스트
      freeBoardList: [], // 자유게시글 리스트
      galleryList: [], // 갤러리 게시글 리스트
      qnaList: [] // 문의글 리스트
    }
  },
  methods: {
    async loadMainBoard(count) {

      getMainNoticeBoardList(count)
          .then(({boardList, alertList}) => {
            this.noticeBoardList = boardList;
            this.alertList = alertList;
          })

      getMainFreeBoardList(count + 1)
          .then(({boardList}) => {
            this.freeBoardList = boardList;
          })

      getMainQnaList(count + 1)
          .then(({qnaList}) => {
            this.qnaList = qnaList;
          })

      getMainGalleryBoardList(3)
          .then(({galleryList}) => {
            this.galleryList = galleryList;
          })

      console.log("free", this.freeBoardList)
      console.log("notices", this.noticeBoardList)
      console.log("alert", this.alertList)
      console.log("qnaList", this.qnaList)
      console.log("galleryList", this.galleryList)

    }
  },

}

</script>
<style scoped>

</style>