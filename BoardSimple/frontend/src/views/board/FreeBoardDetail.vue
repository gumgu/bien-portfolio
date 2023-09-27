<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>자유게시판</h1>
      </div>
    </div>
    <div>
      <div class="container mt-4">
        <div class="detail-container">
          <BoardBody :board="board"/>

          <div class="attachment-container" v-if="board.files" v-for="file in board.files">
            <div class="attachment-box" @click="downloadFile(file)"> {{ file.originName }}</div>
          </div>

          <ReplyBox :replyList="replyList"
                    @registerReply="registerReply"
                    @removeReply="removeReply"/>
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
import {deleteFreeBoard, getFreeBoardDetail} from "@/api/BoardAxios";
import ReplyBox from "@/components/board/ReplyBox.vue";
import {deleteReply, getReplyList, saveReply} from "@/api/ReplyAxios";
import DetailButtons from "@/components/board/DetailButtons.vue";
import BoardBody from "@/components/board/BoardBody.vue";
import {downloadFile} from "@/api/FileAxios";

export default {
  name: 'FreeBoardDetail',
  components: {DetailButtons, ReplyBox, BoardBody},
  data() {
    return {
      seq: Number, // 개시글 번호
      board: {}, // 게시글
      replyList: {}, // 댓글 리스트
    }
  },
  created() {
    this.seq = this.$route.params.seq;
    this.loadBoard(this.seq);
    this.loadReply(this.seq);

  },
  methods: {
    /**
     * 전달받은 seq의 게시글을 조회합니다.
     * @param seq
     * @returns {Promise<void>} 게시글 내용
     */
    async loadBoard(seq) {
      try {
        const { board } = await getFreeBoardDetail(seq);
        this.board = board;
      } catch(error) {
        console.log(error);
      }
    },
    /**
     * 전달받은 seq 게시글의 댓글을 조회합니다.
     * @param seq
     * @returns {Promise<void>} 댓글 내용
     */
    async loadReply(seq) {
      this.replyList = await getReplyList(seq);
    },
    /**
     * 전달받은 file을 다운로드합니다.
     * @param file
     */
    downloadFile(file) {
      downloadFile(file);
    },
    // 리스트 이동
    moveToList() {
      this.$router.push({name: 'FreeBoardList'})
    },

    // 수정 페이지 이동
    moveToUpdate() {
      this.$router.push({
        name: 'FreeBoardUpdate',
        params: {seq: this.seq}
      })
    },

    // 게시글 삭제
    moveToDelete() {
      deleteFreeBoard(this.seq);
      this.$router.push({ name: 'FreeBoardList' });
    },
    /**
     * 새로운 댓글을 등록합니다.
     * @param replyContent 등록할 댓글 내용
     * @returns {Promise<void>}
     */
    async registerReply(replyContent) {

      const reply = {
        content: replyContent,
        boardSeq: this.seq
      };

      await saveReply(reply);
      await this.loadReply(this.seq);
    },
    /**
     * 댓글을 삭제합니다.
     * @param replySeq 삭제할 댓글 번호
     * @returns {Promise<void>}
     */
    async removeReply(replySeq) {
      await deleteReply(replySeq);
      await this.loadReply(this.seq);
    }
  },
}
</script>
<style scoped>
.detail-container {
  border: 1px solid #ccc;
  margin-top: 10px;
  padding: 30px;
}

.attachment-box {
  margin: 10px;
}

</style>