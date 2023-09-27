<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>문의 게시판</h1>
      </div>
    </div>

    <div>
      <SearchBar :totalListCount="this.boardSearchCondition.totalListCount"
                 :isQna='true'
                 @search="loadBoardList"/>
      <RegisterButton @goToWrite="moveToWrite"
                      v-if="isAuthenticated"/>
      <QnaTable :qnaList="qnaList"
                @openModal="openModal"
                @moveToDetail="moveToDetail"/>
      <BoardPagination :totalListCount="this.boardSearchCondition.totalListCount"
                       :pageSize="this.boardSearchCondition.pageSize"
                       @pageChange="changePage"/>

      <error-alert v-if="this.showModal">
        <div>
          <div class="input-form-box">
            <input type="passwrd" name="password" class="modalPassword form-control" placeholder="비밀번호를 입력하세요"
                   v-model="memberDTO.password">
          </div>
          <div class="show-result" v-if="passwordMatch !== undefined">
            <p class="result-message" v-if="!this.passwordMatch">비밀번호가 일치하지 않습니다.</p>
          </div>
          <div class="button-box">
            <button type="button" class="modalBtn btn btn-primary btn-xs" @click="validatePassword">확인</button>
            <button type="button" class="modalBtn btn btn-primary btn-xs" @click="closeModal">목록으로</button>
          </div>
        </div>
      </error-alert>
    </div>
  </div>
</template>

<script>
import SearchBar from "@/components/board/SearchBar.vue";
import RegisterButton from "@/components/board/RegisterButton.vue";
import {getQnaList} from "@/api/QnaAxios";
import QnaTable from "@/components/qna/QnaTable.vue";
import ErrorAlert from "@/components/global/ErrorAlert.vue";
import {checkPassword} from "@/api/MemberAxios";
import BoardPagination from "@/components/board/BoardPagination.vue";

export default {
  components: {
    BoardPagination,
    ErrorAlert,
    SearchBar,
    QnaTable,
    RegisterButton,
  },
  data() {
    return {
      totalListCount: undefined, // 서버에서 받아오는 총 게시물 수
      qnaList: [], // 문의글 리스트

      // 검색 조건
      boardSearchCondition: {
        // 검색, 정렬 변수
        type: undefined,
        formData: undefined,
        toDate: undefined,
        search: undefined,
        orderStandard: 'date',
        orderSequence: 0,
        // qna 변수
        myQnaOnly: undefined,

        // 페이징 변수
        currentPage: 1,
        pageSize: 30,
        totalListCount: undefined,
      },

      // 비밀번호 확인용 변수
      memberDTO: {
        id: undefined, // 작성자 아이디
        password: undefined // 입력받은 비밀번호
      },
      selectedSeq: undefined, // 선택된 게시글 식별자
      showModal: false, // 비밀번호 확인 모달창 변수
      passwordMatch: undefined // 비밀번호 일치여부 확인용 변수
    }
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    }
  },
  created() {
    this.loadBoardList(this.boardSearchCondition);
  },
  methods: {
    /**
     * 검색 조건에 맞춰 문의글 리스트를 조회합니다.
     * @param boardSearchCondition 검색 조건
     */
    loadBoardList(boardSearchCondition) {
      this.boardSearchCondition = boardSearchCondition

      // 검색 요청 및 결과를 업데이트 합니다.
      getQnaList(this.boardSearchCondition)
          .then(({qnaList, totalListCount}) => {
                this.qnaList = qnaList;
                this.boardSearchCondition.totalListCount = totalListCount;
                console.log(this.qnaList)
              }
          )
    },
    /**
     * 페이지 변화를 적용합니다.
     * @param newPage
     */
    changePage(newPage) {
      // 페이지 변화를 적용합니다.
      this.boardSearchCondition.currentPage = newPage;
      this.loadBoardList(this.boardSearchCondition)
    },

    validatePassword() {
      checkPassword(this.memberDTO)
          .then((result) => {
            console.log("result:", result.result);
            if (result.result) {
              this.moveToDetail(this.selectedSeq);
            } else {
              // 일치하지 않는 경우, 오류 메시지를 띄웁니다.
              this.passwordMatch = false;
            }
          })
          .catch((reason) => {
            console.log("reason:", reason);
          });
    },
    /**
     * 아이디와 비밀번호가 일치하는 경우, 상세페이지로 이동합니다.
     * @param seq 게시글 번호
     */
    moveToDetail(seq) {
      this.$router.push({
        name: 'QnaDetail',
        params: {seq: seq},
      })
    },
    /**
     * 새로운 문의글 작성 페이지로 이동합니다.
     */
    moveToWrite() {
      this.$router.push({name: 'QnaWrite'})
    },
    /**
      비밀글을 선택한 경우, 모달 창을 띄웁니다.
     * @param seq 선택한 비밀글 번호
     * @param id 비밀글 작성자
     */
    openModal(seq, id) {
      this.memberDTO.id = id;
      this.selectedSeq = seq;

      this.showModal = true;
    },
    /**
     * 모달창을 내립니다.
     */
    closeModal() {
      this.showModal = false;
    },
  }
};
</script>

<style scoped>
.modalBtn {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
}

.modalPassword {
  width: 500px;
  margin-bottom: 10px;
}

.result-message {
  padding: 15px;
  color: red;
}

</style>
