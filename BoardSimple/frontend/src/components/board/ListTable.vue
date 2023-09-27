<template>
  <div>
    <table class="table">
      <thead>
      <tr>
        <th>번호</th>
        <th>분류</th>
        <th class="subject">제목</th>
        <th>조회</th>
        <th>등록일시</th>
        <th>등록자</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="alert in alertList" :key="alert.seq" class="bg-light" @click="clickBoard(alert.seq)">
        <td>{{ alert.seq }}</td>
        <td>{{ alert.categoryName }}</td>
        <td class="subject">{{ alert.subject }}</td>
        <td>{{ alert.readCount }}</td>
        <td>{{ dateTime(alert.date, 'YYYY-MM-DD') }}</td>
        <td>{{ alert.adminId }}</td>
      </tr>
      <tr v-for="board in boardList" :key="board.seq" @click="clickBoard(board.seq)">
        <td>{{ board.seq }}</td>
        <td>{{ board.categoryName }}</td>
        <td class="subject">{{ board.subject }}
          <span v-if="board.replyCount"> ({{ board.replyCount }})</span>
          <span v-if="board.hasFile"> 🗎 </span>
        </td>
        <td>{{ board.readCount }}</td>
        <td>{{ dateTime(board.date, 'YYYY-MM-DD') }}</td>
        <td v-if="board.adminId">{{ board.adminId }}</td>
        <td v-else-if="board.memberId">{{ board.memberId }}</td>
        <td v-else-if="board.memberId && board.adminId">{{ board.memberId }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {dateTime} from "../../util/ParamUtil";

export default {
  name: 'ListTable',
  props: {
    boardList: {},
    alertList: {}
  },
  emits: ['showDetail'],
  methods: {
    dateTime,
    clickBoard(seq) {
      this.$emit('showDetail', seq);
    }
  },
};
</script>

<style scoped>
th.subject {
  text-align: center;
  width: 700px;
}
</style>