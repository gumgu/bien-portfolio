/**
 * 전달받은 변수가 비어있는지 확인합니다.
 * @param value
 * @returns {boolean} 비어있는 경우 = true
 * @returns {boolean} 비어있지 않은 경우 = false
 */
export const required = (value) => {
    return value === '' || value === undefined;
}

/**
 * 전달받은 변수가 min과 max 사이에 있는지 확인합니다.
 * @param min 최소값
 * @param max 최대값
 * @param value
 * @returns {boolean} 범위 내 위치하지 않는 경우 = true
 * @returns {boolean} 범위 내 위치하는 경우 = false
 */
export const sizeInclude = (min, max, value) => {
    if(value === '' || value === undefined) {
        return true
    } else {
        return !(min <= value.length && value.length <= max)
    }
}
