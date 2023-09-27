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
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="gallery in galleryList" :key="gallery.seq">
        <td>{{ gallery.seq }}</td>
        <td>{{ gallery.categoryName }}</td>
        <td>
          <div class="img-box">
            <img v-if="gallery.fileName" :src="'/upload/' + gallery.fileName">
            <img v-else src="/upload/placeholder.png">
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<script>
export default {
  name: 'MiniGalleryBoard',
  props: {
    galleryList: { // 게시글 리스트
      type: Object,
      required: true
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

.table th, .table td {
//border: 1px solid #ccc; padding: 8px; text-align: center;
}

/* 테이블 헤더 스타일 */
.table th {
  background-color: #f2f2f2;
}

a {
  color:black;
  text-decoration: none;
}

/* 갤러리용 이미지 스타일 */

.img-box {
  flex: 2; /* 이미지 영역 크기 비율 */
  margin-right: 10px;
  text-align: center;
  height: 64px;
}

img {
  width: auto;
  max-height: 100%;
}
</style>
