<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>문의 게시판</h1>
      </div>
    </div>
    <div class="row mt-4">
      <div class="col-md-12">
        <form @submit.prevent="registerQna">
          <table>
            <tr>
              <th><label for="title" class="form-label">제목</label></th>
              <td><input type="text" id="title" class="form-control" placeholder="제목을 입력하세요"
                         v-model="qna.questionSubject">
              </td>
            </tr>
            <tr>
              <th><label for="content" class="form-label">내용</label></th>
              <td><textarea id="content" class="form-control" rows="5" placeholder="내용을 입력하세요"
                            v-model="qna.questionContent"></textarea></td>
            </tr>
            <tr>
              <th><label for="privacy" class="form-label">비밀글</label></th>
              <td><input type="checkbox" class="checkbox" id="privacy" v-model="qna.privacy">
              </td>
            </tr>
          </table>
          <WriteButtons @register="registerQna"
                        @cancel="cancel"/>
        </form>
      </div>
    </div>
  </div>

</template>

<script>
import {getQnaDetail, saveQna, updateQna} from "@/api/QnaAxios";
import WriteButtons from "@/components/board/WriteButtons.vue";

export default {
  name: 'QnaWrite',
  components: {WriteButtons},
  data() {
    return {
      qna: {
        seq: undefined,
        memberId: this.$store.getters.id,
        questionSubject: undefined,
        questionContent: undefined,
        privacy: undefined
      },
    }
  },
  /**
   * update를 위한 접근인 경우, seq의 문의글을 불러옵니다.
   * @param seq 불러올 문의글 번호
   * @returns {Promise<void>}
   */
  async created() {
    if (this.$route.params.seq !== undefined) {
      this.qna.seq = this.$route.params.seq;
      this.qna = await getQnaDetail(this.qna.seq);
    }
  },
  methods: {
    /**
     * 문의글 작성 & 수정
     * - (seq가 undefined인 경우) 새로운 문의글을 작성합니다.
     * - (seq가 값을 가지는 경우) 문의글을 수정합니다.
     */
    registerQna() {

      // 새로운 게시글 등록
      if (this.qna.seq === undefined) {
        saveQna(this.qna)
            .then((seq) => {
              console.log('seq', seq);
              this.$router.push({name: 'QnaDetail', params: {seq: seq}})
            })
      }

      // 게시글 수정
      else {
        console.log("수정ㅇ할 qna", this.qna);
        updateQna(this.qna)
            .then(() => {
              this.$router.push({name: 'QnaDetail', params: {seq: this.qna.seq}});
            })
      }

    },
    /**
     * 취소버튼: 이전 페이지로 돌아갑니다.
     */
    cancel() {
      this.$router.go(-1);
    },
  }
}
</script>

<style scoped>
button {
  width: 100px;
}

.button-spacing {
  margin-right: 10px;
}

input[type=checkbox] {
  width: 25px;
  height: 25px;
}

table {
  width: 100%;
  border-collapse: separate;
}

th {
  background-color: aliceblue;
  text-align: center;
}
</style>
