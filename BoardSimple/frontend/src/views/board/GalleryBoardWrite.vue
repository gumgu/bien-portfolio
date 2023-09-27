<template>
  <head>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  </head>
  <div class="container mt-4">
    <div class="uploadRow">
      <div class="col-md-12 text-start">
        <h1>갤러리</h1>
      </div>
    </div>
    <div class="row mt-4">
      <div class="col-md-12">
        <form @submit.prevent="registerGalleryBoard">
          <table class="table">
            <tr>
              <th><label for="category">분류</label></th>
              <td>
                <select id="category" class="form-select" v-model="galleryBoardDTO.categoryId">
                  <option value="celebrity">연예인</option>
                  <option value="memorial">기념일</option>
                  <option value="place">장소</option>
                </select>
                <p class="field-error" v-if="validationErrors.categoryId">
                  카테고리를 선택하세요
                </p>
              </td>
            </tr>
            <tr>
              <th><label for="title">제목</label></th>
              <td><input type="text" id="title" class="form-control" placeholder="제목을 입력하세요"
                         v-model="galleryBoardDTO.subject">
                <p class="field-error" v-if="validationErrors.subject">
                  제목을 입력하세요. 4 ~ 100자 사이로 입력 가능합니다.
                </p>
              </td>
            </tr>
            <tr>
              <th><label for="content">내용</label></th>
              <td>
                <textarea id="content" class="form-control" rows="5" placeholder="내용을 입력하세요"
                          v-model="galleryBoardDTO.content"></textarea>
                <p class="field-error" v-if="validationErrors.content">
                  내용을 입력하세요. 4 ~ 2000자 사이로 입력 가능합니다.
                </p>
              </td>
            </tr>
            <tr>
              <th><label for="attachment" class="form-label">갤러리 이미지</label></th>
              <td>
                <p>
                  jpg, gif, png, zip 파일만 파일사이즈 2MB 까지 업로드 가능합니다.
                </p>
                <p>
                  1번째 이미지는 썸네일로 활용 됩니다.
                </p>

                <div v-if="galleryBoardDTO.files" v-for="file in galleryBoardDTO.files">
                  <div class="upload-thumb-wrap">
                    <!--                    <img :src=getImageUrl(file)>-->
                  </div>
                  <span @click="downloadFile(file)">🗎 {{ file.originName }}</span>
                  <button type="button" class="btn btn-dark" @click="deleteFile(file.fileName)">삭제</button>
                </div>

                <div class="attachInput" v-for="uploadRow in uploadRows">
                  <div class="upload-display">
                    <div class="upload-thumb-wrap" v-if="uploadRow.imageUrl">
                      <img :src=uploadRow.imageUrl>
                    </div>
                    <input type="file" class="fileInput" ref="attach" @change="handleFile($event, uploadRow)">
                    <button type="button" class="attachBtn btn btn-outline-dark" @click="removeRow(uploadRow)">x
                    </button>
                  </div>
                </div>
                <button type="button" class="attachBtn btn btn-outline-dark" @click="addRow">Add uploadRow</button>
              </td>
            </tr>
          </table>
          <WriteButtons @register="registerGalleryBoard"
                        @cancel="cancel"/>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import {getGalleryBoardDetail, saveGalleryBoard, updateGalleryBoard} from "@/api/BoardAxios";
import WriteButtons from "@/components/board/WriteButtons.vue";
import {downloadFile} from "@/api/FileAxios";
import {required, sizeInclude} from "@/api/BoardValidateAxios";

export default {
  name: 'GalleryBoardWrite',
  components: {WriteButtons},
  data() {
    return {
      galleryBoardDTO: {
        // board 변수
        seq: undefined,
        subject: undefined,
        content: undefined,
        categoryId: undefined,

        // galleryBoardDTO 변수
        files: [],
        deleteFileNames: [],
        addFiles: []
      },

      // 첨부파일 html 추가를 위한 변수
      uploadRows: [
        {
          file: {}, // 첨부파일
          imageUrl: undefined // preview image url
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
      this.galleryBoardDTO.seq = this.$route.params.seq;
      getGalleryBoardDetail(this.galleryBoardDTO.seq)
          .then(({board}) => {
            console.log("this.galleryBoardDTO:", this.galleryBoardDTO);
            this.galleryBoardDTO.subject = board.subject;
            this.galleryBoardDTO.content = board.content;
            this.galleryBoardDTO.categoryId = board.categoryId;
            this.galleryBoardDTO.files = board.files;
          });
    }
  },
  methods: {
    downloadFile,
    /**
     * 새로운 첨부파일 업로드 행을 추가합니다.
     * - 20개까지 추가 가능
     */
    addRow() {
      if (this.uploadRows.length + this.galleryBoardDTO.files.length < 20) {
        this.uploadRows.push({file: ""})
      } else {
        alert("20개까지 업로드 가능합니다.");
      }
    },

    /**
     * 선택된 첨부파일 업로드 행을 삭제합니다.
     * - 단, 남은 행이 1개가 아닐 때.
     * - input 요소의 파일 선택 내용을 초기화합니다.
     */
    removeRow(uploadRow) {
      const index = this.uploadRows.indexOf(uploadRow);

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
        if (!['image/gif', 'image/png', 'image/jpeg'].includes(selectedFile.type)) {
          alert("jpg, gif, png, zip 파일만 업로드 가능합니다.");
          event.target.value = '';
        }

        // 선택된 파일이 업로드 가능한 사이즈인지 확인합니다.
        else if (selectedFile.size > 8388608) {
          alert("크기가 1MB보다 작은 파일만 업로드 가능합니다!");
          event.target.value = '';
        } else {
          // 검증 성공 시, 해당 행의 file(임시 저장 공간)에 파일을 추가합니다.
          uploadRow.file = selectedFile;

          // preview image를 제공합니다.
          const url = URL.createObjectURL(selectedFile);
          uploadRow.imageUrl = url;

        }
        console.log("uploadRows:", this.uploadRows);
      }
    },

    /**
     * 작성된 게시글을 검증합니다.
     */
    validateForm() {
      // 검증 결과 초기화
      this.validationErrors = {};

      if (required(this.galleryBoardDTO.categoryId)) {
        this.validationErrors.categoryId = true;
      }

      if (required(this.galleryBoardDTO.subject) || sizeInclude(4, 100, this.galleryBoardDTO.subject)) {
        this.validationErrors.subject = true;
      }

      if (required(this.galleryBoardDTO.content) || sizeInclude(4, 2000, this.galleryBoardDTO.content)) {
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
    async registerGalleryBoard() {

      // 폼의 각 필드 검증 수행
      this.validateForm();
      console.log('Object.keys(this.validationErrors.length === 0)',
          Object.keys(this.validationErrors).length === 0);

      // uploadRows에 첨부된 파일을 addFiles에 저장합니다.
      for (let uploadRow of this.uploadRows) {
        if (uploadRow.file.size > 0) {
          this.galleryBoardDTO.addFiles.push(uploadRow.file);
        }
      }

      // 새로운 게시글 등록
      if (this.galleryBoardDTO.seq === undefined) {
        saveGalleryBoard(this.galleryBoardDTO)
            .then((response) => {
              const seq = response.data.result.seq;
              this.$router.push({name: 'GalleryBoardDetail', params: {seq: seq}});
            })
            .catch((err) => {
              console.log(err);
            });
      }

      // 게시글 수정
      else {
        updateGalleryBoard(this.galleryBoardDTO)
            .then(() => {
              this.$router.push({name: 'GalleryBoardDetail', params: {seq: this.galleryBoardDTO.seq}});
            })

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
      this.galleryBoardDTO.deleteFileNames.push(fileName);

      // galleryBoardDTO.file에서 요청 파일 제거
      for (let i = 0; i < this.galleryBoardDTO.files.length; i++) {
        if (this.galleryBoardDTO.files[i].fileName === fileName) {
          this.galleryBoardDTO.files.splice(i, 1);
        }
      }
    }
  }
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

/* 텍스트박스 스타일 */
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

/* =========================  imaged preview  관련 css ========================= */
.attachInput .upload-display { /* 이미지가 표시될 지역 */
  margin-bottom: 5px;
  display: inline-block;
}

.attachInput .upload-thumb-wrap { /* 추가될 이미지를 감싸는 요소 */
  width: 54px;
  padding: 2px;
  vertical-align: middle;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #fff;
  display: inline-block;
}

.attachInput .upload-display img { /* 추가될 이미지 */
  display: block;
  max-width: 100%;
  width: 100% \9;
  height: auto;
}

.fileInput {
  margin: 10px;
}

/* 검증 스타일*/
.field-error {
  color: red;
}
</style>
