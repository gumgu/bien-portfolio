<template>
  <div class="error-page">
    <div class="error-content">
      <h1>오류 페이지</h1>
      <p>{{ errorMessage }}</p>
      <router-link to="/">메인 페이지로</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ErrorPage',
  data() {
    return {
      errorMessage: this.$route.params.errorMessage || "잘못된 접근입니다."
    };
  },
  created() {
    console.log('errorMessage', this.$route.params.errorMessage)
  },
};
</script>

<style scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 550px;
  //background-color: #f0f0f0;
}

.error-content {
  text-align: center;
  width: 400px;
  padding: 20px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
}

h1 {
  font-size: 36px;
  color: #e74c3c;
}

p {
  font-size: 18px;
  margin-top: 10px;
  color: #333;
}

router-link {
  display: block;
  font-size: 18px;
  margin-top: 20px;
  text-decoration: none;
  color: #3498db; /* Choose a color for the link */
}
</style>
