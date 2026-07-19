export default {
    async tt_ts_get_tb_esim(ma_tinh, soSim_moi, ma_tb, context) {
        let response = await context.$root.context.post(`/ccbs/executor/tt_ts_get_tb_esim`, {
            ma_tinh: ma_tinh,
            so_msin_moi: soSim_moi,
            so_tb: ma_tb
        })
        return response.data;
    },
    async tt_ts_get_tb_sim(ma_tb, soSim_moi, ma_tinh, context) {
        let response = await context.$root.context.post(`/ccbs/executor/tt_ts_get_tb_sim`, {
            ma_tinh: ma_tinh,
            so_msin_moi: soSim_moi,
            so_tb: ma_tb
        })
        return response.data;
    },
    async ts_tracuu_laytt_tb(ma_tb, context) {
        let response = await context.$root.context.post(`/ccbs/tracuu/ts_tracuu_laytt_tb`, {
            so_tb: ma_tb
        })
        return response.data;
    },
    async ts_tracuu_laytt_tb_ts(ma_tb, ma_tinh, context) {
        let response = await context.$root.context.post(`/ccbs/executor/ts_tracuu_laytt_tb_ts`, {
            ma_tinh: ma_tinh,
            so_tb: ma_tb
        })
        return response.data;
    }
}