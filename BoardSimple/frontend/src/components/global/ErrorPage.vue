<template>
  <div v-if="errorDTO" class="error-alert">
    <h1>{{ errorDTO.message }}</h1>
  </div>
  <router-link to="/boards">게시판 목록</router-link>
</template>

<script>

export default {
  name: 'ErrorPage',
  data() {
    return {
      errorDTO: {}
    }
  },
  created() {
    this.errorDTO = this.$route.query
    console.log(this.errorDTO)
  }
}
</script>

<style>

</style>
