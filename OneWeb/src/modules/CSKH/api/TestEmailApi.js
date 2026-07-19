import axios from "axios"

export default {
    kiemTraKetNoi: (axios, data) => axios.post('web-thuno/api/thu-no/xu-ly-thue-bao/test-email-connection', data),
}