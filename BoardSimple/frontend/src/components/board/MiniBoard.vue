<template>
  <div class="mini-board">
    <div class="row">
      <h4 class="title d-flex justify-content-center">
        {{ boardTitle }}
      </h4>
      <router-link :to=path class="d-flex justify-content-end mb-2">더보기 +</router-link>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th>번호</th>
        <th>분류</th>
        <th>제목</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="alert in alertList" :key="alert.seq" class="bg-light">
        <td>{{ alert.seq }}</td>
        <td>{{ alert.categoryName }}</td>
        <td class="subject">{{ alert.subject }}</td>
      </tr>
      <tr v-for="board in boardList" :key="board.seq">
        <td>{{ board.seq }}</td>
        <td>{{ board.categoryName }}</td>
        <td class="subject">{{ board.subject }}</td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<script>
export default {
  name: 'MiniBoard',
  props: {
    boardList: { // 게시글 리스트
      type: Object,
      required: true
    },
    alertList: { // 알림글 리스트
      type: Object
    },
    boardTitle: { // 게시판 타입명
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

.subject{
  text-align: left;
}

/* 테이블 헤더 스타일 */
.table th {
  background-color: #f2f2f2;
  text-align: center;
}

td{
  text-align: center;
}

a {
  color:black;
  text-decoration: none;
}

</style>
