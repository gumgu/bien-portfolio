<template>
  <footer class="footer">
    <div class="container">
      <p class="hrefs">
        <a href="https://velog.io/@bienlee" target="_blank">Blog</a> |
        <a href="https://github.com/gumgu/Spring-Vue-Board" target="_blank">Github</a>
      </p>
      <p class="copyright">&#169; 2023 BoardSimple. All Rights Reserved.</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'FooterSection'
};
</script>

<style>
.footer {
  border-top: 1px solid gray;
  background-color: white;
  padding: 20px;
  text-align: center;
  margin-top: 50px;
}

.footer a {
  color: black;
  text-decoration: none;
}
</style>
