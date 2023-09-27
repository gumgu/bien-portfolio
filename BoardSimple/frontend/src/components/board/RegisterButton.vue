<template>
  <div class="register d-flex justify-content-end">
    <button class="btn btn-primary" @click="goToWrite">글 등록</button>
  </div>
</template>
<script>
export default {
  name: 'RegisterButton',
  emits: ['goToWrite'],
  methods: {
    goToWrite() {
      this.$emit('goToWrite');
    }
  }
}
</script>

<style scoped>
.register {
  margin-top: 30px;
  margin-bottom: 10px;
}
</style>