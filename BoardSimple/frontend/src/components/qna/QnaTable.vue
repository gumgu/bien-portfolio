<template>
  <div>
    <table class="table">
      <thead>
      <tr>
        <th>번호</th>
        <th class="subject">제목</th>
        <th>조회</th>
        <th>등록일시</th>
        <th>등록자</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="qna in qnaList" :key="qna.seq" @click="handleClick(qna)">
        <td>{{ qna.seq }}</td>
        <td class="subject"> {{ qna.questionSubject }}
          <span v-if="qna.hasAnswer">{{ "(답변완료)" }}</span>
          <span v-else>{{ "(미답변)" }}</span>
          <span v-if="qna.privacy">🔒︎</span>
        </td>
        <td>{{ qna.readCount }}</td>
        <td>{{ dateTime(qna.date, 'YYYY-MM-DD') }}</td>
        <td v-if="qna.memberId">{{ qna.memberId }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {dateTime} from "../../util/ParamUtil";

export default {
  name: 'QnaTable',
  props: {
    qnaList: {
      qna: {
        seq: Number,
        questionSubject: String,
        questionContent: String,
        answer: String,
        privacy: Boolean,
        readCount: Number,
        hasAnswer: Boolean,
        date: Date,
        updateDate: Date,
        memberId: String,
        adminId: String
      }
    },
  },
  emits: ['openModal', 'moveToDetail'],
  methods: {
    dateTime,
    handleClick(qna) {
      if (qna.privacy) {
        this.$emit('openModal', qna.seq, qna.memberId);
      } else {
        this.$emit('moveToDetail', qna.seq);
      }
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