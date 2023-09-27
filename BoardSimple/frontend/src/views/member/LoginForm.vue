<template>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">


  </head>

  <body class="text-center">
  <div id="container">

    <div id="loginBox">

      <div id="loginBoxTitle text-center">로그인</div>

      <div id="inputBox">
        <div class="input-form-box">
          <input type="text" name="id" class="form-control" placeholder="아이디" v-model="memberDTO.id">
        </div>

        <div class="input-form-box">
          <input type="passwrd" name="password" class="form-control" placeholder="비밀번호" v-model="memberDTO.password">
        </div>

        <div class="button-login-box">
          <button type="button" id="inForm" class="btn btn-primary btn-xs" @click="login">로그인</button>
          <button type="button" id="inForm" class="btn btn-primary btn-xs" @click="pushSignup">회원가입</button>
        </div>
      </div>
    </div>
  </div>

  <error-alert v-if="showModal" class="error-alert">
    <div>
      <h2 class="error-alert-element">로그인 실패</h2>
      <p class="error-alert-element">{{ errorMessage }}</p>
      <button @click="confirmError" class="error-alert-element">확인</button>
    </div>
  </error-alert>

  </body>
</template>

<script>
import ErrorAlert from "@/components/global/ErrorAlert.vue";

export default {
  name: "LoginForm",
  components: {ErrorAlert},
  data() {
    return {
      memberDTO: {
        id: '',
        password: ''
      },
      showModal: false,
      errorMessage: ''
    }
  },
  methods: {
    // 회원가입 폼으로 이동합니다.
    pushSignup() {
      this.$router.push({name: 'SignUpForm'})
    },

    // 로그인 작업을 수행합니다.
    login() {
      this.$store.dispatch('login', {value: this.memberDTO})
          .then((result) => {
            // 로그인 성공 시 알람을 띄우고, 메인 페이지로 이동합니다.
            if (result === 'success') {
              alert('로그인 성공');
              this.$router.replace({ name: 'MainBoard'})
            }
          })
          .catch((errorDTO) => {
            // 로그인 실패시 오류 문자를 출력합니다.
            console.log(errorDTO);
            this.showModal = true;
            this.errorMessage = errorDTO.message;
          })
    },

    // 모달창을 닫습니다.
    confirmError() {
      this.showModal = false;
    },
  }
}
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

html, body {
  height: 100%;
  background: #ffffff;
}

#inForm {
  width: 100%;
}

#container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 100%;
}

#loginBox {
  width: 300px;
  text-align: center;
  background-color: #ffffff;
}

.input-form-box {
  border: 0px solid #ff0000;
  display: flex;
  margin-bottom: 5px;
}

.input-form-box > span {
  display: block;
  text-align: left;
  padding-top: 5px;
  min-width: 65px;
}

.button-login-box {
  margin: 10px 0;
}

.error-alert {
  margin: 10px;
  padding: 10px;
}

.error-alert-element {
  margin-bottom: 5px;
}

#loginBoxTitle {
  color: #000000;
  font-weight: bold;
  font-size: 32px;
  text-transform: uppercase;
  padding: 5px;
  margin-bottom: 20px;
  background: linear-gradient(to right, #270a09, #8ca6ce);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

#inputBox {
  margin: 10px;
}

#inputBox button {
  padding: 3px 5px;
  margin-bottom: 5px;
}
</style>