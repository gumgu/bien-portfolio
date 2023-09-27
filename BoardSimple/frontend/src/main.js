import { createApp } from 'vue'
import App from './App.vue'
import store from "@/store";
import router from "@/router";
import axios from "@/api/axios";

// createApp(App).use(store).use(router).mount('#app')

const app = createApp(App)
app.config.globalProperties.$axios = axios // 전역변수 설정
app.config.globalProperties.$serverUrl = '//localhost:8080' // api Server URL
app.config.globalProperties.$store = store
app
    .use(router)
    .use(store)
    .mount('#app')
