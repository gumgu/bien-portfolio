<template>
  <header class="bg-white p-3">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-md-12 text-center mb-3">
          <a href="/"><h1 class="h1">Board Simple</h1></a>
        </div>
        <div class="col-md-6">
          <nav class="nav">
            <a class="nav-link" href="/board/notices">공지사항</a>
            <a class="nav-link" href="/board/frees">자유 게시판</a>
            <a class="nav-link" href="/board/galleries">갤러리</a>
            <a class="nav-link" href="/qnas">문의 게시판</a>
            <!-- Add more menu items here -->
          </nav>
        </div>
        <div class="col-md-6 text-md-end d-flex justify-content-end">
          <nav class="nav text-end" v-if="!isLoggedIn">
            <a class="nav-link" href="/member/login">로그인</a>
            <a class="nav-link" href="/member/signup">회원가입</a>
          </nav>
          <nav class="nav text-end" v-if="isLoggedIn">
            <a class="nav-link">{{ id }}님 안녕하세요!</a>
            <a class="nav-link" @click="logout">로그아웃</a>
          </nav>
        </div>
      </div>
    </div>
  </header>
  <hr class="my-4">
  <!-- Rest of your content goes here -->
</template>

<script>
export default {
  name: 'HeaderSection',
  computed: {
    isLoggedIn() {
      return this.$store.getters.id != null;
    },
    id() {
      return this.$store.getters.id;
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout');
    }
  }
}
</script>

<style scoped>
a {
  color: black;
  text-decoration: none;
}
</style>