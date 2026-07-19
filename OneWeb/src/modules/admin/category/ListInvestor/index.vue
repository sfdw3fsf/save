<template>
    <div>
        <vue-bread-crumb :header="header" />
        <vue-nav>
            <ul class="list">             
              
                <li
                    @click="clickButton('layTT')"
                    :class="button.layTT ? 'active' : 'none-active'"
                >
                    <a>
                        <span class="icon one-file-attach"></span>Lấy TT
                    </a>
                </li>
                <li
                    @click="clickButton('nhapmoi')"
                    :class="button.nhapmoi ? 'active' : 'none-active'"
                >
                    <a>
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li
                    @click="clickButton('ghilai')"
                    :class="button.ghilai ? 'active' : 'none-active'"
                >
                    <a>
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li
                    @click="clickButton('huy')"
                    :class="button.huy ? 'active' : 'none-active'"
                >
                    <a>
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                    </a>
                </li>
                <li
                    @click="clickButton('xoa')"
                    :class="button.xoa ? 'active' : 'none-active'"
                >
                    <a>
                        <span class="icon one-trash"></span>Xóa
                    </a>
                </li>
                
            </ul>
        </vue-nav>
                <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin tổ chức quản lý dự án</div>
                <div class="grid-stack-box">
                    <div class="box-col box-form" id="boxLeft">
                        <div class="info-row">
                            <div class="key">Phân loại</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="form.phanloai" :allowFiltering="true" dataTextField="ten_loai_cdt"
                                        dataValueField="loai_cdt_id" :dataSource="cbophanloai" ></SelectExt>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key">Mã TCQLDA <span class="red">(*)</span></div>
                                    <div class="value">
                                        <input 
                                        v-model="txtmaTCQLDA"
                                        type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key">Tên TCQLDA <span class="red">(*)</span></div>
                                    <div class="value">
                                        <input 
                                        v-model="txttenTCQLDA"
                                        type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key">Số điện thoại <span class="red">(*)</span></div>
                                    <div class="value">
                                        <input 
                                        v-model="txtSDT"
                                        type="Number" required class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key">Người liên hệ <span class="red">(*)</span></div>
                                    <div class="value">
                                        <input 
                                        v-model="txtnguoilienhe"
                                        type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key">Chức vụ</div>
                                    <div class="value">
                                        <input 
                                        v-model="txtchucvu"
                                        type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key">Email</div>
                                    <div class="value">
                                        <input 
                                        v-model="txtemail"
                                        type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w80">Mã số thuế</div>
                                    <div class="value">
                                        <input 
                                        v-model="txtmasothue"
                                        type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key">Số tài khoản <span class="red">(*)</span></div>
                                    <div class="value">
                                        <input 
                                        v-model="txtsotaikhoan"
                                        type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key">Ngân hàng <span class="red">(*)</span></div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <SelectExt v-model="form.nganhang" :allowFiltering="true" dataTextField="ten_nh"
                                                dataValueField="nganhang_id" :dataSource="cbonganhang"></SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-col box-form" id="boxRight">
                        <div class="info-row">
                            <div class="key w65">Địa chỉ<span class="red">(*)</span></div>
                            <div class="value">
                                <textarea 
                                v-model="txtdiachi"
                                name="" class="form-control" style="height: 90px;resize: none;"></textarea>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w65">Ghi chú<span class="red">(*)</span></div>
                            <div class="value">
                                <textarea
                                v-model="txtghichu"
                                name="" class="form-control" style="height: 90px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách tổ chức quản lý dự án</div>
                    <DataGrid :columns="columns_danhsach" ref="danhsach" :dataSource="options.griddanhsachTCQLDA" :enable-paging-server="false"
                    :allowPaging="true" :showFilter="true" @selectedRowChanged="onDanhSachSelected">
                    </DataGrid>
            </div>
        </div>
    </div>
</template>
<script>
import '@/assets/vendor/jquery/split'
import moment from "moment"
import define from './define'
export default {
    prop: [],
    components: {
        moment,
  },
    data() {
        return {
            ...define,
        };
    },
    mounted() {
        Split(['#boxLeft', '#boxRight'], {
        sizes: [70, 30],
        gutterSize: 16,
        onDragEnd: function (sizes) {
        },
        });

        this.loadDanhSachChuDauTu();       

    },
    methods: {
        loadDanhSachChuDauTu() {
            this.loadGrid();
            this.loadNganHang();
            this.loadPhanLoai();        
        },
        async loadGrid() {
           try {
                this.loading(true);
                let res =   await this.axios.post("web-toanha/danhmuc-chudautu/sp_lay_ds_chu_dautu", {});
                if (res.data.error_code && res.data.error_code === this.success_code) {
                    this.options.griddanhsachTCQLDA = res.data.data
                    if (this.options.griddanhsachTCQLDA != null) {

                        this.options.griddanhsachTCQLDA = [...this.options.griddanhsachTCQLDA]
                        this.loading(false);
                        console.log("load grid");
                    }
            }
            this.SetButton(3);
           } catch (e) {
               console.log("lỗi load grid", e);
           }
        },
        async loadPhanLoai() {

           let res = await this.axios.post("web-toanha/danhmuc-chudautu/sp_lay_ds_loai_chudt", {});
            if (res.data.error_code && res.data.error_code === this.success_code) {
                this.cbophanloai = res.data.data ? res.data.data :[]
            }
            else {
                console.log("lỗi get danh sách loại chủ đầu tư")
            }
        },
        async loadNganHang() {            
           let res = await this.axios.post("web-toanha/danhmuc-chudautu/sp_lay_ds_nganhang", {});

            if (res.data.error_code && res.data.error_code === this.success_code) {
                this.cbonganhang = res.data.data ? res.data.data :[]
            }
            else {
                console.log("lỗi get danh sách ngân hàng")
            }                 

        },
        onDanhSachSelected(item) {
            if (item != null) {
                this.txttenTCQLDA = item.ten_chudtu,
                this.txtmaTCQLDA = item.ma_chudtu,
                this.txtSDT = item.so_dt,
                this.txtchucvu = item.chucvu,
                this.txtsotaikhoan = item.taikhoan,
                this.txtemail = item.email,
                this.txtnguoilienhe = item.nguoi_lh,
                this.txtdiachi = item.diachi,
                this.txtghichu = item.ghichu,
                this.form.nganhang = item.nganhang_id,
                this.form.phanloai = item.loai_cdt_id,
                this.txtmasothue = item.mst,
                this.CHU_DAU_TU_ID = item.chudtu_id,
                //
                this.current.phanloaitbd = item.loai_cdt_id,
                this.current.nganhang = item.nganhang_id,
                this.current.tenchudautu = item.ten_chudtu,
                this.current.machudautu = item.ma_chudtu,
                this.current.sdt = item.so_dt,
                this.current.chucvu = item.chucvu,
                this.current.sotaikhoan = item.taikhoan,
                this.current.nguoilienhe = item.nguoi_lh,
                this.current.email = item.email,
                this.current.masothue = item.mst,
                this.current.diachi = item.diachi,
                this.current.ghichu = item.ghichu

                this.button.xoa = true
            }
        },
        Clear() {
            this.txttenTCQLDA = null,
            this.txtmaTCQLDA = null,
            this.txtSDT = null,
            this.txtchucvu = null,
            this.txtsotaikhoan = null,
            this.txtemail = null,
            this.txtnguoilienhe = null,
            this.txtdiachi = null,
            this.txtghichu = null,
            this.form.nganhang = null,
            this.form.phanloai = null,
            this.txtmasothue = null
        },
       async GhiLai() {
            if (!this.KiemTraDuLieu())
                return false;
            this.loading(true);     
            let ngay_cn = moment(new Date()).format('YYYY-MM-DD HH:mm:ss');
            let may_cn = this.$root.token.getMaNhanVien();
            let nguoi_cn = this.$root.token.getUserName();
            let insert = 0;
            let chudautu_id = this.CHU_DAU_TU_ID;
            try{      
                await fetch('https://api.ipify.org?format=json')
                .then(x => x.json())
                .then(({ ip }) => {          
                this.ip_cn =  ip;
                });
                
            }catch(e) {
                this.ip_cn = "";
            }           
                // tạo dữ liệu
            if (!this.button.nhapmoi) {
                insert = 1;
                chudautu_id = null
            }
            let danhsach = [
                {
                    chudtu_id: chudautu_id,
                    ma_chudtu: this.txtmaTCQLDA,
                    ten_chudtu: this.txttenTCQLDA,
                    diachi: this.txtdiachi,
                    so_dt: this.txtSDT,
                    nguoi_lh: this.txtnguoilienhe,
                    ghichu: this.txtghichu,
                    ngay_cn: ngay_cn,
                    may_cn: may_cn,
                    nguoi_cn: nguoi_cn,
                    ip_cn: this.ip_cn,
                    tinhtrang: '',
                    taikhoan: this.txtsotaikhoan,
                    nganhang_id: this.form.nganhang,
                    loai_cdt_id: this.form.phanloai,
                    chucvu: this.txtchucvu,
                    mst: this.txtmasothue,
                    email: this.txtemail
                }
            ]

            console.log("ghi lai", danhsach);
            await this.axios.post("web-toanha/danhmuc-chudautu/sp_capnhat_chu_dtu",
                {
                    "is_insert": insert,
                    "danhsach": JSON.stringify(danhsach)
                }
            ).then(res => {
                if (res.data.error_code && res.data.error_code === this.success_code) {
                    if (!this.button.nhapmoi) {
                        this.$toast.success("Thêm mới thành công");
                    } else {
                        this.$toast.success("Cập nhật thành công");
                    }
                    this.loading(false);
                    this.loadGrid();
                } else {
                    this.$toast.error("Lỗi");
                    this.loading(false);
                }
            })

        },
        Xoa() {
            this.$confirm(
                "Bạn chắc chắn muốn xóa thông tin chủ đầu tư này không?",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không",
                    type: "warning"
                }
            ).then (async () => {
                try {
                    this.axios.post("web-toanha/danhmuc-chudautu/sp_xoa_chu_dtu", 
                    {
                       "chudtu_id": this.CHU_DAU_TU_ID
                    }).then(res =>
                    {
                        if (res.data.error_code && res.data.error_code === this.success_code) {
                            this.$toast.success("Xóa thành công!");
                            console.log("chu truong id xoa: -", this.CHU_DAU_TU_ID);
                            this.loadGrid();
                        } else {
                            this.$toast.error("Lỗi");
                        }
                    })
                } catch (e) {
                    this.$toast.error("Không thể xóa bản ghi này");
                }
            });
         
        },
        KiemTraDuLieu(){
             if (this.txtmaTCQLDA == null || this.txtmaTCQLDA == '') {
                this.$toast.warning("Bạn chưa nhập mã chủ đầu tư");
                return false;
            }           
            if (this.txttenTCQLDA == null || this.txttenTCQLDA == '') {
                this.$toast.warning("Bạn chưa nhập Tên chủ đầu tư");
                return false;
            }
            if (this.txttenTCQLDA.trim().length > 200) {
                this.$toast.warning("Tên chủ đầu tư không được lớn hơn 200 ký tự");
                return false;
            }
            if (this.txtnguoilienhe == null || this.txtnguoilienhe == '' ) {
                this.$toast.warning("Bạn chưa nhập tên người lên hệ");
                return false;
            }
            if (this.txtnguoilienhe !=null && this.txtnguoilienhe.trim().length > 200) {
                this.$toast.warning("Tên người liên hệ không được lớn hơn 200 ký tự");
                return false;
            }
            if (this.txtdiachi == null || this.txtdiachi=='') {
                this.$toast.warning("Bạn chưa nhập địa chỉ");
                return false;
            }
            if (this.txtdiachi !=null && this.txtdiachi.trim().length > 500) {
                this.$toast.warning("Địa chỉ không được lớn hơn 500 ký tự");
                return false;
            }  
            if (this.txtghichu == null || this.txtghichu.trim() =='') {
                this.$toast.warning("Bạn chưa nhập ghi chú");
                return false;
            }
            if (this.txtghichu !=null && this.txtghichu.trim().length > 1000) {
                this.$toast.warning("Ghi chú không được lớn hơn 1000 ký tự");
                return false;
            } 

            if (this.txtSDT ==null || this.txtSDT=='') {
                this.$toast.warning("Bạn chưa nhập Số điện thoại");
                return false;
            }      
            if (this.txtSDT !== null) {
                if (this.txtSDT.length > 11) {
                    this.$toast.warning("Số điện thoại không được lớn hơn 11 ký tự");
                    return false;
                }
                if (this.txtSDT.length < 7) {
                    this.$toast.warning("Số điện thoại không được ít hơn 7 ký tự");
                    return false;
                }
            } 
            if (this.txtsotaikhoan ==null || this.txtsotaikhoan=='') {
                this.$toast.warning("Bạn chưa nhập số tài khoản");
                return false;
            }
            if (this.txtsotaikhoan != null)                      
            {
                if (this.txtsotaikhoan.length > 20) {
                    this.$toast.warning("Số tài khoản không được lớn hơn 20 ký tự");
                    return false;
                }
            }
            return true;
        },
        clickButton(key) {
            if(!this.button[key]) {
            return false;
            }
            if (key=="nhapmoi") {
            this.btnNhapMoi_Click();
            }
            if (key =="layTT") {
            this.btnLayTT_Click();
            }
            if (key=="huy") {
            this.btnHuy_Click();
            }
            if (key =="xoa") {
            this.btnXoa_Click();
            }
            if (key =="ghilai") {
            this.btnGhiLai_Click();
            }
        },
        btnNhapMoi_Click() {
            this.SetButton(1);
        },
        btnLayTT_Click() {
            //reload lại trang
            this.$router.go();
        },
        btnGhiLai_Click() {
            this.GhiLai();
        },
        btnHuy_Click() {
            this.SetButton(2);
            this.LoadPreview();
        },
        btnXoa_Click() {
            this.Xoa();
        },
        LoadPreview() {
            this.form.phanloai = this.current.phanloaitbd
            this.form.nganhang = this.current.nganhang
            this.txttenTCQLDA = this.current.tenchudautu
            this.txtmaTCQLDA = this.current.machudautu
            this.txtSDT = this.current.sdt
            this.txtchucvu = this.current.chucvu
            this.txtsotaikhoan = this.current.sotaikhoan
            this.txtemail = this.current.email
            this.txtnguoilienhe = this.current.nguoilienhe
            this.txtdiachi = this.current.diachi
            this.txtghichu = this.current.ghichu
            this.txtmasothue = this.current.masothue
        },
        SetButton(kieu) {
            this.button.layTT = true,
            this.button.nhapmoi = false,
            this.button.ghilai = false,
            this.button.huy = false,
            this.button.xoa = false
            if (kieu == -1) //Bắt đầu
            {
                this.button.ghilai = true,
                this.button.huy = true
            }
            if (kieu == 0) //Bat dau
            {
                this.button.nhapmoi = true,
                this.Clear();
            }
            if (kieu == 1) //Them moi
            {
                this.Clear();
                this.button.ghilai = true,
                this.button.huy = true
            }
            if (kieu == 2) //Huy
            {
                this.button.nhapmoi = true
                this.button.ghilai = true
                this.button.huy = true
            }
            if (kieu == 3) {
                this.button.nhapmoi = true,
                this.button.xoa = true, 
                this.button.ghilai = true,
                this.button.huy = true
            }
        }
    },
}
</script>

<style>
li.none-active a{
    color: #d3d3d3 !important;
}
</style>
