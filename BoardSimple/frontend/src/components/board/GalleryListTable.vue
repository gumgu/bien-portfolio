<template>
   <div class="gallery-item" v-for="gallery in galleryList" :key="gallery.seq" @click="clickBoard(gallery.seq)">
     <div class="img-box">
       <img v-if="gallery.fileName" :src="'/upload/' + gallery.fileName">
       <img v-else src="/upload/placeholder.png">
     </div>
     <div class="content">
       <h3>{{gallery.subject}}</h3>
       <p>{{ gallery.content}}</p>
     </div>
   </div>

  <div class="non-data" v-if="galleryList.length === 0">
    <span>조회된 데이터가 없습니다.</span>
  </div>
</template>

<script>
export default {
  name: 'GalleryListTable',
  props: {
    galleryList: {}
  },
  created() {
    console.log()
  },
  emits: ['showDetail'],
  methods: {
    clickBoard(seq) {
      this.$emit('showDetail', seq);
    }
  },
};
</script>

<style scoped>
.gallery-item {
  display: flex;
  border: 1px solid #ccc;
  padding: 10px;
  height: 150px;
  align-items: stretch; /* 이미지 높이 맞추기 */
  margin-bottom: 15px;
}

.gallery-item .img-box {
  flex: 2; /* 이미지 영역 크기 비율 */
  margin-right: 10px;
  text-align: center;
}

.gallery-item img {
  width: auto;
  max-height: 100%;

}

.gallery-item .content {
  flex: 8; /* 내용 영역 크기 비율 */
  margin: 10px;
}

.gallery-item h3 {
  font-size: 1.2em;
}

.gallery-item p {
  font-size: 1em;
  margin-top: 5px;
  color: #666;
}

.non-data {
  text-align: center;
  margin: 70px 0;
}

.non-data span {
  font-size: 1.5rem; /* h4 크기로 조절 */
}
</style>