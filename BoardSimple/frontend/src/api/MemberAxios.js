import axios from "axios";
import router from "@/router";
import store from "@/store";

const serverUrl = 'http://localhost:8080/'

/**
 * 회원 가입 요청을 서버로 전송합니다.
 * @param memberDTO - 회원 정보 객체
 * @returns void. 에러 시 에러페이지로 이동합니다.
 */
export const signUp = (memberDTO) => {
    return axios({
        method: 'post',
        url: serverUrl + 'api/member/signup',
        data: memberDTO
    }).catch((error) => {
        const errorDTO = error.response.data.result.errorDTO;
        console.log("errorDTO" + errorDTO)

        router.push({
            name: 'ErrorPage',
            query: errorDTO
        });
    })

}

/**
 * 아이디 중복 여부를 확인합니다.
 * @param id - 확인할 아이디
 * @returns 중복 결과(boolean). 에러 시 에러페이지로 이동합니다.
 */
export const checkId = (id) => {
    return axios({
        method: "get",
        url: serverUrl + "api/member/checkId/" + id
    }).then((response) => {
        console.log(response)
        const result = response.data.result.isUnique

        return {
            result: result
        }
    }).catch((error) => {
        console.log(error)
    })
}

/**
 * 전송한 회원 정보의 id & password 일치 여부를 확인합니다.
 * @param memberDTO 회원 정보
 * @returns boolean 일치 여부(result)
 */
export const checkPassword = (memberDTO) => {
    console.log("memberDTO", memberDTO)

    return axios.post(serverUrl + "api/member/checkPassword",
        {
            id: memberDTO.id,
            password: memberDTO.password
        },
        {
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then((response) => {
            const result = response.data.result.result;
            return {
                result: result
            }
        }).catch((error) => {
            return Promise.reject();
        })
}
