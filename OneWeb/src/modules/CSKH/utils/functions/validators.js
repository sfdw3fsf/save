/**
 * To check whether the object is null or undefined.
 * @param {Object} value - To check the object is null or undefined
 * @return {boolean}
 * @private
 */
 export function isNullOrUndefined(value) {
    return value === undefined || value === null;
}
/**
 * To check whether the object is undefined.
 * @param {Object} value - To check the object is undefined
 * @return {boolean}
 * @private
 */
export function isUndefined(value) {
    return ('undefined' === typeof value);
}

export function isNullOrEmpty(value) {
    if (isNullOrUndefined(value)) return true
    return isNullOrUndefined(value.trim()) || value.trim() === ""
}

/**
 * To convert the object to string for query url
 * @param  {Object} data
 * @returns string
 * @private
 */
 export function queryParams(data) {
    var array = [];
    var keys = Object.keys(data);
    for (var _i = 0, keys_2 = keys; _i < keys_2.length; _i++) {
        var key = keys_2[_i];
        array.push(encodeURIComponent(key) + '=' + encodeURIComponent('' + data[key]));
    }
    return array.join('&');
}

export function formatString(data) {
    if (isNullOrEmpty(data)) return ''
    return data.trim()
}

export function kiemTraCauHoiKHL(cauHoi, vRchId, vKieu) {
    console.log("kiemTraCauHoiKHL 1")
    if (vKieu == 1)
    {

        let slChuaHT = cauHoi.filter(x => x.MA_HL == 'CHUA_HT' && x.RCH_ID == vRchId).length
        console.log("kiemTraCauHoiKHL slChuaHT", slChuaHT)
        if (slChuaHT > 0)
        {
            return 1
        }
        else
        {
            let slDaHT = cauHoi.filter(x => x.MA_HL == 'DA_HT' && x.RCH_ID == vRchId).length
            console.log("kiemTraCauHoiKHL slDaHT", slDaHT)
            if (slDaHT > 0)
            {
                return 2
            }
        }
    }
    else if (vKieu == 2)
    {
        let sl = cauHoi.filter(x => x.MA_HL == 'KHL_NVKT' && x.MA_TL == 'TL_KHL_KT' && x.RCH_ID == vRchId).length
        console.log("kiemTraCauHoiKHL sl", sl)
        if (sl > 0)
        {
            return 1
        }
    }
    else if (vKieu == 3)
    {
        let sl = cauHoi.filter(x => x.MA_HL == 'KHL_NVKT' && x.MA_TL == 'KT3' && x.RCH_ID == vRchId).length
        console.log("kiemTraCauHoiKHL sl", sl)
        if (sl > 0)
        {
            return 1
        }
    }
    return 0
}

export function handleNotiKhaoSat(toast,data) {
    if(!data) return
    let listMessages = data.split("#")
    listMessages.forEach(message => {
        let pattern = /.*\d/g;
        let result = message.match(pattern)
        // if (
        //     result &&
        //     result.length > 0 &&
        //     result[0].includes("1")
        //   ) {
        //     toast.success(message.replace(result, ""));
        //   } else {
        //     toast.error(message.replace(result, ""));
        //   }
        if (message.includes('1'))
        {
            toast.success(message.replace(result, ""))
        }
        else
        {
            toast.error(message.replace(result, ""))
        }
    });
}
