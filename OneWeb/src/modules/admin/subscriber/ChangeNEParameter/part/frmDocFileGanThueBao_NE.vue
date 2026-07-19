<template>
<b-modal id="frmDocFileGanThueBao_NE" title="Cập nhật thông tin NE theo file" modal-class="fileGanThueBao-modal" header-class="popup-header" content-class="fileGanThueBao-content" body-class="fileGanThueBao-body overflow-auto" size="xl" hide-footer>
    <div class="popup-body">
        <div class="list-actions-top">
            <ul class="list">
                <li :style="!tsbtnKiemTraDuLieu.Visible ? 'display: none' : ''" @click.prevent="tsbtnKiemTraDuLieu_Click">
                    <a href="javascript:void(0)">
                        <span class="icon one-save"></span> Kiểm tra dữ liệu
                    </a>
                </li>
                <li :style="!tsbtnGhiLai.Visible ? 'display: none' : ''" @click.prevent="tsbtnGhiLai_Click">
                    <a href="javascript:void(0)" :class="{'disabled': !tsbtnGhiLai.Enabled}">
                        <span class="icon one-save"></span> Ghi lại
                    </a>
                </li>
                <li :style="!tsbtnChapNhan.Visible ? 'display: none' : ''" @click.prevent="tsbtnChapNhan_Click">
                    <a href="javascript:void(0)">
                        <span class="icon one-save"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)"  @click.prevent="thoat">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Thoát
                    </a>
                </li>
            </ul>
        </div>
        <div class="grid-stack-box">
            <div class="box-col box-form" id="boxLeft">
                <div class="row">
                    <p><span>{{lblThongBao}}</span></p>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Đường dẫn</div>
                            <input type="file" class="form-control " accept=".xls, .xlsx" @change="onChangeFile($event)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">Danh sách thuê bao khu vực</div>
            <div class="table-content">
                <DataGrid ref="gridDanhSach" v-bind:columns="gridKetQua.header" v-bind:dataSource="gridKetQua.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="false">
                </DataGrid>
            </div>
        </div>
    </div>
</b-modal>
</template>

 
<script>
import moment from "moment";
import Vue from 'vue'
import API from '../api/index'
import xlsx from "xlsx"
export default {
    name: 'frmDocFileGanThueBao_NE',
    components: {
        API
    },
    props: [],
    mounted() {
        this.frmDocFileGanThueBao_NE_Load()
    },
    data() {
        return {
            gridKetQua: {
                list: [],
                header: [{
                        fieldName: 'MA_TB',
                        headerText: 'Mã thuê bao',
                        allowFiltering: true,
                        width: 'auto'
                    },
                    {
                        fieldName: 'NE',
                        headerText: 'NE mới',
                        allowFiltering: true,
                        width: 'auto'
                    },
                    {
                        fieldName: 'NE_ID',
                        headerText: 'NE ID',
                        allowFiltering: true,
                        width: 'auto'
                    },
                    {
                        fieldName: 'TRANG_THAI',
                        headerText: 'Trạng thái',
                        allowFiltering: true,
                        width: 'auto'
                    },
                ],
                value: {},
                isEnabled: true,
            },
            file: null,
            tt_nd: {
                ngay_cn: this.$auth.getNgayCapNhat(),
                tentat: this.$root.token.getMaTinh(),
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                phanvung_id: this.$auth.getPhanVungID()
            },
            _kiemtra: false,
            xmlData: "<ds></ds>",
            tsbtnKiemTraDuLieu: {
                Visible: false,
                Enabled: false
            },
            tsbtnChapNhan: {
                Visible: false,
                Enabled: false
            },
            tsbtnGhiLai: {
                Visible: false,
                Enabled: false
            },
            lblThongBao: "",
            _ts_ne: "",
        };
    },

    methods: {
        thoat(){
            this.$bvModal.hide("frmDocFileGanThueBao_NE")
        },
        show() {
            this.frmDocFileGanThueBao_NE_Load()
            this.$bvModal.show("frmDocFileGanThueBao_NE")
        },
        frmDocFileGanThueBao_NE_Load() {
            if (this.tt_nd.tentat == "HNI") {
                this.tsbtnKiemTraDuLieu.Visible = false;
                this.tsbtnChapNhan.Visible = true;
                this.tsbtnGhiLai.Visible = false;
                this._ts_ne = "NE";
                this.lblThongBao = "File excel gồm 2 trường: MA_TB, NE";
            } else {
                this.tsbtnKiemTraDuLieu.Visible = true;
                this.tsbtnChapNhan.Visible = false;
                this.tsbtnGhiLai.Visible = true;
                this._ts_ne = "NE_ID";
                this.lblThongBao = "File excel gồm 2 trường: MA_TB, NE_ID";
            }
            if (this._kiemtra == false) {
                this.tsbtnGhiLai.Enabled = false;
            }
        },
        async tsbtnGhiLai_Click() {
            if (this.gridKetQua.list) {
                if (this.gridKetQua.list.length > 0) {
                    this.xmlData = this.createXmlStringFromDataTable(this.gridKetQua.list);
                }
            }
            let kq_log = await this.axios.post('/web-tracuu/docfile_ganthuebao_ne/fnc_capnhat_so_ne_theo_file', {
                "vdanhsach": this.xmlData
            })
            if (kq_log == "1") {
                this.showSuccess("Cập nhật dữ liệu thành công !");
            } else {
                this.ShowAlert(kq_log);
            }
        },
        createXmlStringFromDataTable(dataTable) {
            let xml = "<ds>";

            for (let i = 0; i < dataTable.length; i++) {
                xml += "<vt ";
                for (let key in dataTable[i]) {
                    const lowercaseKey = key.toLowerCase();
                    xml += `${lowercaseKey}=\"${dataTable[i][key]}"\ `;
                }
                xml += "></vt>";
            }

            xml += "</ds>";

            return xml;
        },
        async tsbtnKiemTraDuLieu_Click() {
            if (this.gridKetQua.list) {
                if (this.gridKetQua.list.length > 0) {
                    this.xmlData = this.createXmlStringFromDataTable(this.gridKetQua.list);
                }
            }
            let dt_log = await this.axios.post('/web-tracuu/docfile_ganthuebao_ne/sp_cap_nhat_trang_thai_so_ne_theo_file', {
                "vdanhsach": this.xmlData
            })
            this.gridKetQua.list = dt_log.data.data
            if(dt_log.data.data&&dt_log.data.data.length > 0){
                if (dt_log.data.data[0].check_error.toString() == "0") {
                  this.showSuccess("Dữ liệu kiểm tra đã hợp lệ");
                  this._kiemtra = true;
                  this.tsbtnGhiLai.Enabled = true;
                } else {
                    this.$toast.error("Tồn lại dữ liệu không hợp lệ:" + dt_log.data.data[0].trang_thai);
                    this._kiemtra = false;
                    this.tsbtnGhiLai.Enabled = false;
                }
            }
        },
        showAlert(mesage) {
            this.$toast.error(mesage);
        },
        kiemTraDuLieu(columns) {
            console.log(columns)
            if (columns == null) {
                this.ShowAlert('File import có cấu trúc không hợp lệ!')
                return false
            }
            if (columns && columns.length > 0) {
                if (columns[0] != 'MA_TB' || columns[1] != this._ts_ne) {
                    this.ShowAlert('Tên cột trong file không đúng định dạng')
                }
            }
            return true
        },
        showSuccess(mesage) {
            this.$toast.success(mesage);
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
        onChangeFile(event) {
            this.file = event.target.files[0]
            this.readFileExcel()
        },
        checkfile() {
            if (this.file == null) {
                this.ShowAlert('Chưa chọn file excel')
                return false
            }
            if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
                this.ShowAlert('File import không đúng định dạng xlsx!')
                return false
            }
            return true
        },
        async tsbtnChapNhan_Click() {
            this.loading(true)
            console.log(this.gridKetQua.list)
             for (var i = 0; i < this.gridKetQua.list.length; i++) {
                let input = {
                    "vne": this.gridKetQua.list[i]['NE'],
                    "vma_tb": this.gridKetQua.list[i]['MA_TB']
                }
                let res = await this.axios.post('/web-tracuu/docfile_ganthuebao_ne/update_ne_db_cd', input)
            }
            this.showSuccess('Cập nhật dữ liệu thành công !')
            this.loading(false)
        },

        readFileExcel() {
            console.log(1111111111)
            let columns
            this.gridKetQua.list = []
            try {
                // this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    console.log('reader is ', reader)
                    reader.onload = (e) => {
                        /* Parse data */
                        console.log('run in this')
                        const bstr = e.target.result
                        const wb = xlsx.read(bstr, {
                            type: "binary"
                        })

                        console.log('wb is ', wb)
                        /* Get first worksheet */
                        const wsname = wb.SheetNames[0];
                        const ws = wb.Sheets[wsname];
                        /* Convert array of arrays */
                        const data = xlsx.utils.sheet_to_json(ws, {
                            header: 1
                        });
                        console.log('data is ', data)
                        columns = data[0]
                        if (this.kiemTraDuLieu(columns)) {
                            //Check dữ liệu
                            for (var i = 1; i < data.length; i++) {
                                var dynamicProperty = this._ts_ne;
                                var item = {
                                    'MA_TB': data[i][0] ? data[i][0] : ''
                                };
                                item[dynamicProperty] = data[i][1] ? data[i][1] : '';
                                this.gridKetQua.list.push(item);
                            }
                            console.log(333333)
                            console.log(this.gridKetQua.list)
                            //Check dữ liệu
                        }

                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                console.log(error)
                // this.$alert('Có lỗi xảy ra khi đọc file', '', {
                //     dangerouslyUseHTMLString: true,
                //     confirmButtonText: 'OK',
                //     type: 'error'
                // })
                this.ShowAlert('Có lỗi xảy ra khi đọc file')
            } finally {
                // this.loading(false)
            }
        }

    }
}
</script>
 
<style scoped>
.fileGanThueBao-modal .modal-dialog {
    height: 100%;
}

.fileGanThueBao-modal .modal-dialog .fileGanThueBao-content {
    height: 100%;
}

.fileGanThueBao-modal .fileGanThueBao-body {
    overflow-y: auto;
}
</style>
