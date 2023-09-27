<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>자유게시판</h1>
      </div>
    </div>
    <div>
      <div class="container mt-4">
        <div class="detail-container">
          <QnaBody :qna="qna" />
          <DetailButtons :member-id="qna.memberId"
                         @moveToList="moveToList"
                         @modifyPost="moveToUpdate"
                         @deletePost="moveToDelete"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {getQnaDetail} from "@/api/QnaAxios";
import QnaBody from "@/components/qna/QnaBody.vue";
import DetailButtons from "@/components/board/DetailButtons.vue";
import {deleteFreeBoard} from "@/api/BoardAxios";

export default {
  name: 'QnaBoardDetail',
  components: {DetailButtons, QnaBody},
  data() {
    return {
      qna: {}, // 문의글
    }
  },
  created() {
    const seq = this.$route.params.seq;
    this.loadQna(seq)
  },
  methods: {
    async loadQna(seq) {
      this.qna = await getQnaDetail(seq);
    },
    // 리스트 이동
    moveToList() {
      this.$router.push({name: 'QnaList'})
    },

    // 수정 페이지 이동
    moveToUpdate() {
      this.$router.push({
        name: 'QnaUpdate',
        params: {seq: this.qna.seq}
      })
    },

    // 문의글 삭제
    moveToDelete() {
      deleteFreeBoard(this.seq);
      this.$router.push({ name: 'FreeBoardList' });
    },
  },
}
</script>
<style scoped></style>