<template>
  <div class="mini-board">
    <div class="row">
      <h4 class="title d-flex justify-content-center">
        {{ qnaTitle }}
      </h4>
      <div class="row">
        <router-link :to=path class="d-flex justify-content-start col-md-6 mb-2">나의 문의 내역</router-link>
        <router-link :to=path class="d-flex justify-content-end col-md-6 mb-2">더보기 +</router-link>
      </div>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="qna in qnaList" :key="qna.seq">
        <td>{{ qna.seq }}</td>
        <td class="subject">{{ qna.questionSubject }}
          <span v-if="qna.hasAnswer">{{ "(답변완료)" }}</span>
          <span v-else>{{ "(미답변)" }}</span>
          <span v-if="qna.privacy">🔒︎</span>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<script>
export default {
  name: 'MiniQna',
  props: {
    qnaList: { // 문의글 리스트
      type: Object,
      required: true
    },
    qnaTitle: { // 문의글 타입명
      type: String,
      required: true
    },
    path: { // 더보기 url
      required: true
    }
  }

}
</script>

<style scoped>
/* 기본 스타일 */
.mini-board {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  display: inline-block;
  border: 1px solid #ccc;
  margin-bottom: 20px;
}

/* 게시판명 스타일 */
.title {
  margin-top: 10px;
}

/* 테이블 스타일 */
.table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #ccc;
}

.table th, {
  text-align: center;
}

/* 테이블 헤더 스타일 */
.table th {
  background-color: #f2f2f2;
}

a {
  color: black;
  text-decoration: none;
}

.subject {
  text-align: left;
}

</style>
