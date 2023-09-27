<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12 text-start">
        <h1>자유게시판</h1>
      </div>
    </div>
    <div class="row mt-4">
      <div class="col-md-12">
        <form @submit.prevent="registerFreeBoard">
          <table class="table">
            <tr>
              <th><label for="category" class="form-label">분류</label></th>
              <td>
                <select id="category" class="form-select" v-model="freeBoardDTO.categoryId">
                  <option value="hobby">취미</option>
                  <option value="humor">유머</option>
                  <option value="touching">감동</option>
                </select>
                <p class="field-error" v-if="validationErrors.categoryId">
                  카테고리를 선택하세요
                </p>
              </td>
            </tr>
            <tr>
              <th><label for="title" class="form-label">제목</label></th>
              <td>
                <input type="text" id="title" class="form-control" placeholder="제목을 입력하세요"
                       v-model="freeBoardDTO.subject">
                <p class="field-error" v-if="validationErrors.subject">
                  제목을 입력하세요. 4 ~ 100자 사이로 입력 가능합니다.
                </p>
              </td>
            </tr>
            <tr>
              <th><label for="content" class="form-label">내용</label></th>
              <td><textarea id="content" class="form-control" rows="5" placeholder="내용을 입력하세요"
                            v-model="freeBoardDTO.content"></textarea>
                <p class="field-error" v-if="validationErrors.content">
                  내용을 입력하세요. 4 ~ 2000자 사이로 입력 가능합니다.
                </p>
              </td>
            </tr>
            <tr>
              <th><label for="attachment" class="form-label">첨부</label></th>
              <td>
                <p>
                  jpg, gif, png, zip 파일만 파일사이즈 2MB 까지 업로드 가능합니다.
                </p>

                <div v-if="freeBoardDTO.files" v-for="file in freeBoardDTO.files">
                  <span @click="downloadFile(file)">🗎 {{ file.originName }}</span>
                  <button type="button" class="btn btn-dark" @click="deleteFile(file.fileName)">삭제</button>
                </div>

                <div class="attachInput" v-for="uploadRow in uploadRows">
                  <input type="file" ref="attach" @change="handleFile($event, uploadRow)">
                  <button type="button" class="attachBtn btn btn-outline-dark" @click="removeRow(uploadRow)">x</button>
                </div>
                <button type="button" class="attachBtn btn btn-outline-dark" @click="addRow">Add uploadRow</button>

              </td>
            </tr>
          </table>
          <WriteButtons @register="registerFreeBoard"
                        @cancel="cancel"/>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import {getFreeBoardDetail, saveFreeBoard, updateFreeBoard} from "@/api/BoardAxios";
import WriteButtons from "@/components/board/WriteButtons.vue";
import {downloadFile} from "@/api/FileAxios";
import {required, sizeInclude} from "@/api/BoardValidateAxios";
import freeBoardDetail from "@/views/board/FreeBoardDetail.vue";

export default {
  name: 'FreeBoardWrite',
  components: {WriteButtons},
  data() {
    return {
      freeBoardDTO: {
        // board 변수
        seq: undefined,
        subject: undefined,
        content: undefined,
        categoryId: undefined,

        // freeBoardDTO 변수
        files: [],
        deleteFileNames: [],
        addFiles: []
      },

      // 첨부파일 html 추가를 위한 변수
      uploadRows: [
        {
          file: {}
        },
      ],

      // 각 필드의 검증 결과를 저장하는 객체
      validationErrors: {}
    }
  },
  /**
   * update를 위한 접근인 경우, seq의 게시글을 불러옵니다.
   * @param seq 불러올 게시글 번호
   * @returns {Promise<void>}
   */
  created() {
    if (this.$route.params.seq !== undefined) {
      this.freeBoardDTO.seq = this.$route.params.seq;
      getFreeBoardDetail(this.freeBoardDTO.seq)
          .then(({board}) => {
            console.log("this.freeBoardDTO:", this.freeBoardDTO);
            this.freeBoardDTO.subject = board.subject;
            this.freeBoardDTO.content = board.content;
            this.freeBoardDTO.categoryId = board.categoryId;
            this.freeBoardDTO.files = board.files;
          });
    }
  },
  methods: {
    downloadFile,
    /**
     * 새로운 첨부파일 업로드 행을 추가합니다.
     * - 5개까지 추가 가능
     */
    addRow() {
      if (this.uploadRows.length + this.freeBoardDTO.files.length < 5) {
        this.uploadRows.push({file: ""})
      } else {
        alert("5개까지 업로드 가능합니다.");
      }
    },

    /**
     * 선택된 첨부파일 업로드 행을 삭제합니다.
     * - 단, 남은 행이 1개가 아닐 때.
     * - input 요소의 파일 선택 내용을 초기화합니다.
     */
    removeRow(uploadRow) {
      const index = this.uploadRows.indexOf(uploadRow);

      // 최소 2개의 행이 남아있을 때,
      if (index !== -1 && this.uploadRows.length !== 1) {

        // input 요소에 있는 파일 선택 내용을 초기화합니다.
        const inputElement = this.$refs.attach[index];
        if (inputElement) {
          inputElement.value = '';
        }

        // input 요소를 제거합니다.
        this.uploadRows.splice(index, 1);
      }
    },

    /**
     * 업로드한 파일의 (타입, 크기)를 검증하고 업로드 배열에 추가합니다.
     * - 파일 타입: gif, png, jpeg, zip
     * - 파일 크기: 2MB
     * @param event
     */
    handleFile(event, uploadRow) {
      const selectedFile = event.target.files[0];
      if (selectedFile) {

        // 선택된 파일이 업로드 가능한 확장자인지 확인합니다.
        if (!['image/gif',
          'image/png',
          'image/jpeg',
          'application/x-zip-compressed'].includes(selectedFile.type)) {
          alert("jpg, gif, png, zip 파일만 업로드 가능합니다.");
          event.target.value = '';
        }

        // 선택된 파일이 업로드 가능한 사이즈인지 확인합니다.
        else if (selectedFile.size > 16777216) {
          alert("크기가 2MB보다 작은 파일만 업로드 가능합니다!");
          event.target.value = '';
        } else {
          // 검증 성공 시, 해당 행의 file(임시 저장 공간)에 파일을 주가합니다.
          uploadRow.file = selectedFile;
        }
      }
      console.log('uploadRow', uploadRow);
    },

    /**
     * 작성된 게시글을 검증합니다.
     */
    validateForm() {
      // 검증 결과 초기화
      this.validationErrors = {};

      if (required(this.freeBoardDTO.categoryId)) {
        this.validationErrors.categoryId = true;
      }

      if (required(this.freeBoardDTO.subject) || sizeInclude(4, 100, this.freeBoardDTO.subject)) {
        this.validationErrors.subject = true;
      }

      if (required(this.freeBoardDTO.content) || sizeInclude(4, 2000, this.freeBoardDTO.content)) {
        this.validationErrors.content = true;
      }

      console.log("this.validationErrors", this.validationErrors);
    },

    /**
     * 게시글 작성 & 수정
     * - uploadRow(행)에 임시저장된 파일을 addFile에 추가합니다.
     * - (seq가 undefined인 경우) 새로운 게시글을 작성합니다.
     * - (seq가 값을 가지는 경우) 게시글을 수정합니다.
     */
    async registerFreeBoard() {

      // 폼의 각 필드 검증 수행
      this.validateForm();
      console.log('Object.keys(this.validationErrors.length === 0)',
          Object.keys(this.validationErrors).length === 0);

      // 검증이 통과한 경우에만 게시글 작성 또는 수정 로직을 실행
      if (Object.keys(this.validationErrors).length === 0) {
        console.log("게시물을 등록합니다.");

        // uploadRows에 첨부된 파일을 addFiles에 저장합니다.
        for (let uploadRow of this.uploadRows) {
          if (uploadRow.file.size > 0) {
            this.freeBoardDTO.addFiles.push(uploadRow.file);
          }
        }

        // 새로운 게시글 등록
        if (this.freeBoardDTO.seq === undefined) {
          await saveFreeBoard(this.freeBoardDTO)
              .then((response) => {
                const seq = response.data.result.seq;
                this.$router.push({name: 'FreeBoardDetail', params: {seq: seq}});
              })
              .catch((err) => {
                console.log(err);
              });
        }

        // 게시글 수정
        else {
          await updateFreeBoard(this.freeBoardDTO)
              .then(() => {
                this.$router.push({name: 'FreeBoardDetail', params: {seq: this.freeBoardDTO.seq}})
              })
        }
      }

    },

    /**
     * 취소버튼: 이전 페이지로 돌아갑니다.
     */
    cancel() {
      this.$router.go(-1);
    },

    //============================== Update인 경우
    /**
     * 삭제 요청한 첨부파일명을 deleteFile 배열에 담습니다.
     */
    deleteFile(fileName) {

      // deleteFile 배열에 추가
      this.freeBoardDTO.deleteFileNames.push(fileName);

      // freeBoardDTO.file에서 요청 파일 제거
      for (let i = 0; i < this.freeBoardDTO.files.length; i++) {
        if (this.freeBoardDTO.files[i].fileName === fileName) {
          this.freeBoardDTO.files.splice(i, 1);
        }
      }
    }
  },


}
</script>

<style scoped>
/* 테이블 스타일 */
table {
  width: 100%;
  border-collapse: separate;
}

th {
  text-align: center;
}

tr {
  border: 1px solid #ccc;
}

p {
  margin: 0;
}

/* 버튼 스타일 */
button {
  width: 100px;
}

.attachBtn {
  width: auto;
}

/* 검증 스타일*/
.field-error {
  color: red;
}

</style>
