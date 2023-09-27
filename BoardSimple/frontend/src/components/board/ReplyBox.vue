<template>
  <div class="reply-box">

    <div class="row">
      <input type="text" class="col-md-10" placeholder="댓글을 입력해주세요" ref="replyContent">
      <div class="button-box col-md-2">
        <input type="button" class="btn btn-light" value="등록" @click="registerReply">
      </div>
    </div>

    <div class="reply-content row" v-for="reply in replyList" :key="reply.seq">
      <hr class="reply-hr">
      <div class="col-md-10">
        <div class="reply-label">
          <span v-if="reply.memberId">{{ reply.memberId }}</span>
          <span v-if="reply.adminId">{{ reply.adminId }}</span>
          <span> {{ dateTime(reply.date, 'YYYY-MM-DD HH:ss') }} </span>
        </div>
        <div class="reply-content">
          <span>{{ reply.content }}</span>
        </div>
      </div>

      <div class="delete-key col-md-2"
           v-if="reply.memberId && checkDeletePermission(reply.memberId) ||
                 reply.adminId && checkDeletePermission(reply.adminId)">
        <button class="btn btn-outline-dark"
                @click="removeReply(reply.seq)">삭제
        </button>
      </div>
    </div>

  </div>
</template>
<script>
import {dateTime} from "../../util/ParamUtil";

export default {
  name: 'ReplyBox',
  emits: ['removeReply', 'registerReply'],
  methods: {
    dateTime,
    /**
     * localStorage의 id와 댓글 작성자를 비교하여, 삭제 가능 여부를 확인합니다.
     * @param id 댓글 작성자
     * @returns {boolean} 댓글 삭제 가능 여부
     */
    checkDeletePermission(id) {
      const storedId = this.$store.getters.id;
      return storedId === id;
    },
    registerReply() {
      console.log(this.$refs.replyContent.value);
      this.$emit('registerReply', this.$refs.replyContent.value);
      this.$refs.replyContent.value = '';
    },
    removeReply(seq) {
      console.log("seq", seq);
      this.$emit('removeReply', seq);
    }
  },
  props: {
    replyList: {
      type: Object
    }
  }
}
</script>

<style scoped>

.reply-box {
  border: 1px solid #ccc;
  margin-top: 30px;
  margin-bottom: 30px;
  padding: 30px;
  background-color: aliceblue;
}

.reply-hr {
  border: 1px dotted #ccc;
  margin-top: 16px;
  margin-bottom: 16px;
}

.btn {
  border: 1px solid #ccc;
  width: 100px;
}
</style>