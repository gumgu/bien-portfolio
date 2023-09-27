<template>
  <div class="page-container align-items-center">
    <div class="pagination d-flex justify-content-center">
      <button class="btn" @click="prevPage" v-if="maxPage != 1" :disabled="currentPage === 1">이전</button>
      <button class="btn" v-for="i in pageList" :key="i" :disabled="currentPage === i" @click="setPage(i)"> {{ i }}
      </button>
      <button class="btn" @click="nextPage" v-if="maxPage != 1" :disabled="currentPage === maxPage">다음</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BoardPagination',
  data() {
    return {
      currentPage: this.parentCurrentPage,
      chapterSize: 10
    }
  },
  props: {
    totalListCount: { // 총 게시물 수
      required: true
    },
    pageSize: { // 페이지당 출력할 게시글 수
      type: Number,
      required: true
    },
    parentCurrentPage: {
      type: Number
    },
  },
  computed: {
    startPage() {
      let temp = (Math.ceil(this.currentPage / this.chapterSize) - 1) * this.chapterSize + 1
      return temp;
    },
    endPage() {
      let temp = this.startPage + this.pageSize - 1
      if (temp > this.maxPage) {
        temp = this.maxPage;
      }
      return temp;
    },
    maxPage() {
      let temp = Math.ceil(this.totalListCount / this.pageSize);
      return temp;
    },
    pageList() {
      let pageRange = [];
      console.log("totalListCount", this.totalListCount)
      console.log("currentPage", this.currentPage)
      console.log("pageSize", this.pageSize)
      console.log("startPage", this.startPage)
      console.log("endPage", this.endPage)
      for (let i = this.startPage; i <= this.endPage; i++) {
        pageRange.push(i);
      }
      console.log("pageRange:", pageRange)
      return pageRange
    }
  },
  emits: ['pageChange'],
  methods: {
    prevPage() {
      if (this.currentPage > 1) {
        this.$emit('pageChange', this.currentPage - 1);
      }
    },
    nextPage() {
      if (this.currentPage < this.maxPage) {
        this.$emit('pageChange', this.currentPage + 1);
      }
    },
    setPage(page) {
      this.currentPage = page;
      this.$emit('pageChange', page);
    }
  },
};
</script>

<style>
.page-container {
  padding: 15px;
}
</style>