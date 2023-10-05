import axios from "./axios";

const serverUrl = 'http://localhost:8080/'

export const downloadFile = (file) => {
    return axios({
        method: 'get',
        url: serverUrl + 'api/file/download',
        params: {
            originName: file.originName,
            fileName: file.fileName
        },
        // response type을 blob으로 설정합니다.
        responseType: 'blob'
    }).then(response => {
        console.log(response.data)
        const blob = new Blob([response.data]);

        const url = window.URL.createObjectURL(blob);

        const link = document.createElement('a')
        link.href = url;
        link.download = file.originName;
        document.body.appendChild(link);
        link.click();
    })
}