import {createStore} from "vuex";

import axios from "axios";

const serverUrl = 'http://localhost:8080/';
const store = createStore({
    state() {
        return {
            id: null,
            accessToken: '123456',
            refreshToken: null
        };
    },
    mutations: {
        // id를 설정합니다.
        setId(state, id) {
            state.id = id;
        },
        // Access-Token를 설정합니다.
        setAccessToken(state, accessToken) {
            state.accessToken = accessToken;
        },
        // Refresh-Token를 설정합니다.
        setRefreshToken(state, refreshToken) {
            state.refreshToken = refreshToken;
        },
        // 초기화시킵니다.
        reset(state) {
            state.id = null;
            state.accessToken = null;
            state.refreshToken = null;
            localStorage.removeItem('id');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
        },
        // Storage에 state를 저장합니다.
        saveStateToStorage(state) {
            localStorage.setItem("id", state.id);
            localStorage.setItem("accessToken", state.accessToken);
            localStorage.setItem("refreshToken", state.refreshToken);
        },
        // Storage에서 state를 읽어옵니다.
        readStateFromStorage(state) {
            if (localStorage.getItem('id') != null) {
                state.id = localStorage.getItem('id');
            }
            if (localStorage.getItem('accessToken') != null) {
                state.accessToken = localStorage.getItem('accessToken');
            }
            if (localStorage.getItem('refreshToken') != null) {
                state.refreshToken = localStorage.getItem('refreshToken');
            }
        }

    },
    actions: {
        /**
         * 로그인 기능을 수행하는 액션 메서드입니다.
         * - 로그인 성공 시, store에 토큰과 아이디를 등록하고, 성공 문자를 반환합니다.
         * - 로그인 실패 시, ErrorDTO를 반환합니다.
         * @param context Vuex context 내장 객체
         * @param payload 컴포넌트로 부터 받은 로그인 정보
         */
        async login({commit}, payload) {

            console.log("payload: " + payload.value)
            const memberDTO = payload.value;

            return new Promise((resolve, reject) => {
                axios({
                    method: "post",
                    url: serverUrl + "api/member/login",
                    data: memberDTO
                }).then((response) => {

                    console.log("memberId", memberDTO.id);
                    console.log("accesstoken", response.data.result.accessToken);
                    console.log("refreshToken", response.data.result.refreshToken);

                    commit('setId', memberDTO.id);
                    commit('setAccessToken', response.data.result.accessToken);
                    commit('setRefreshToken', response.data.result.refreshToken);
                    commit('saveStateToStorage');

                    // 로그인 작업 성공 시, 성공 문구를 반환합니다.
                    resolve('success');

                }).catch((error) => {
                    console.log('login의 error', error);
                    const errorDTO = error.response.data.result.errorDTO;

                    console.log("errorDTO:", errorDTO);
                    // 로그인 작업 실패 시, ErrorDTO를 반환합니다.
                    reject(errorDTO);
                });
            })
        },
        async sendRefresh() {

            const refreshToken = store.getters.refreshToken;
            const id = store.getters.id;

            await axios.post(
                serverUrl + "api/refresh",
                {id},
                {
                    headers: {
                        Authorization: `Bearer ${refreshToken}`
                    }
                }
            ).then((response) => {
                console.log("response", response);
            }).catch((error) => {
                console.log("error", error);
            })
        },
        /**
         * 로그아웃을 시행합니다.
         *
         * @param state
         */
        logout({commit}) {
            commit('reset');
        },
        /**
         * Local Storage에 보관된 사용자 정보를 state에 load합니다.
         * @param commit
         */
        loadStateFromStorage({commit}) {
            commit('readStateFromStorage');
        }
    },
    getters: {
        /**
         * 저장소에 저장된 사용자 아이디를 반환합니다.
         *
         * @param state 저장소 유저 정보
         * @returns 사용자의 id
         */
        id(state) {
            return state.id;
        },
        /**
         * 저장소에 저장된 사용자의 access 토큰을 반환합니다.
         *
         * @param state 저장소 토큰 정보
         * @returns 사용자의 토큰
         */
        accessToken(state) {
            return state.accessToken;
        },
        /**
         * 저장소에 저장된 사용자의 refresh 토큰을 반환합니다.
         *
         * @param state 저장소 토큰 정보
         * @returns 사용자의 토큰
         */
        refreshToken(state) {
            return state.refreshToken;
        },
        /**
         * 저장소에 토큰의 유무를 반환합니다.
         *
         * @param state
         * @returns 토큰 유무
         */
        isAuthenticated(state) {
            return state.id != null;
        }
    }
});
export default store;