<template src="./frmNhanVienTH.html"></template>
<script>

import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
export default {
    components: {
        DataGridCustom
    },
    data() {
        return {
            colums: [
                {
                    headerText: "Ngày giao",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ngaygiao",
                },
                {
                    headerText: "Người giao",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "nv_giao",
                },
                {
                    headerText: "Đơn vị",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ten_dv",
                },
                {
                    headerText: "Mã NV TH",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ma_nv",
                },
                {
                    headerText: "Người thực hiện",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ten_nv",
                },
                {
                    headerText: "Điện thoại",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "so_dt",
                },
                {
                    headerText: "Nhiệm vụ",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "nhiemvu",
                },
                {
                    headerText: "Kết quả XL",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "kqxl_id",
                },
                {
                    headerText: "Trạng thái",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "trangthai",
                },
                {
                    headerText: "Ghi chú",
                    autoFit: true,
                    allowSorting: false,
                    fieldName: "ghichu",
                },
            ],
            tchopdong : {},
            tt_nd : {},
            nhanvien : {},
            dt : {},
            vphieu_id : 0,
            vphieu_bh_id : 0,
            vphieu_kn_id : 0,
            vbaohong_id : 0,
            vhdtb_id : 0
        }
    },
    methods: {
        async frmNhanVienTH_Load()
        {
            try
            {
                this.loading(true)
                let rs
                if (this.vphieu_id > 0) {
                    rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/lay_ds_nhanvien_theo_phieu_id", {
                        "p_kieu_id": 2,
                        "p_nhanvien_id": this.$root.token.getNhanVienID(),
                        "p_phieu_id": this.vphieu_id
                    })
                    this.dt = rs.data.data
                }
                if (this.vphieu_bh_id > 0) {
                    rs = await this.axios.get("web-baohong/baohong/lay_ds_nhanvien_theo_phieu_id", {params: 
                        {
                            "kieu_id": 2,
                            "phieu_id": this.vphieu_bh_id
                        }
                    })
                    this.dt = rs.data.data
                }
                if (this.vphieu_kn_id > 0)
                {
                    rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/lay_ds_nhanvien_theo_phieukn_id", {
                        "vkieu_id": 2,
                        "vnhanvien_id": this.$root.token.getNhanVienID(),
                        "vphieukn_id": this.vphieu_kn_id
                    })
                    this.dt = rs.data.data
                }
                if (this.vphieu_id == 0 & this.vphieu_bh_id == 0 && this.vphieu_kn_id == 0 && this.vbaohong_id != 0) {
                    rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/lay_ds_nhanvien_xuly", 
                        {
                            "vid": this.vbaohong_id,
                            "vkieu_id": 1
                        }   
                    )
                    this.dt = rs.data.data
                }
                if (this.vphieu_id == 0 & this.vphieu_bh_id == 0 && this.vphieu_kn_id == 0 && this.vhdtb_id != 0) {
                    rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/lay_ds_nhanvien_xuly", 
                        {
                            "vid": this.vhdtb_id,
                            "vkieu_id": 2
                        }   
                    )
                    this.dt = rs.data.data
                }
                this.$refs.gridDaGiao.dataSource = this.dt;
                this.loading(false)
            }
            catch (err)
            {
                this.loading(false)
                this.$toast.error("Có lỗi: "+ err.message);
            }
        },
       show() {
           this.frmNhanVienTH_Load()
           this.$refs.frmNhanVienTH.show()
       } 
    }
}
</script>