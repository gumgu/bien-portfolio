<template>
  <div class="button-container">
    <div class="button-box">
      <button type="button" class="btn btn-primary" @click="register">등록</button>
    </div>
    <div class="button-box">
      <button type="button" class="btn btn-secondary" @click="cancel">취소</button>
    </div>
  </div>

</template>
<script>
import {dateTime} from "@/util/ParamUtil";

export default {
  name: 'WriteButtons',
  emits: ['register', 'cancel'],
  computed: {
    writer() {
      const id = this.$store.getters.id;
      return id === this.memberId;
    }
  },
  methods: {
    dateTime,
    register() {
      this.$emit('register')
    },
    cancel() {
      this.$emit('cancel')
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


