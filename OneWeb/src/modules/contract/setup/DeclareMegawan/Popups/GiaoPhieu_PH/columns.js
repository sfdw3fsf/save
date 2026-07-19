const columns = {
    columnsDS_PYC: [
        { fieldName: "PHIEU_ID", headerText: "ID phiếu", textAlign: "left", width: "200px" },
        { fieldName: "MA_TB", headerText: "Số máy/Acc", textAlign: "left", autoFit: "true" },
        { fieldName: "NGAYGIAO", headerText: "Ngày giao", textAlign: "center", autoFit: "true" },
        { fieldName: "NGUOIGIAO", headerText: "Người giao", textAlign: "left", autoFit: "true" },
        { fieldName: "DV_NHAN", headerText: "Đơn vị nhận", textAlign: "left", autoFit: "true" },
        { fieldName: "ND_GIAO", headerText: "Nội dung giao", textAlign: "left", autoFit: "true" },
        { fieldName: "TRANGTHAI", headerText: "Trạng thái", textAlign: "left", autoFit: "true" },
        { fieldName: "NGAY_TH", headerText: "Ngày thực hiện", textAlign: "center", autoFit: "true" },
        { fieldName: "NGUOI_TH", headerText: "Người thực hiện", textAlign: "left", autoFit: "true" },
        { fieldName: "ND_THUCHIEN", headerText: "Nội dung thực hiện", textAlign: "left", autoFit: "true" },
        { fieldName: "NGAYTRA", headerText: "Ngày trả", textAlign: "center", autoFit: "true" },
        { fieldName: "ND_TRA", headerText: "Nội dung trả", textAlign: "left", autoFit: "true" },
        { fieldName: "LYDO_TON", headerText: "Lý do tồn", textAlign: "left", autoFit: "true" },
        { fieldName: "HUONGGIAO", headerText: "Hướng giao", textAlign: "left", autoFit: "true" },
    ],
    columnsDS_PDG: [
        { fieldName: "phieu_id", headerText: "ID phiếu", textAlign: "left", width: "200px" },
        { fieldName: "nd_giao", headerText: "Nội dung giao", textAlign: "left", autoFit: "true" },
        { fieldName: "ngaygiao", headerText: "Ngày giao", textAlign: "center", autoFit: "true" },
        { fieldName: "nguoigiao", headerText: "Người giao", textAlign: "left", autoFit: "true" },
        { fieldName: "ten_dv", headerText: "Đơn vị nhận", textAlign: "left", autoFit: "true" },
        { fieldName: "ketqua_xl", headerText: "KQ xử lý", textAlign: "left", autoFit: "true" },
        { fieldName: "nd_thuchien", headerText: "Nội dung thực hiện", textAlign: "left", autoFit: "true" },
        { fieldName: "nv_thuchien", headerText: "Người thực hiện", textAlign: "left", autoFit: "true" },
        { fieldName: "ngay_th", headerText: "Ngày thực hiện", textAlign: "center", autoFit: "true" },      
        { fieldName: "huonggiao", headerText: "Hướng giao", textAlign: "left", autoFit: "true" },
        // {
        //     fieldName: 'phieu_id',
        //     textAlign: 'Center',
        //     width: 90,
        //     template: function () {
        //         return {
        //             template: Vue.component('xoaLyDoHuyTemplate', {
        //                 template: `<a class="btn btn-huylydo lh14" @click="onClickHuyGiao"
        //                                 style="background-color: #3684e0 !important; padding: 5px 15px; color: red">
        //                                 <span class="icon nc-icon-glyph ui-1_simple-remove"></span>
        //                             </a>`,
        //                 data () {
        //                     return {data: {}}
        //                 },
        //                 computed: {
        //                     parent () {
        //                     return this.$parent.$parent.$parent
        //                     }
        //                 },
        //                 methods: {
        //                     onClickHuyGiao () {
        //                     this.parent.tsbtnHuyGiao_Click(this.data)
        //                     }
        //                 }
        //             })
        //         }
        //     }
        // }
    ]
}

export default columns
