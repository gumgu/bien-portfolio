<template>

  <div class="detail-header">
    <h2 class="col-md-8">
      <span>{{ qna.questionSubject }}</span>
      <span v-if="qna.answer">{{ "(답변완료)" }}</span>
      <span v-else>{{ "(미답변)" }}</span>
    </h2>
    <div class="col-md-2">{{ dateTime(qna.date, 'YYYY-MM-DD HH:ss') }}</div>
    <div class="col-md-1">{{ qna.memberId }}</div>
  </div>

  <hr class="hr">

  <div class="mb-5">
    <h3 class="detail-view-count">조회수 {{ qna.readCount }}</h3>
  </div>

  <div class="detail-content">
    {{ qna.questionContent }}
  </div>

  <div class="detail-answer" v-if="qna.answer">
    {{ qna.answer }}
  </div>

</template>

<script>
import {dateTime} from "../../util/ParamUtil";

export default {
  name: 'QnaBody',
  props: {
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
  created() {
    console.log(this.qna)
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

.detail-answer {
  background-color: #cfe2ff;
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}

</style>
