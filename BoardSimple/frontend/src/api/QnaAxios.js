import axios from "./axios";
import router from "@/router";

const serverUrl = 'http://localhost:8080/'

/**
 * 문의글 리스트를 호출합니다
 *
 * @param boardSearchCondition 검색조건
 * @returns 호출 성공 시, return (문의글 리스트)
 * @returns 호출 실패 시, return (errorDTO)
 */
export const getQnaList = (boardSearchCondition) => {
    return axios
        .get(serverUrl + 'api/qnas', {
            params: boardSearchCondition
        })
        .then((response) => {
            // 서버로부터 받아온 응답 데이터의 구조를 확인하여 처리합니다.
            const qnaList = response.data.result.qnaList
            const totalListCount = response.data.result.totalListCount

            console.log("response: ", response)

            return {
                qnaList: qnaList,
                totalListCount: totalListCount
            };
        }).catch((error) => {
            const errorDTO = error.response.data.result.errorDTO;
            console.log(errorDTO)

            router.push({
                name: 'ErrorPage',
                query: errorDTO
            });
        })
}


/**
 * 메인 페이지에 출력될 문의글 리스트를 조회합니다.
 *
 * @param count 조회할 문의글 리스트 크기
 * @returns 호출 성공 시, return (문의글)
 * @returns 호출 실패 시, return (errorDTO)
 */
export const getMainQnaList = (count) => {
    return axios
        .get(serverUrl + 'api/qnas/' + count)
        .then((response) => {
            const qnaList = response.data.result.qnaList
            console.log("response: ", response)

            return {
                qnaList: qnaList
            };
        }).catch((error) => {
            const errorDTO = error.response;
            console.log(errorDTO)

            // router.push({
            //     name: 'ErrorPage',
            //     query: errorDTO
            // });
        })
}


/**
 * 문의글 상세 정보를 호출합니다
 *
 * @returns 호출 성공 시, return (qna)
 * @returns 호출 실패 시, return (errorDTO)
 */
export const getQnaDetail = (seq) => {
    return axios({
        method: "get",
        url: serverUrl + "api/qna/" + seq,
    }).then((response) => {
        const qna = response.data.result.qna;
        console.log("qna:", qna);
        return qna;
    }).catch((error) => {
        const errorDTO = error;
        console.log(errorDTO)

        router.push({
            name: 'ErrorPage',
            query: errorDTO
        });
    })
}

/**
 * 새로운 문의글을 작성합니다.
 *
 * @param qna 작성할 문의글
 * @returns seq 작성된 문의글의 id
 */
export const saveQna = (qna) => {
    console.log("axios save용 qna", qna);
    return axios({
        method: "post",
        url: serverUrl + "api/qna",
        data: qna
    }).then((seq) => {
        return seq;
    }).catch((error) => {
        const errorDTO = error;
        console.log(errorDTO)

        router.push({
            name: 'ErrorPage',
            query: errorDTO
        })
    })
}

/**
 * 문의글을 수정합니다.
 *
 * @param qna 수정할 문의글
 */
export const updateQna = (qna) => {
    console.log("axios save용 qna", qna);
    return axios({
        method: "post",
        url: serverUrl + "api/qna/" + qna.seq,
        data: qna
    }).then((seq) => {
        return seq;
    }).catch((error) => {
        const errorDTO = error;
        console.log(errorDTO)

        router.push({
            name: 'ErrorPage',
            query: errorDTO
        })
    })
}