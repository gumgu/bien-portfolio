<template>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

  </head>

  <body class="text-center">
  <div id="container">

    <div id="loginBox">

      <div id="loginBoxTitle text-center">회원가입</div>

      <div id="inputBox">

        <form @submit.prevent="submitForm">

          <div class="input-form-box">
            <input type="text" name="id" class="form-control" placeholder="아이디" v-model="memberDTO.id">
            <button typ="button" class="btn btn-primary" id="checkButton" @click="checkDupli(memberDTO.id)">중복확인
            </button>
          </div>

          <div class="input-form-box">
            <input type="text" name="name" class="form-control" placeholder="이름" v-model="memberDTO.name">
          </div>

          <div class="input-form-box">
            <input type="passwrd" name="password" class="form-control" placeholder="비밀번호" v-model="memberDTO.password">
          </div>

          <div class="input-form-box">
            <input type="passwrd" name="password" class="form-control" placeholder="비밀번호 확인"
                   v-model.trim="confirmPassword">
          </div>

          <div class="button-login-box">
            <button type="button" class="btn btn-primary btn-xs" id="signUpButton" @click="submitForm">회원가입 하기</button>
          </div>
        </form>

        <error-alert v-if="showModal">
          <div v-if="!idIsUnique" class="error-alert">
            <h2>아이디 중복</h2>
            <p>다른 아이디를 입력해주세요</p>
            <button @click="confirmError">확인</button>
          </div>
          <div v-if="idIsUnique" class="error-alert">
            <p>해당 아이디는 사용 가능합니다.</p>
            <button @click="confirmError">확인</button>
          </div>
        </error-alert>

      </div>

    </div>

  </div>

  </body>
</template>

<script>
import ErrorAlert from "@/components/global/ErrorAlert.vue";
import {checkId, signUp} from "@/api/MemberAxios";

export default {
  name: "SignUpForm",
  components: {ErrorAlert},
  data() {
    return {
      memberDTO: {
        id: '',
        name: '',
        password: '',
      },
      confirmPassword: '',
      idIsUnique: false,
      formIsValid: true,
      showModal: false
    }
  },
  methods: {
    // 아이디의 중복 여부를 확인합니다.
    checkDupli(id) {
      checkId(id)
          .then(({result}) => {
            this.showModal = true;
            this.idIsUnique = result;
          })
    },

    // 작성된 양식의 유효성을 검증하고 회원가입을 진행합니다.
    submitForm() {
      this.formIsValid = true;
      if (this.id === '' || this.password === '' || this.confirmPassword === '') {
        this.formIsValid = false;
        return;
      }
      signUp(this.memberDTO)
          .then(() => {
            alert("회원가입에 성공했습니다!");
            // 회원가입 성공 시 로그인 화면으로 이동합니다.
            this.$router.push({name: 'LoginForm'});
          })
          .catch(() => {
            alert("회원가입에 실패했습니다!");
          })
    },

    // 모달창을 닫습니다.
    confirmError() {
      this.showModal = false;
    },

  }
}
</script>

<style>
* {
  padding: 0;
  margin: 0;
}

html, body {
  height: 100%;
  background: #ffffff;
}

#checkButton {
  width: 40%;
  margin-left: 5px;
}

#signUpButton {
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