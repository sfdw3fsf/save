<template>
<div>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjHenKH" :position="position" :close="closeDialog" :header="'Cập nhật thông tin hẹn'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
        <div class="popup-box">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript:void(0);" @click.prevent="tsbtnCapNhat_Click" rel="frmHenKH"> <span class="icon one-reload1"></span>Cập nhật </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" @click.prevent="Thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w80 padt0">Lựa chọn</div>
                        <div class="value">
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="ckbSendSMS" />
                                <span class="name">Tự động nhắn cho KH</span>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">Ghi chú</div>
                        <div class="value">
                            <input type="text" v-model="txtGhiChu" class="form-control" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Từ giờ</div>
                                <div class="value">
                                    <date-picker :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpNgayHenTu" :type="typeFormat" :disabled-date="disabledRange">
                                        <template #icon-calendar>
                                            <span class="icon one-calendar"></span>
                                        </template>
                                    </date-picker>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Đến giờ</div>
                                <div class="value">
                                    <date-picker :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpNgayHenDen" :type="typeFormat">
                                        <template #icon-calendar>
                                            <span class="icon one-calendar"></span>
                                        </template>
                                    </date-picker>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="table-content">
                    <DataGrid v-bind:columns="gridViewLichSuHen.headers" :enabledSelectFirstRow="true" v-bind:dataSource="gridViewLichSuHen.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
                </div>
            </div>
        </div>
    </ejs-dialog>
</div>
</template>

<script>
// WinUI.WinUIQLDHXLSuCo.frmHenKH
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import {
    flatpickerSetting
} from '../../../../../plugins/pluginInit'
export default {
    name: 'frmHenKH',
    components: {
        moment,
        DatePicker,
        api,
        frmApi
    },
    props: {
        params: {
            type: Object,
            default: function () {
                return null
            }
        }
    },
    data() {
        return {
            Loading: false,
            target: '#app', //  .main-wrapper-payment
            targetTop: '#app', // document.querySelector('body'),.main-wrapper
            animationSettings: {
                effect: 'Zoom'
            },
            position: {
                X: 'center',
                Y: 'top'
            },
            ckbSendSMS: true,
            txtGhiChu: '',
            dtpNgayHenTu: '',
            dtpNgayHenDen: '',
            dateFormat: 'DD/MM/YYYY HH:mm:ss',
            typeFormat: 'datetime',
            cboLyDoHen: '',
            cboLyDoHenList: [],
            gridViewLichSuHen: {
                headers: [{
                        fieldName: 'giohen_tu',
                        headerText: 'Ngày hẹn từ'
                    },
                    {
                        fieldName: 'giohen_den',
                        headerText: 'Ngày hẹn đến'
                    },
                    {
                        fieldName: 'ngay_cn',
                        headerText: 'Ngày CN'
                    },
                    {
                        fieldName: 'nguoi_cn',
                        headerText: 'Người CN'
                    },
                    {
                        fieldName: 'nv_hen',
                        headerText: 'NV hẹn'
                    },
                    {
                        fieldName: 'dv_hen',
                        headerText: 'ĐV hẹn'
                    },
                    {
                        fieldName: 'noidung',
                        headerText: 'Nội dung'
                    }
                ],
                data: [],
                selectedItem: {},
                selectedItems: []
            },
            ngaycn: 0,
            baohong_id: 0,
            phieu_id: 0,
            thuchien: false,
            kt_thoigian_hen_baohong: false,
            khoaphieu: 0,
            ischapnhan: false,
            Quyen_NVKD: 0,
            Quyen_NVTTDH: 0,
            nhantin_kythuat_sauhen: 0, // : 0 : , : 1 : Nhắn tin cho ông kỹ thuật quản lý địa bàn sau khi hẹn khách hàng
            ngayht: '',
            ngayhd: ''
        }
    },
    watch: {
        Loading: function (val) {
            this.$root.showLoading(val)
        }
    },
    created() {
        this.ngaycn = parseInt(moment().format('YYYYMMDDHHmmss'))
    },
    mounted() {
        this.dtpNgayHenTu = moment().format(this.dateFormat)
        this.dtpNgayHenDen = moment().format(this.dateFormat)
    },
    methods: {
        openDialog() {
            try {
                this.getDataFromProp()
                this.Loading = true
                Promise.all([this.frmHenKH_Load()])
                    .then((r) => {
                        console.log(r)
                    })
                    .finally((x) => {
                        this.Loading = false
                    })
            } catch (ex) {
                this.Loading = false
            } finally {
                this.Loading = false
                this.$refs.dialogObjHenKH.show()
            }
        },
        closeDialog() {},
        Thoat() {
            this.$refs.dialogObjHenKH.hide()
        },
        async HienThiDanhSach() {
            try {
                if (this.baohong_id != 0) {
                    //BaoHong_Facade Baohong = new BaoHong_Facade();
                    //DataSet ds = Baohong.Lay_lichsu_hen_baohong(baohong_id);
                    let ds = await this.lay_lichsu_hen_baohong()
                    if (ds != null && ds.length > 0) {
                        this.gridViewLichSuHen.data = ds
                        //gridViewLichSuHen.BestFitColumns();
                    } else {
                        //gridViewLichSuHen.DataSource = null;
                        this.gridViewLichSuHen.data = []
                    }
                }
                //gridViewLichSuHen.DataSource = null;
                else this.gridViewLichSuHen.data = []
            } catch (err) {
                this.ShowError(err.message)
            }
        },
        async map_ttbh_id(baohong_id) {
            // let phanvungid = this.$root.token.getPhanVungID()
            // let input = { id_neo: 'ttbh_id', in_table: 'baohong.baohong', in_dk: `where baohong_id= ${baohong_id} and phanvung_id=${phanvungid}` }
            // let data = this.GetData(await api.map_id(this.axios, input))
            let data = this.GetData(await api.fn_tt_baohong(this.axios, {
                param: baohong_id,
                type: 4
            }))
            return data
        },
        async map_ngay_bh(baohong_id) {
            // let phanvungid = this.$root.token.getPhanVungID()
            // let input = { id_neo: "to_char(ngay_bh,'dd/mm/yyyy hh24:mi:ss')", in_table: 'baohong.baohong', in_dk: `where baohong_id= ${baohong_id} and phanvung_id=${phanvungid} ` }
            // let data = this.GetData(await api.map_id(this.axios, input))
            let data = this.GetData(await api.fn_tt_baohong(this.axios, {
                param: baohong_id,
                type: 3
            }))
            return data
        },
        async tsbtnCapNhat_Click() {
            try {
                let tt_nd = await this.$root.token.getThongTinNguoiDung()
                console.log('tsbtnCapNhat_Click:tt_nd', tt_nd)
                if (this.Quyen_Duoc_hen == 0) {
                    this.ShowError('Bạn chưa được cấp quyền hẹn xử lý với khách hàng')
                    return
                }
                let ttbh = ''
                //nhapt theem 19072017
                ttbh = await this.map_ttbh_id(this.baohong_id) // checkdata.MAP_ID("ttbh_id", DatabaseConstants.DB7 + ".baohong", "where baohong_id=" + baohong_id);
                if (ttbh == '6') {
                    this.ShowError('Báo hỏng đã hoàn thành. Không thể cập nhật giờ hẹn!')
                    return
                } else if (ttbh == '-1') {
                    //thêm trường hợp ngoại lệ cho chắc
                    this.ShowError('Báo hỏng không tồn tại')
                    return
                }
                if (this.dtpNgayHenTu == null || this.dtpNgayHenDen == null) {
                    this.ShowError(`Bạn chưa cập nhật giờ hẹn !`)
                    return
                }
                if (this.dtpNgayHenTu == '' || this.dtpNgayHenDen == '') {
                    this.ShowError(`Bạn chưa cập nhật giờ hẹn !`)
                    return
                }
                //Lấy ngày báo hỏng
                let ngay_bh = await this.map_ngay_bh(this.baohong_id) // checkdata.MAP_ID("to_char(ngay_bh,'dd/mm/yyyy hh24:mi:ss')", DatabaseConstants.DB7 + ".baohong", "where baohong_id=" + baohong_id);
                let dNgayBH = moment(ngay_bh, 'DD/MM/YYYY HH:mm:ss') // DateTime.ParseExact(ngay_bh, "dd/MM/yyyy HH:mm:ss", System.Globalization.CultureInfo.CurrentCulture, System.Globalization.DateTimeStyles.None);
                let dNgayHenTu = moment(this.dtpNgayHenTu, 'DD/MM/YYYY HH:mm:ss')
                let dNgayHenDen = moment(this.dtpNgayHenDen, 'DD/MM/YYYY HH:mm:ss')
                let iNgayHenTu = parseInt(moment(dNgayHenTu).format('YYYYMMDDHHmmss'))
                let iNgayHenDen = parseInt(moment(dNgayHenDen).format('YYYYMMDDHHmmss'))
                let idNgayBH = parseInt(moment(dNgayBH).format('YYYYMMDDHHmmss'))
                let iMoment = this.ngaycn // parseInt(moment().format('YYYYMMDDHHmmss'))
                console.log('iNgayHenTu', iNgayHenTu, 'iNgayHenDen', iNgayHenDen, 'iMoment', iMoment)
                if (iNgayHenTu <= idNgayBH || iNgayHenDen <= idNgayBH) {
                    this.ShowError('Ngày hẹn phải lớn hơn ngày báo hỏng!')
                    return
                }
                // if (dNgayHenTu > dNgayHenDen) {
                if (iNgayHenTu > iNgayHenDen) {
                    this.ShowError('Ngày hẹn từ không thể lớn hơn Ngày hẹn đến. Hãy kiểm tra lại!')
                    return
                }

                if (iNgayHenTu < iMoment) {
                    this.ShowError('Ngày bắt đầu hẹn không thể nhỏ hơn ngày hiện tại!')
                    //dtpNgayHenTu.Focus();
                    return
                }

                if (iNgayHenDen < iMoment) {
                    this.ShowError('Ngày hẹn đến không thể nhỏ hơn ngày hiện tại!')
                    //dtpNgayHenDen.Focus();
                    return
                }

                if (moment(dNgayHenTu).format('HH:mm:ss') == '00:00:00') {
                    this.ShowError('Ngày bắt đầu hẹn có định dạng Giờ:phút:giây là 00:00:00. Hãy kiểm tra lại!')
                    // dtpNgayHenTu.Focus();
                    return
                }

                if (moment(dNgayHenDen).format('HH:mm:ss') == '00:00:00') {
                    this.ShowError('Ngày hẹn đến có định dạng Giờ:phút:giây là 00:00:00. Hãy kiểm tra lại!')
                    // dtpNgayHenDen.Focus();
                    return
                }
                //alert('123')
                if (this.kt_thoigian_hen_baohong) {
                    let ngay_hientai = moment()
                    //System.TimeSpan diffResult = dtpNgayHenDen.Value.Subtract(dtpNgayHenTu.Value);
                    //if (diffResult.Seconds < 0)
                    //{
                    //    this.ShowError("Thời điểm hẹn bắt đầu không được lớn hơn điểm hẹn kết thúc!");
                    //    return;
                    //}

                    // if (dtpNgayHenTu.Value.Date.Subtract(ngay_hientai.Date).Seconds < 0 || dtpNgayHenDen.Value.Date.Subtract(ngay_hientai.Date).Seconds < 0)
                    // {
                    //     this.ShowError("Ngày hẹn không được nhỏ hơn ngày hiện tại!");
                    //     return;
                    // }
                    let mNgayHenDen = moment(this.dtpNgayHenDen, 'DD/MM/YYYY HH:mm:ss')
                    let mNgayHenTu = moment(this.dtpNgayHenTu, 'DD/MM/YYYY HH:mm:ss')
                    var diff = mNgayHenDen.diff(mNgayHenTu, 'days')
                    switch (this.khoaphieu) {
                        //báo hỏng
                        case 0: {
                            let songay = 2
                            //var diff = mNgayHenDen.diff(mNgayHenTu,'days');
                            let dt = await this.LAYSO_NGAY_HEN(1)
                            if (dt != null && dt.length > 0) {
                                songay = dt[0]['songay']
                            }
                            if (diff > songay) {
                                this.ShowError('Đối với phiếu báo hỏng thời gian hẹn không quá ' + songay + ' ngày!')
                                return
                            }
                            break
                        }
                        // bảo dưỡng
                        case 2:
                            let songay_bd = 2
                            let dt_bd = await this.LAYSO_NGAY_HEN(3)
                            if (dt_bd != null && dt_bd.length > 0) {
                                songay_bd = dt_bd[0]['songay']
                            }
                            if (diff > songay_bd) {
                                this.ShowError('Đối với phiếu bảo dưỡng thời gian hẹn không quá ' + songay_bd + ' ngày!')
                                return
                            }
                            break
                    }
                }
                this.Loading = true
                // Baohong.capnhat_giohen(baohong_id, phieu_id, tt_nd.nhanvien_id, tt_nd.donvi_id, txtGhiChu.Text, dtpNgayHenTu.Value, dtpNgayHenDen.Value,
                //     tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                let result = await this.capnhat_giohen()
                if (result == false) return
                let kq_sms = ''
                if (this.ckbSendSMS) {
                    try {
                        if (this.baohong_id <= 0) {
                            this.ShowError('Không có báo hỏng để nhắn')
                        } else {
                            let kq = await this.send_sms_ld()
                            if (kq == 1) {
                                kq_sms = '1'
                            }
                            // var kq_ = checkdata.GET_VALUE_FUNC("{?DB2}.send_sms_ld",
                            //         "vkieu", 2,
                            //         "vrid", baohong_id,
                            //         "vma_tb", "",
                            //         "vsdt", "",
                            //         "vmay_cn", tt_nd.may_cn,
                            //         "vnguoi_cn", tt_nd.ma_nd,
                            //         "vip_cn", tt_nd.ip);
                            // if (kq_.ToString() == "1")
                            // {
                            //     kq_sms = "1";
                            // }
                        }
                    } catch (err) {
                        // if (!ex.ToString().Contains("ORA-00904"))
                        //     this.ShowError("Có lỗi khi send sms cho khách hàng " + ex);
                    }
                }
                if (kq_sms == '1') this.ShowSuccess('Cập nhật và nhắn cho khách hàng thành công')
                else this.ShowSuccess('Cập nhật thành công')
                //#region Nhắn tin cho kỹ thuật sau khi hẹn khách hàng
                kq_sms = ''
                if (this.nhantin_kythuat_sauhen == 1) {
                    try {
                        if (this.baohong_id <= 0) {
                            this.ShowError('Không có báo hỏng để nhắn')
                        } else {
                            let kq = this.send_sms_hen_kythuat()
                            if (kq == 1) {
                                kq_sms = '1'
                            }
                            // var kq_ = checkdata.GET_VALUE_FUNC("{?DB2}.SEND_SMS_HEN_KYTHUAT",
                            //         "vkieu", 2,
                            //         "vrid", baohong_id,
                            //         "vma_tb", "",
                            //         "vnoidung_hen", txtGhiChu.Text,
                            //         "vmay_cn", tt_nd.may_cn,
                            //         "vnguoi_cn", tt_nd.ma_nd,
                            //         "vip_cn", tt_nd.ip);
                            // if (kq_.ToString() == "1")
                            // {
                            //     kq_sms = "1";
                            // }
                        }
                    } catch (ex) {
                        // if (!ex.ToString().Contains("ORA-00904"))
                        //     this.ShowError("Có lỗi khi send sms cho nhân viên kỹ thuật " + ex);
                    }
                }
                //#endregion
                this.$emit("CapnhatSuccess")
                await this.HienThiDanhSach()
                this.thuchien = true
                this.ischapnhan = true
                this.ngayht = this.dtpNgayHenTu
                this.ngayhd = this.dtpNgayHenDen
                this.Close()
            } catch (err) {
                this.thuchien = false
                this.ischapnhan = false
                this.ShowError(`${err.message}`)
            } finally {
                this.Loading = false
            }
        },
        disabledRange: function (date) {
            return date < moment().subtract(1, 'days')
        },
        async frmHenKH_Load() {
            try {
                let ds = await this.LAY_DS_THAMSO_MD()
                if (ds != null && ds.length > 0) {
                    let str_vtemp = 'NHANTIN_KYTHUAT_KHIHEN'
                    // List<DataRow> det = ds.Tables[0].AsEnumerable()
                    //                         .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();
                    let det = ds.filter((x) => x.ma_ts == str_vtemp)
                    if (det != null && det.length > 0) {
                        let row = det[0]
                        if (row['ten_ts'] == '1') {
                            this.nhantin_kythuat_sauhen = 1
                        }
                    }
                    //doan tv kiem tra chi ttkd có quyền hẹn HPG
                    str_vtemp = 'KT_THOIGIAN_HEN_BAOHONG' //kt thời gian hẹn
                    // det = ds.Tables[0].AsEnumerable()
                    //                         .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();
                    det = ds.filter((x) => x.ma_ts == str_vtemp)
                    if (det != null && det.length > 0) {
                        let row = det[0]
                        if (row['ten_ts'] == '1') this.kt_thoigian_hen_baohong = true
                    }
                    //doantv cap nhat chi cho ttkd cap nhat hẹn
                    str_vtemp = 'CHECK_CAPNHAT_HEN'
                    //  det = ds.Tables[0].AsEnumerable()
                    //                         .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();
                    det = ds.filter((x) => x.ma_ts == str_vtemp)
                    let donviId = this.$root.token.getDonViID()
                    if (det != null && det.length > 0) {
                        let row = det[0]
                        if (row['ten_ts'] == '1') {
                            //DataSet thamso_dv = new ThamSo_MDFacade().LAY_DS_THAMSO_MACDINH_DV(tt_nd.donvi_id, 4);
                            let thamso_dv = await this.LAY_DS_THAMSO_MACDINH_DV()

                            //  let sql = "select a.khoaphieu from "+DatabaseConstants.DB7+".baohong  a where a.baohong_id=" + baohong_id;
                            //  let dt = bangts.getDataFromSQL(sql, "dansach").Tables[0];
                            let dt = await this.Lay_KhoaPhieu()
                            if (dt != null && dt.length > 0) {
                                if (dt[0]['khoaphieu'] != null) this.khoaphieu = dt[0]['khoaphieu']
                                switch (khoaphieu) {
                                    //báo hỏng
                                    case 0: {
                                        let str_TTKD = 'TTKD_CAPNHAT_HEN'
                                        //  List<DataRow> dsQuyen = thamso_dv.Tables[0].AsEnumerable()
                                        //                          .Where(x => x["ma_ts"].ToString() == str_TTKD && x["donvi_id"].ToString() == tt_nd.donvi_id.ToString()).ToList();

                                        let dsQuyen = thamso_dv.filter((x) => x.ma_ts == str_TTKD && x.donvi_id == donviId)
                                        if (dsQuyen != null && dsQuyen.length > 0) {
                                            let rowQuyen = dsQuyen[0]
                                            if (rowQuyen['ten_ts'] == '1') {
                                                this.Quyen_Duoc_hen = 1
                                            } else {
                                                this.ShowError('Quyền chưa được thiết lập!')
                                                this.Close()
                                            }
                                        } else {
                                            this.ShowError('Quyền chưa được thiết lập!')
                                            this.Close()
                                        }
                                        break
                                    }
                                    // bảo dưỡng
                                    case 2:
                                        let str_TTDH = 'TTDH_CAPNHAT_HEN'
                                        //  let dsQuyenDH = thamso_dv.Tables[0].AsEnumerable()
                                        //                          .Where(x => x["ma_ts"].ToString() == str_TTDH && x["donvi_id"].ToString() == tt_nd.donvi_id.ToString()).ToList();
                                        let dsQuyenDH = thamso_dv.filter((x) => x.ma_ts == str_TTDH && x.donvi_id == donviId)
                                        let str_TTKD2 = 'TTKD_CAPNHAT_HEN'
                                        // List<DataRow> dsQuyen2 = thamso_dv.Tables[0].AsEnumerable()
                                        //                        .Where(x => x["ma_ts"].ToString() == str_TTKD2 && x["donvi_id"].ToString() == tt_nd.donvi_id.ToString()).ToList();
                                        let dsQuyen2 = thamso_dv.filter((x) => x.ma_ts == str_TTKD2 && x.donvi_id == donviId)
                                        if (dsQuyenDH != null && dsQuyenDH.length > 0) {
                                            let row1 = dsQuyenDH[0]
                                            if (row1['ten_ts'] == '1') {
                                                this.Quyen_NVTTDH = 1
                                            } else {
                                                this.Quyen_NVTTDH = 0
                                            }
                                        } else {
                                            this.Quyen_NVTTDH = 0
                                        }
                                        if (dsQuyen2 != null && dsQuyen2.length > 0) {
                                            let row1 = dsQuyen2[0]
                                            if (row1['ten_ts'] == '1') {
                                                this.Quyen_NVKD = 1
                                            } else {
                                                this.Quyen_NVKD = 0
                                            }
                                        } else {
                                            this.Quyen_NVKD = 0
                                        }
                                        if (this.Quyen_NVKD == 1 || this.Quyen_NVTTDH == 1) {
                                            this.Quyen_Duoc_hen = 1
                                        } else {
                                            this.Quyen_Duoc_hen = 0
                                            this.ShowError('Quyền chưa được thiết lập!')
                                            this.Close()
                                        }
                                        break
                                }
                            }
                        }
                    }
                }
                await this.HienThiDanhSach()
            } catch (ex) {
                this.ShowError(ex.message)
            }
        },
        getDataFromProp() {
            if (this.params != null) {
                let keys = Object.keys(this.params)
                for (const k of keys) {
                    console.log('getDataFromProp:', k, ',value:', this.params[k])
                    if (this.params[k] != null && this.params[k] != undefined) this[k] = this.params[k]
                }
            }
        },
        async Lay_KhoaPhieu() {
            // let data = this.GetData(
            //   await api.lay_dulieu_bang_theo_dieukien(this.axios, {
            //     schema: 'baohong',
            //     table_name: 'baohong',
            //     list_of_cols: 'khoaphieu',
            //     where: `baohong_id= ${this.baohong_id}`,
            //     order: ''
            //   })
            // )
            let data = this.GetData(
                await api.sp_tt_baohong(this.axios, {
                    p_param: this.baohong_id,
                    p_type: 1
                })
            )
            return data
        },
        async LAY_DS_THAMSO_MD() {
            let input = {
                kieu_id: 0
            }
            let data = this.GetData(await api.lay_ds_thamso_macdinh(this.axios, input))
            return data
        },
        async LAY_DS_THAMSO_MACDINH_DV() {
            let donviId = this.$root.token.getDonViID()
            let nhanvienId = this.$root.token.getNhanVienID()
            let input = {
                vdonvi_id: donviId,
                vkieu_id: 4
            }
            let data = this.GetData(await api.lay_ds_thamso_macdinh_dv(this.axios, input))
            return data
        },
        Close() {
            this.$emit('closeHenKH', this.thuchien)
            this.Thoat()
        },
        async lay_lichsu_hen_baohong() {
            let input = {
                p_baohong_id: this.baohong_id
            } // 4516625 this.baohong_id
            let data = this.GetData(await frmApi.lay_lichsu_hen_baohong(this.axios, input))
            return data
        },
        async capnhat_giohen() {
            let donviId = this.$root.token.getDonViID()
            let nhanvienId = this.$root.token.getNhanVienID()
            let ip = this.$root.token.getIP()
            let may_cn = this.$root.token.getMachine()
            let username = this.$root.token.getUserName()
            let tt_nd = this.$root.token.getThongTinNguoiDung()
            let phanvungid = this.$root.token.getPhanVungID()
             let input = { phanvung_id: phanvungid, baohong_id: this.baohong_id, phieu_id: this.phieu_id, nhanvien_id: nhanvienId, donvi_id: donviId, ghichu_ton: this.txtGhiChu, hensuatu: this.dtpNgayHenTu, hensuaden: this.dtpNgayHenDen, nguoi_cn: username, may_cn: 'web', ip_cn: '127.0.0.1' }
            let data = this.GetData(await frmApi.capnhat_giohen(this.axios, input))
            return data
        },
        async LAYSO_NGAY_HEN(loaiphieu) {
            let input = {
                loaiphieu: loaiphieu
            }
            let data = this.GetData(await frmApi.layso_ngay_hen(this.axios, input))
            return data
        },
        async send_sms_ld() {
            let input = {
                vkieu: 2,
                vrid: this.baohong_id,
                vma_tb: '',
                vsdt: ''
            }
            let data = this.GetData(await frmApi.send_sms_ld(this.axios, input))
            return data
        },
        async send_sms_hen_kythuat() {
            try {
                return 0
                let input = {
                    vkieu: 2,
                    vrid: this.baohong_id,
                    vma_tb: '',
                    vsdt: ''
                }
                let data = this.GetData(await frmApi.send_sms_hen_kythuat(this.axios, input))
                return data
            } catch (err) {
                return 0
            }
        },
        GetData: function (response) {
            if ((response.data.error === 200 || response.data.error === '200') && response.data.error_code == 'BSS-00000000') {
                return response.data.data
            } else {
                return response.data.message_detail
            }
            return []
        },
        ShowAlert: function (msg) {
            this.$toast.warning(msg)
        },
        ShowSuccess: function (msg) {
            this.$toast.success(msg)
        },
        ShowError: function (msg) {
            this.$toast.error(msg)
        }
    }
}
</script>
