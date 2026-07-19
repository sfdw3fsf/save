
// export default async function(context, soDt, noiDung) {
    
//     if (!soDt) return

//     if (soDt.startsWith("0")) {
//         soDt = `84${soDt.substr(1)}`
//     }

//     let data = {
//         content: noiDung,
//         msisdn: soDt,
//         mt_type: "reply"
//     }

//     try {
//         await context.$root.context.post("/web-cabman/traphieukhieunai/off-send-sms", data)
//     } catch(err) {
//         throw 'Lỗi khi gửi tin nhắn'
//     }
// }