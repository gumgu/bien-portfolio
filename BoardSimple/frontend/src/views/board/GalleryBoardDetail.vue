<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>

  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>갤러리</h1>
      </div>
    </div>
    <div>
      <div class="container mt-4">
        <div class="detail-container">
          <BoardBody :board="board"/>

          <carousel :nav="true" :dots="true">
            <slide v-for="file in board.files" :key="file.seq">
              <img :src="'/upload/'+ file.fileName">
            </slide>
          </carousel>

          <DetailButtons :memberId="board.memberId"
                         @moveToList="moveToList"
                         @modifyPost="moveToUpdate"
                         @deletePost="moveToDelete"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {deleteGalleryBoard, getGalleryBoardDetail} from "@/api/BoardAxios";
import DetailButtons from "@/components/board/DetailButtons.vue";
import BoardBody from "@/components/board/BoardBody.vue";
import 'vue3-carousel/dist/carousel.css'
import {Carousel, Slide} from 'vue3-carousel'

export default {
  name: 'GalleryBoardDetail',
  components: {
    DetailButtons,
    BoardBody,
    Carousel,
    Slide
  },

  data() {
    return {
      seq: Number, // 개시글 번호
      board: {}, // 게시글
      replyList: {}, // 댓글 리스트
    }
  },
  created() {
    this.seq = this.$route.params.seq;
    this.loadBoard(this.seq)
  },
  methods: {
    /**
     * 전달받은 seq의 게시글을 조회합니다.
     * @param seq
     * @returns {Promise<void>} 게시글 내용
     */
    async loadBoard(seq) {
      await getGalleryBoardDetail(seq)
          .then(({board}) => {
            this.board = board;
          })
    },
    // 리스트로 이동
    moveToList() {
      this.$router.push({name: 'GalleryBoardList'})
    },

    // 수정 페이지 이동
    moveToUpdate() {
      this.$router.push({
        name: 'GalleryBoardUpdate',
        params: {seq: this.seq}
      })
    },

    // 게시글 삭제
    async moveToDelete() {
      await deleteGalleryBoard(this.seq);
      await this.$router.push({name:'GalleryBoardList'});
    },
  },
}
</script>
<style scoped>
.detail-container {
  border: 1px solid #ccc;
  margin-top: 10px;
  padding: 30px;
}
</style>