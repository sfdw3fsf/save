export default {
  capnhat: (axios, data) => axios.post("/web-ecms/hatang/capphat-ip/update", data),
  confirm: (axios, data) => axios.post("/web-ecms/hatang/capphat-ip/confirm", data)
}
