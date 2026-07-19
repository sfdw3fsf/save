<template>
    <div>
        <vue-bread-crumb :header="header" />
        <vue-nav>
            <ul class="list">
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
                    @click="clickButton('huybo')"
                    :class="button.huybo ? 'active' : 'none-active'"
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
                <div class="legend-title">Thông tin Mail Sever</div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Sever<span class="red">(*)</span></div>
                            <div class="value">
                                <input 
                                v-model="txtServer"
                                type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Username<span class="red">(*)</span></div>
                            <div class="value">
                                <input 
                                v-model="txtUsername"
                                type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w70">Ghi chú</div>
                            <div class="value">
                                <input 
                                v-model="txtGhiChu"
                                type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w70">Password<span class="red">(*)</span></div>
                            <div class="value">
                                <input 
                                v-model="txtPassword"
                                type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w70">Port<span class="red">(*)</span></div>
                            <div class="value">
                                <input 
                                v-model="txtPort"
                                type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách Mail Sever</div>
                    <DataGrid :columns="columns_danh_sach_mail_server" ref="noidung" :dataSource="options.danh_sach_mail_server" :enable-paging-server="false"
                    :allowPaging="true" :showFilter="true" @selectedRowChanged="onMailServerSelected">
                    </DataGrid>
            </div>
        </div> 
    </div>
</template>
<script>
import define from "./define";
import moment from 'moment'
export default {
    data() {
        return {
            ...define,
        };
    },
    mounted() {
        this.loadNoiDungMailSerVer();
        
    },

    methods: {
        loadNoiDungMailSerVer() {
            this.LoadGrid();
            this.SetButton(3);
        },

        async btnXoa_click() {
            if (this.MAIL_SERVER_ID == null) {
                this.$toast("Bạn chưa chọn Mail server cần xóa!");
                return false;
            }

            this.$confirm(
                "Bạn chắc chắn muốn xóa thông tin Mail server này không?",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không",
                    type: "warning",
                }
            ).then(async () => {
                try {
                    await this.axios.post(
                        "web-quantri/mail_server/xoa_mail_server",
                        {
                           "mailsv_id": this.MAIL_SERVER_ID, 
                        }
                    );
                    this.$toast.success("Xóa Mail Server trên dữ liệu viễn thông tỉnh thành công!");
                    this.LoadGrid();
                    this.SetButton(3);
                } catch (e) {
                    this.$toast.error("Không thể xóa bản ghi này")
                }
            });
        },
        async ghilai() {
           try{
                if (!this.KiemTraDuLieu()) 
                    return false
                this.loading(true);
                let ngay_cn = moment(new Date()).format('DD/MM/YYYY hh:mm:ss');
                let may_cn = this.$root.token.getMaNhanVien();
                let nguoi_cn = this.$root.token.getUserName();
                let mailsv_id = 0
                if (this.button.nhapmoi) {
                    mailsv_id = this.MAIL_SERVER_ID
                }
                let json_mail = [
                                {
                                    MAILSV_ID: mailsv_id,
                                    SERVER: this.txtServer,
                                    USERNAME: this.txtUsername,
                                    PASSWORD: this.txtPassword,
                                    PORT: this.txtPort,
                                    NGAY_CN: ngay_cn,
                                    MAY_CN: may_cn,
                                    NGUOI_CN: nguoi_cn,
                                    GHICHU: this.txtGhiChu
                                }
                            ];
                if (!this.button.nhapmoi) {
                    let rsnhapmoi = await this.axios.post(
                        "/web-quantri/mail_server/insert_mail_server",
                        {
                            json_mail_server: JSON.stringify(json_mail)
                        }
                    );
                    if (rsnhapmoi.data.data == null) {
                        this.$toast.error("Thêm mới không thành công!");
                        this.loading(false);
                    } else {
                        this.$toast.success("Thêm mới Mail Server vào cơ sở dữ liệu tỉnh thành công!");
                    }
                } 
                else {
                    let rsupdate = await this.axios.post(
                        "web-quantri/mail_server/update_mail_server", 
                        {
                            json_mail_server: JSON.stringify(json_mail)
                        }
                    );
                    if (rsupdate.data.request_id == null) {
                        this.$toast.error("Cập nhật không thành công!");
                        this.loading(false);
                    } else {
                         this.$toast.success("Cập nhật Mail Server vào cơ sở dữ liệu tỉnh thành công!");
                    }
                }
                this.loading(false);                  
                this.LoadGrid();
                this.SetButton(3);
           } catch(e) {
               this.$toast.error("Lỗi!")
               this.loading(false);  
           }         
        },
        async LoadGrid(val) {
            try {
                let res = await this.axios.get("/web-quantri/mail_server/lay_ds_mail_server", 
                {
                    "mailsvId": 0
                })
                this.options.danh_sach_mail_server = res.data.data
                if (this.options.danh_sach_mail_server != null) {
                    this.options.danh_sach_mail_server = [...this.options.danh_sach_mail_server]
                }
                if (this.options.danh_sach_mail_server.length == 0) {
                    console.log("danh sách rỗng")
                    this.SetButton(1);
                }
            } catch (err) {
                this.$toast.error("Lỗi!");
            }

        },
        clickButton(key) {
            if(!this.button[key]) {
                return false;
            }
            if (key=="ghilai") {
                this.ghilai();
            }
            if (key=="nhapmoi") {
                this.btnNhapMoi_Click();
            }
            if (key=="huybo") {
                this.btnHuyBo_Click();
            }
            if (key=="xoa") {
                this.btnXoa_click();
            }
        },
        btnNhapMoi_Click() {
            this.SetButton(1);
        },
        btnHuyBo_Click() {
            this.SetButton(2);
            this.LoadPreview();            
        },
        LoadPreview(){
            this.txtServer = this.current.server
            this.txtUsername = this.current.username
            this.txtPassword = this.current.password
            this.txtPort = this.current.port
            this.txtGhiChu = this.current.ghichu
            this.MAIL_SERVER_ID = this.current.id
        },
        SetButton(kieu) {
            this.button.nhapmoi = false;
            this.button.huybo = false;
            this.button.xoa = false;
            this.button.ghilai = false;
            if (kieu == - 1) {
                 // start
                 this.button.ghilai = true;
                 this.button.huybo = true;
            }
            if (kieu == 0) {
                // bat dau...
                this.button.nhapmoi = true;
                this.Clear();
            }
            if (kieu == 1) {
                //nhap moi
                this.button.ghilai = true;
                this.button.huybo = true;
                this.Clear();
            }
            if (kieu == 2) {
                //huy
                this.button.nhapmoi = true;
                this.button.xoa = true;
                this.button.ghilai = true;
                // this.Clear();
            }
            if (kieu == 3) {
                //Edit
                this.button.nhapmoi = true;
                this.button.ghilai = true;
                this.button.xoa = true;
                this.button.huybo = true;
            }
        },
        KiemTraDuLieu() {
           if (this.txtServer == "") {
                this.$toast.warning("Bạn chưa nhập thông tin Server!");
                return false;
            }
            if (this.txtUsername == "") {
                this.$toast.warning("Bạn chưa nhập thông tin Username!");
                return false;
            }
            if (this.txtPassword == "") {
                this.$toast.warning("Bạn chưa nhập thông tin Password!");
                return false;
            }
            if (this.txtPort == "") {
                this.$toast.warning("Bạn chưa nhập thông tin Port!");
                return false;
            }
            return true;
        },
        onMailServerSelected(e) {
            
            if (e !== null) {
                this.txtServer = e.server;   
                this.txtUsername = e.username
                this.txtPassword = e.password
                this.txtPort = e.port
                this.txtGhiChu = e.ghichu
                this.MAIL_SERVER_ID = e.mailsv_id  

                this.current.server = e.server
                this.current.username = e.username
                this.current.password = e.password
                this.current.port = e.port
                this.current.ghichu = e.ghichu
                this.current.id = e.mailsv_id          
            }
        },
        Clear() {
            this.txtServer = null
            this.txtUsername =null
            this.txtPassword = null
            this.txtPort = null
            this.txtGhiChu = null
        },
    }
}
</script>
<style>
li.none-active a{
    color:#d3d3d3 !important;
}
</style>