<template>

  <div class="detail-header">
    <h4 class="col-md-1">{{ this.board.categoryName }}</h4>
    <h2 class="col-md-8">{{ this.board.subject }}</h2>
    <div class="col-md-2">{{ dateTime(board.date, 'YYYY-MM-DD HH:ss') }}</div>
    <div class="col-md-1" v-if="board.adminId">{{ board.adminId }}</div>
    <div class="col-md-1" v-else>{{ board.memberId }}</div>
  </div>

  <hr class="hr">

  <div class="mb-5">
    <h3 class="detail-view-count">조회수 {{ board.readCount }}</h3>
  </div>

  <slot>
  </slot>

  <div class="detail-content">
    {{ board.content }}
  </div>

</template>

<script>
import {dateTime} from "../../util/ParamUtil";

export default {
  name: 'BoardBody',
  props: {
    board: {
      seq: String,
      memberId: String,
      adminId: String,
      categoryName: String,
      subject: String,
      readCount: Number
    },
  },
  methods: {
    dateTime
  }

}
</script>

<style scoped>
.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  margin-top: 15px;
}

.detail-view-count {
  font-size: 12pt;
  text-align: right;
  margin-bottom: 10px;
}

.detail-content {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}


</style>
