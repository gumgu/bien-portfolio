import axios from 'axios';
import store from "@/store";
import router from "@/router";
// axios 공통 처리를 위한 interceptors 파일입니다.

const serverUrl = 'http://localhost:8080/';

// 기존 요청의 설정을 임시저장하기 위한 변수
let originalRequestConfig = null;

/**
 * Request Interceptor: 요청 전 실행되는 요청 인터셉터
 * - 모든 요청에 Authorization 헤더로 Access token을 전송합니다.
 */
axios.interceptors.request.use(
    async (config) => {
        if (config.headers['UseRefreshToken']) {
            const refreshToken = store.getters.refreshToken;
            config.headers.Authorization = "Bearer " + refreshToken;
        } else {
            const accessToken = store.getters.accessToken;
            config.headers.Authorization = "Bearer " + accessToken;

            originalRequestConfig = config;
            console.log('accessToken으로 한 요청', originalRequestConfig);

        }

        return config;
    },
    (error) => {
        console.log(('interceptor request error:', error));
        return Promise.reject(error);
    });

/**
 * Response Interceptor: 요청 후 실행되는 응답 인터셉터
 */
axios.interceptors.response.use(
    (response) => {
        console.log("인터셉터 response:", response);
        return response;
    },
    async (error) => {
        console.log("인터셉터 error:", error)
        console.log("error.response.data.status:", error.response.data.status)

        // 토큰이 비정상적인 경우
        if (error.response.data.status === 401 || error.response.status === 401) {
            await store.dispatch('logout');
            await router.push('/member/login');
            return Promise.reject();
        }

        // access token이 만료된 경우
        else if (error.response.data.status === 403) {
            const refreshToken = "Bearer " + store.getters.refreshToken;
            console.log("refreshToken", refreshToken);
            const id = store.getters.id;

            // refresh 토큰 통해 access 토큰 갱신
            return await axios.post(
                serverUrl + "api/refresh",
                id,
                {
                    headers: {
                        'UseRefreshToken': true,
                        'Content-Type': 'text/plain'
                    }
                }
            ).then( async (response) => {
                console.log('refresh token 갱신 요청 후 response', response);
                // 갱신된 accessToken 등록
                const accessToken = response.data.result.accessToken;
                localStorage.setItem("accessToken", accessToken);
                store.commit('setAccessToken', accessToken);

                // 기존 요청 재시행
                const newRequestConfig = { ...originalRequestConfig };
                console.log("기존요청", newRequestConfig);

                newRequestConfig.headers.Authorization= "Bearer " + accessToken;

                return Promise.resolve(await axios(newRequestConfig));
            }).catch((error) => {
                console.log("error", error);
            });

        }

        // 로그인 오류의 경우 error 객체를 반환합니다.
        else if (error.response.data.result.errorDTO.status === 405) {
            return Promise.reject(error);
        }

        // 그 외 에러는 ErrorPage를 보여줍니다.
        else {
            console.log('그 외 error', error);
            console.log('error 메시지', error.response.data.result.errorDTO.message);
            router.push({
                name: 'ErrorPage',
                params: { errorMessage: error.response.data.result.errorDTO.message }
            });
        }
    }
);

export default axios;

