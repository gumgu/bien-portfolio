<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>

  <div class="container mt-4">

    <form @submit.prevent="submitSearch">
      <div class="row search-container">
        <div class="col-md-5">
          <div class="search-box">
            <div class="search-group">
              <span>등록일시</span>
              <input type="date" class="form-control" v-model="boardSearchCondition.fromDate">
              <span>~</span>
              <input type="date" class="form-control" v-model="boardSearchCondition.toDate">
            </div>
          </div>
        </div>
        <div class="col-md-2"  v-if="!isQna">
          <div class="search-box">
            <select class="form-control" v-model="boardSearchCondition.categoryId">
              <option value="">전체 분류</option>
              <option v-for="i in categoryList"
                      :key=i
                      :value="i.id">{{ i.categoryName }}
              </option>
            </select>
          </div>
        </div>
        <div class="col-md-4">
          <div class="search-box">
            <div class="search-group">
              <input type="text" class="form-control" placeholder="제목 검색" v-model="boardSearchCondition.search">
            </div>
          </div>
        </div>
        <div class="col-md-1 align-self-center">
          <button type="submit" class="btn btn-primary">검색</button>
        </div>
        <div class="col-md-3" v-if="isQna">
          <div class="search-box">
            <div class="search-group">
              나의 문의내역만 보기
              <input type="checkbox" v-model="boardSearchCondition.myQnaOnly">
            </div>
          </div>
        </div>
      </div>
    </form>


    <!-- 반응형 정렬 기능 -->
    <div class="row additional-search">
      <!-- 페이지 사이즈 -->
      <div class="col-md-6 d-flex justify-content-start">
        <div class="search-box d-flex align-items-center">
          <select class="form-select col-md-2" v-model="boardSearchCondition.pageSize" @change="this.submitSearch">
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="30">30</option>
            <option value="40">40</option>
            <option value="50">50</option>
          </select>
        </div>
        <span class="d-flex align-items-center">개씩 보기</span>
      </div>

      <!-- 정렬 기준 -->
      <div class="col-md-6 d-flex justify-content-end">
        <span class="d-flex align-items-center">정렬</span>
        <div class="search-box d-flex align-items-center">
          <select class="form-select col-md-2" v-model="boardSearchCondition.orderStandard" @change="this.submitSearch">
            <option value="date">등록일시</option>
            <option value="search">제목</option>
            <option value="categoryName">분류</option>
            <option value="readCount">조회수</option>
          </select>
        </div>

        <!-- 오름차순/내림차순 -->
        <div class="search-box d-flex align-items-center">
          <select class="form-select" v-model="boardSearchCondition.orderSequence" @change="this.submitSearch">
            <option value="0">오름차순</option>
            <option value="1">내림차순</option>
          </select>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: 'SearchBar',
  data() {
    return {
      boardSearchCondition: { // 검색, 정렬 변수
        fromDate: undefined,
        toDate: undefined,
        search: undefined,
        categoryId: '',
        orderStandard: 'date',
        orderSequence: 0,
        // qna 변수
        myQnaOnly: undefined,

        // 페이징 변수
        pageSize: 10,
      }
    }
  },
  props: {
    categoryList: {
      type: Object
    },
    parentTotalListCount: {
      type: Number
    },
    parentCurrentPage: {
      type: Number
    },
    isQna: {
      // qna인 경우 "나의 문의내역보기"를 활성화합니다.
      type:Boolean,
      required: false,
      default: false
    }
  },
  emits: ['search'],
  methods: {
    submitSearch() {
      console.log(this.boardSearchCondition)
      this.$emit('search', this.boardSearchCondition);
    },
  }
}
</script>

<style>
span {
  padding: 0.375rem 0.75rem
}

.search-box {
  padding: 10px;
}

.search-group {
  display: flex;
  justify-content: space-between;
}

.search-group input {
  flex: 1;
  margin-right: 10px;
}

.search-container {
  border: 1px solid #ccc;
  margin-top: 10px;
}
</style>