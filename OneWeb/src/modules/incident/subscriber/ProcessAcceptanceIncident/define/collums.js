import Vue from 'vue'
const collumns = {
    collumnsDSBH: [
        {
            fieldName: "nv_giu",
            headerText: "Người giữ",
            allowFiltering: true,
            autoFit: "true",
            freeze: "left"
        },
        {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            autoFit: "true",
            freeze: "left"
        },
        {
            fieldName: "ma_lt",
            headerText: "Số ảo",
            allowFiltering: true,
            autoFit: "true",
            freeze: "left"
        },
        {
            fieldName: "loaihinh_tb",
            headerText: "Loại hình",
            allowFiltering: true,
            autoFit: "true",
            freeze: "left"
        },
        {
            fieldName: "dienthoai_lh",
            headerText: "Số liên hệ",
            allowFiltering: true,
            autoFit: "true",
        },
        {
            fieldName: "dienthoai_bh",
            headerText: "Điện thoại báo hỏng",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nguoi_bh",
            headerText: "Người báo hỏng",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ngay_bh",
            headerText: "Ngày báo hỏng",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "thoigian_ck",
            headerText: "Thời gian CK",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "donvigiao",
            headerText: "Đơn vị giao",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nvkt_db",
            headerText: "Nhân viên KT",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "diachi_ld",
            headerText: "Địa chỉ LĐ",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "goi_kt",
            headerText: "Gói KT",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ten_plkh",
            headerText: "Phân loại KH",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "kenh_tn",
            headerText: "Kênh tiếp nhận",
            allowFiltering: true,
            autoFit: "true",
            headerTemplate: () => {
                return {
                    template: Vue.component("action-check-all", {
                        template: `
                        <div class="header" style="position: relative; z-index: 9999;">
                            <i class="icon nc-icon-outline ui-2_filter" @click="handleValueClick($event)"></i>
                            <label v-if="!show_filter" class="header-label">Kênh tiếp nhận</label>
                            <div v-if="show_filter" style="position: absolute; top: 0px">
                                <select v-model="kenhtn" style="width: 160px" @change="handleValueChange" v-bind:placeholder="placeholderText">
                                    <option disabled value="">{{ placeholderText }}</option>
                                    <option v-for="item in groupedValues" :value="item.kenh_tn" :key="item.kenh_tn">
                                        {{ item.kenh_tn }}
                                    </option>
                                </select> 
                            </div>
                        </div>
                            `,
                        data: function () {
                            return {
                                 groupedValues: [],
                                 show_filter: false,
                                 kenhtn: 0,
                                 showTTHD:"",
                                 placeholderText: "Kênh tiếp nhận"
                             };
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent
                            }, 
                        },
                        mounted() {
                            const distinctArray = this.parent.dtList.filter((item, index, self) =>
                            index === self.findIndex(obj => obj.kenh_tn === item.kenh_tn)
                            );
                            this.groupedValues = distinctArray
                        },
                        methods: {
                            handleValueClick(event) { 
                                event.stopPropagation();
                                this.show_filter = !this.show_filter
                                this.parent.HienThiDanhSach()
                            },
                            handleValueChange(){
                                this.parent.dtList =  this.parent.dtList.filter(x => x.kenh_tn == this.kenhtn)
                            }
                        }
                    }),
                };
            },
        },
        {
            fieldName: "chitieu_tg",
            headerText: "Chỉ tiêu thời gian",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "tgxl",
            headerText: "Giờ xử lý",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "gio_conlai",
            headerText: "Giờ còn lại",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "gio_ck",
            headerText: "Giờ CK",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "mucdo",
            headerText: "Mức độ",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ds_nhanvien_th",
            headerText: "Nhân viên TH",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "donvinhan",
            headerText: "Đơn vị nhận",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "tinhtrang",
            headerText: "Tình trạng",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "luot_bh",
            headerText: "Lượt BH",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "lan_pa",
            headerText: "KH gọi",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nguyennhan",
            headerText: "Nguyên nhân",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ngaygiao",
            headerText: "Ngày giao",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nd_giao",
            headerText: "Nội dung giao",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "lan_ks",
            headerText: "Lần khảo sát",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ngay_sd",
            headerText: "Ngày SD",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ghichu_hong",
            headerText: "Ghi chú hỏng",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ghichu_th",
            headerText: "Ghi chú TH",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "lydoton",
            headerText: "Lý do tồn",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ghichuton",
            headerText: "Ghi chú tồn",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ttvt_ton",
            headerText: "Ghi chú TTVT",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nhanvienql",
            headerText: "Nhân viên QL",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ten_cap",
            headerText: "Cáp gốc",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "doi_cap",
            headerText: "Đôi số",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ten_kc",
            headerText: "Kết cuối",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "vitri",
            headerText: "Vị trí",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "tientrinh",
            headerText: "Tiến trình",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "hensuatu",
            headerText: "Hẹn sửa từ",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "hensuaden",
            headerText: "Hẹn sửa đến",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ten_kv",
            headerText: "Khu vực",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nvkd_db",
            headerText: "Nhân viên kinh doanh",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nd_tra_con",
            headerText: "Nội dung trả",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "ngaytra_con",
            headerText: "Ngày trả",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "chuyenmang",
            headerText: "NCC khác(nhà cung cấp khác)",
            allowFiltering: true,
            autoFit: "true"
            
        },
        {
            fieldName: "nhom_kh",
            headerText: "Nhóm KH",
            allowFiltering: true,
            autoFit: "true"
            
        },
    ],
}

export default collumns
