import dayjs from "dayjs";

/**
 * 전달 받은 날짜의 양식을 formatting합니다. (ex. YYYY-MM-DD)
 *
 * @param value 날짜
 * @param form 변경을 원하는 날짜 양식
 * @returns 1. null인 경우, '-'
 * @returns 2. 날짜 양식
 */
export const dateTime = (value, form) => {
    if (value == null) {
        return '-';
    } else {
        return dayjs(value).format(form)
    }
}
