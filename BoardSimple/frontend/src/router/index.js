import {createRouter, createWebHistory} from 'vue-router'
import SignUpForm from "@/views/member/SignUpForm.vue";
import LoginForm from "@/views/member/LoginForm.vue";
import FreeBoardWrite from "@/views/board/FreeBoardWrite.vue";
import store from "@/store";
import FreeBoardList from "@/views/board/FreeBoardList.vue";
import NoticeBoardList from "@/views/board/NoticeBoardList.vue";
import NoticeBoardDetail from "@/views/board/NoticeBoardDetail.vue";
import MainBoard from "@/views/board/MainBoard.vue";
import FreeBoardDetail from "@/views/board/FreeBoardDetail.vue";
import QnaList from "@/views/qna/QnaList.vue";
import QnaDetail from "@/views/qna/QnaDetail.vue";
import QnaWrite from "@/views/qna/QnaWrite.vue";
import GalleryBoardWrite from "@/views/board/GalleryBoardWrite.vue";
import GalleryBoardDetail from "@/views/board/GalleryBoardDetail.vue";
import GalleryBoardList from "@/views/board/GalleryBoardList.vue";
import ErrorPage from "@/views/global/ErrorPage.vue";

const routes = [
    {
        path: '/',
        name: 'MainBoard',
        component: MainBoard
    },
    {
        path: '/member/signup',
        name: 'SignUpForm',
        component: SignUpForm,
        meta: {requiresUnAuth: true}
    },
    {
        path: '/member/login',
        name: 'LoginForm',
        component: LoginForm,
        meta: {requiresUnAuth: true}
    },
    {
        path: '/board/free',
        name: 'FreeBoardWrite',
        component: FreeBoardWrite,
        meta: {requiresAuth: true}
    },
    {
        path: '/board/free/modify/:seq',
        name: 'FreeBoardUpdate',
        component: FreeBoardWrite,
        meta: {requiresAuth: true},
        beforeEnter: (to, from, next) => {
            if (to.params.seq) {
                next(); // seq가 있는 경우에만 update로 이동합니다.
            } else {
                // seq가 없는 경우 이동하지 않습니다.
                next(false);
            }
        }
    },
    {
        path: '/board/free/:seq',
        name: 'FreeBoardDetail',
        component: FreeBoardDetail,
    },
    {
        path: '/board/frees',
        name: 'FreeBoardList',
        component: FreeBoardList,
    },
    {
        path: '/board/notices',
        name: 'NoticeBoardList',
        component: NoticeBoardList,
        meta: {requiresAuth: true}
    },
    {
        path: '/board/notice/:seq',
        name: 'NoticeBoardDetail',
        component: NoticeBoardDetail
    },
    {
        path: '/board/gallery',
        name: 'GalleryBoardWrite',
        component: GalleryBoardWrite,
        meta: {requiresAuth: true}
    },
    {
        path: '/board/gallery/modify/:seq',
        name: 'GalleryBoardUpdate',
        component: GalleryBoardWrite,
        meta: {requiresAuth: true},
        beforeEnter: (to, from, next) => {
            if (to.params.seq) {
                next();
            } else {
                next(false);
            }
        }
    },
    {
        path: '/board/gallery/:seq',
        name: 'GalleryBoardDetail',
        component: GalleryBoardDetail,
    },
    {
        path: '/board/galleries',
        name: 'GalleryBoardList',
        component: GalleryBoardList,
    },
    {
        path: '/qnas',
        name: 'QnaList',
        component: QnaList
    },
    {
        path: '/qna',
        name: 'QnaWrite',
        component: QnaWrite
    },
    {
        path: '/qna/:seq',
        name: 'QnaDetail',
        component: QnaDetail
    },
    {
        path: '/qna/modify/:seq',
        name: 'QnaUpdate',
        component: QnaWrite,
        meta: {requiresAuth: true},
        beforeEnter: (to, from, next) => {
            if (to.params.seq) {
                next();
            } else {
                next(false);
            }
        }
    },
    {
        path: '/board/gallery/modify/:seq',
        name: 'GalleryBoardUpdate',
        component: GalleryBoardWrite,
        meta: {requiresAuth: true},

    },
    {
        path: '/error/:errorMessage',
        name: 'ErrorPage',
        component: ErrorPage
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach(function(to, from, next) {

    if (to.meta.requiresAuth && !store.getters.isAuthenticated) {

        // 로그인 하지 않은 사용자가 제한된 페이지에 접속하려는 경우,
        next('/member/login');

    } else if (to.meta.requiresUnAuth && store.getters.isAuthenticated) {

        // 로그인 한 사용자가 로그인 페이지에 접속하려는 경우,
        next('/')

    } else {
        next();
    }
})

export default router