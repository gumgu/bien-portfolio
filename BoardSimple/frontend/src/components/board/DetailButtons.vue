<template>
  <div class="button-container">
    <div class="button-box">
      <button class="btn btn-primary" @click="moveToList">목록</button>
    </div>
    <div class="button-box" v-if="writer">
      <button class="btn btn-outline-dark" @click="modifyPost">수정</button>
    </div>
    <div class="button-box" v-if="writer">
      <button class="btn btn-outline-dark" @click="deletePost">삭제</button>
    </div>
  </div>

</template>
<script>
import {dateTime} from "@/util/ParamUtil";

export default {
  name: 'DetailButtons',
  emits: ['moveToList', 'modifyPost', 'deletePost'],
  props: {
    memberId: {
      type: String,
      required: true
    }
  },
  computed: {
    writer() {
      const id = this.$store.getters.id;
      return id === this.memberId;
    }
  },
  methods: {
    dateTime,
    moveToList() {
      this.$emit('moveToList');
    },
    modifyPost() {
      this.$emit('modifyPost');
    },
    deletePost() {
      this.$emit('deletePost');
    },
  },
}
</script>
<style scoped>

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-box {
  padding: 10px;
}

.btn {
  width: 100px;
}
</style>