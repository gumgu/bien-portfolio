import axios from "axios";
import router from "@/router";

const serverUrl = 'http://localhost:8080/'

/**
 * 새로운 댓글을 작성합니다.
 *
 * @param reply 작성할 댓글
 */
export const saveReply = (reply) => {
    console.log("axios save용 reply", reply);
    return axios({
        method: "post",
        url: serverUrl + "api/reply",
        data: reply
    }).catch((error) => {
        console.log(error)
    })
}

/**
 * 게시글의 댓글 리스트를 조회합니다.
 *
 * @param seq 조회할 게시글 Seq
 */
export const getReplyList = (seq) => {
    return axios({
        method: "get",
        url: serverUrl + "api/replies/" + seq,
    }).then((response) => {
        const replyList = response.data.result.replyList;
        console.log("ReplyAxios replyList", replyList);
        return replyList;
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
 * 주어진 seq의 댓글을 삭제합니다.
 *
 * @param replySeq 삭제할 댓글 seq
 */
export const deleteReply = (replySeq) => {
    return axios({
        method: "delete",
        url: serverUrl + "api/reply/" + replySeq
    }).catch((error) => {
        const errorDTO = error;
        console.log(errorDTO)

        router.push({
            name: 'ErrorPage',
            query: errorDTO
        })
    })
}