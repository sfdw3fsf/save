// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setSMTPInfor(state, data) {
        state.SMTP_CSKH_SERVER = data.SMTP_CSKH_SERVER
        state.SMTP_CSKH_USERNAME = data.SMTP_CSKH_USERNAME
        state.SMTP_CSKH_PASSWORD = data.SMTP_CSKH_PASSWORD
        state.SMTP_CSKH_PORT = data.SMTP_CSKH_PORT
        state.SMTP_ALIAS = data.SMTP_ALIAS

        state.SMTP_CSKH_DCLH = data.SMTP_CSKH_DCLH
        state.SMTP_CSKH_EMAIL = data.SMTP_CSKH_EMAIL
        state.SMTP_CSKH_SDT = data.SMTP_CSKH_SDT
    },
    clearData(state, data) {
        state.SMTP_CSKH_DCLH = ''
        state.SMTP_CSKH_EMAIL = ''
        state.SMTP_CSKH_SDT = ''

        state.SMTP_CSKH_SERVER = ''
        state.SMTP_CSKH_USERNAME = ''
        state.SMTP_CSKH_PASSWORD = ''
        state.SMTP_CSKH_PORT = ''
        state.SMTP_ALIAS = ''

    }
}
