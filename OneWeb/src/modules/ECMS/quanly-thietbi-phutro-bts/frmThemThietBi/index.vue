<template src="./index.template.html"></template>
<script>
    import ModalChonDiaChi from '@/modules/EXTS/HOPDONG/ChuanHoaDLKhachHang/popupChonDiaChi'
    import BssErrorMarker from '@/components/BssErrorMarker'
    import BssRequiredMarker from '@/components/BssRequiredMarker'
    import { required } from 'vuelidate/lib/validators'
    import FrmCapNhatGiaTriTT from './frmCapNhatGiaTriTT'
    import FrmChonAnh from './frmChonAnh'

    export default { 
        components: { 
            BssErrorMarker, 
            BssRequiredMarker, 
            ModalChonDiaChi,
            FrmCapNhatGiaTriTT,
            FrmChonAnh
        },
        validations: {
            txtmaTbi: {
                Text: {
                    required
                }
            }
        },
        data() {
            return {
                LOAI_DV: {
                    DONVIQL_LD: 3,
                    TRAM_VT: 5,
                    CSHT_NHATRAM: 555
                },
                propDiaChi: {
                    tieuDe: "Địa chỉ",
                    tinh_id: 0,
                    quan_id: 0,
                    phuong_id: 0,
                    pho_id: 0,
                    dacdiem_id: 0,
                    ap_id: 0,
                    khu_id: 0,
                    so_nha: "",
                    diachi: null
                },
                diachi_id: 0,
                pho_id: 0,
                dacdiem_id: 0,
                ap_id: 0,
                khu_id: 0,
                phuong_id: 0,
                quan_id: 0,
                tinh_id: 0,
                sonha: 0,
                diachi: 0,
                vido: 0,
                kinhdo: 0,
                donvi_id: null,
                to_id: null,
                nhatram_id: null,
                vattu_id_csht: 0,
                nhomtbi_id: 0,
                vvattu_id: 0,
                vthietbi_id: 0,
                capnhat_kt_giatri_tt: false,
                xacnhan: false,
                lstThuocTinh: [],
                txtSoLuong: {
                    Text: 1,
                    Enabled: false
                },
                txtmaTbi: {
                    Text: ""
                },
                txtSerial: {
                    Text: ""
                },
                txtGhiChu: {
                    Text: ""
                },
                cboDonVi: {
                    Key: 1,
                    DataSource: [],
                    Value: null
                },
                cboTramTC: {
                    Key: 1,
                    DataSource: [],
                    Value: null
                },
                cboTramVT: {
                    Key: 1,
                    DataSource: [],
                    Value: null
                },
                cboLoaiTbi: {
                    Key: 1,
                    DataSource: [],
                    Value: null
                },
                cboThietBiCha: {
                    Key: 1,
                    DataSource: [{ THIETBI_ID: 0, MOTA: 'Lựa chọn thiết bị'}],
                    Value: 0
                },
                cboTBI: {
                    Key: 1,
                    DataSource: [],
                    Value: null,
                    Visible: false
                },
                grid_Value: {
                    DataSource: []
                },
                resolvePromise: null,
                isOpeningModal: false
            }
        },
        watch: {
            "cboLoaiTbi.Value": async function() {
                if (this.isOpeningModal) {
                    await this.cboLoaiTbi_EditValueChanged()
                }
            },
            "cboTBI.Value": async function() {
                if (this.isOpeningModal) {
                    await this.cboTBI_EditValueChanged()
                }
            }
        },
        methods: {
            show() {
                this.$refs['ref-modal'].show()
                return new Promise(resolve => this.resolvePromise = resolve)
            },
            hide() {
                this.resolvePromise({ 
                    xacnhan: this.xacnhan,
                    nhomtbi_id: this.nhomtbi_id,
                    vvattu_id: this.vvattu_id
                })
                this.$refs['ref-modal'].hide()
            },
            onModalHidden() {

                this.isOpeningModal = false

                this.$v.$reset()

                this.propDiaChi = {
                    tieuDe: "Địa chỉ",
                    tinh_id: 0,
                    quan_id: 0,
                    phuong_id: 0,
                    pho_id: 0,
                    dacdiem_id: 0,
                    ap_id: 0,
                    khu_id: 0,
                    so_nha: "",
                    diachi: null
                }
                
                this.diachi_id = 0
                this.pho_id = 0
                this.dacdiem_id = 0
                this.ap_id = 0
                this.khu_id = 0,
                this.phuong_id = 0
                this.quan_id = 0
                this.tinh_id = 0
                this.sonha = 0
                this.diachi = 0
                this.vido = 0
                this.kinhdo = 0
                this.donvi_id = null
                this.to_id = null
                this.nhatram_id = null
                this.vattu_id_csht = 0
                this.nhomtbi_id = 0
                this.vvattu_id = 0
                this.vthietbi_id = 0
                this.capnhat_kt_giatri_tt = false
                this.xacnhan = false
                this.lstThuocTinh = []
                this.txtSoLuong.Text = 1
                this.txtSoLuong.Enabled = false
                this.txtmaTbi.Text = ""
                this.txtSerial.Text = ""
                this.txtGhiChu.Text = ""
                this.cboDonVi.DataSource = []
                this.cboDonVi.Value = null
                this.cboTramTC.DataSource = []
                this.cboTramTC.Value = null
                this.cboTramVT.DataSource = []
                this.cboTramVT.Value = null
                this.cboLoaiTbi.DataSource = []
                this.cboLoaiTbi.Value = null
                this.cboThietBiCha.DataSource = [{ THIETBI_ID: 0, MOTA: 'Lựa chọn thiết bị'}]
                this.cboThietBiCha.Value = 0
                this.cboTBI.DataSource = []
                this.cboTBI.Value = null
                this.cboTBI.Visible = false
                this.grid_Value.DataSource = []
                this.resolvePromise = null
            },
            onModalShown() {
                this.isOpeningModal = true
                ++this.cboDonVi.Key
                ++this.cboTramTC.Key
                ++this.cboTramVT.Key
                ++this.cboLoaiTbi.Key
                ++this.cboTBI.Key
                setTimeout(() => this.frmThemThietBi_Load(), 300)
            },
            async frmThemThietBi_Load() {

                this.cboDonVi.DataSource = await this.lay_ds_donvi_cua_loai_dvi({
                    donvi_id: 0,
                    loaidv_id: this.LOAI_DV.DONVIQL_LD
                })

                let donVi = this.cboDonVi.DataSource.find(item => item.DONVI_ID == parseInt(this.donvi_id))

                this.cboDonVi.Value = !donVi ? null : `${donVi.DONVI_ID}`

                if (!!this.donvi_id) {

                    this.cboTramTC.DataSource = await this.lay_ds_donvi_con_loaidv({
                        donvi_id: this.donvi_id,
                        loaidv_id: this.LOAI_DV.TRAM_VT
                    })

                    let tramTC = this.cboTramTC.DataSource.find(item => item.DONVI_ID == parseInt(this.to_id))
                    this.cboTramTC.Value = !tramTC ? null : `${tramTC.DONVI_ID}`
                }

                if (!!this.to_id) {

                    this.cboTramVT.DataSource = await this.lay_ds_donvi_loai_dvi_v2({
                        donvi_id: this.to_id,
                        loaidv_id: this.LOAI_DV.CSHT_NHATRAM
                    })

                    let tramVT = this.cboTramVT.DataSource.find(item => item.DONVI_ID == parseInt(this.nhatram_id))
                    this.cboTramVT.Value = !tramVT ? null : `${tramVT.DONVI_ID}`
                }

                this.cboLoaiTbi.DataSource = await this.lay_ds_thietbi_loaitbi(70)

                if (this.cboLoaiTbi.DataSource.length > 0) {
                    this.cboLoaiTbi.Value = this.cboLoaiTbi.DataSource[0].VATTU_ID
                }

                let ds_kt = await this.lay_ds_thamso_md()

                if (ds_kt.length > 0) {

                    let thamSo = ds_kt.find(item => item.MA_TS == 'VATTU_ID_CSHT')

                    if (!!thamSo) {
                        this.vattu_id_csht = thamSo.TEN_TS
                    }

                    thamSo = ds_kt.find(item => item.MA_TS == 'CAPNHAT_KT_GIATRI_TT')

                    if (!!thamSo) {
                        this.vattu_id_csht = thamSo.TEN_TS
                    }

                    this.capnhat_kt_giatri_tt = !!thamSo && parseInt(thamSo.TEN_TS) == 1
                }
            },
            async tsbtnGhiLai_Click() {

                this.$v.$touch()

                let errorMessage = []

                if (this.$v.$invalid) {

                    if (!this.$v.txtmaTbi.Text.required) {
                        errorMessage.push("Bạn chưa nhập mã tài sản!")
                        this.$refs['ref-txt-ma-tbi'].focus()
                    }
                }

                if (errorMessage.length > 0) {
                    this.$root.toastError(errorMessage.join('\n'))
                    return
                }

                try {
                    this.loading(true)

                    let vdonvi_id = parseInt(this.cboTramVT.Value);
                    let vattu_id = parseInt(this.cboLoaiTbi.Value);
                    let vthietbi_cha_id = 0;

                    if (this.cboThietBiCha.Value) {
                        vthietbi_cha_id = parseInt(this.cboThietBiCha.Value);
                    }
                        
                    //Kiểm tra đối với trạm mới, bắt buộc phải nhập CSHT trước mới cho nhập mới các đối tượng khác 
                    let kiemtra_csht_tram_moi = await this.kiemtra_csht_tram_moi(vdonvi_id) 

                    if (!kiemtra_csht_tram_moi && vattu_id != this.vattu_id_csht)
                    {
                        this.$root.toastError("Trạm mới, vui lòng nhập CSHT trước khi thêm mới các đối tượng khác!");
                        return;
                    }

                    if (this.cboTBI.Visible == true) {
                        this.nhomtbi_id = parseInt(this.cboTBI.Value);
                        this.vvattu_id = vattu_id;
                    }

                    if (!this.txtSoLuong.Enabled)
                    {
                        let data = {
                            donvi_id: vdonvi_id,
                            vattu_id: vattu_id,
                            ma_tbi: this.txtmaTbi.Text,
                            serial: this.txtSerial.Text,
                            ghichu: this.txtGhiChu.Text,
                            nhomtbi_id: this.nhomtbi_id,
                            thietbi_cha_id: vthietbi_cha_id
                        }

                        this.vthietbi_id = await this.phutro_them_tbi_v3(data);
                        await this.ThemThuocTinh(this.vthietbi_id);

                        if (this.tinh_id > 0 && this.quan_id > 0) {
                            data = {
                                thietbi_id: this.vthietbi_id,
                                tinh_id: this.tinh_id,
                                quan_id: this.quan_id,
                                phuong_id: this.phuong_id,
                                pho_id: this.pho_id,
                                khu_id: this.khu_id,
                                ap_id: this.ap_id,
                                dacdiem_id: this.dacdiem_id,
                                sonha: this.sonha,
                                diachi: this.diachi,
                                kinhdo: this.kinhdo,
                                vido: this.vido,
                            }
                            await this.themmoi_diachi_thietbi(data);
                        }
                    }
                    else
                    {
                        for (let i = 0; i < parseInt(this.txtSoLuong.Text); i++)
                        {
                            let data = {
                                donvi_id: vdonvi_id,
                                ttu_id: vattu_id,
                                ma_tbi: this.txtmaTbi.Text,
                                serial: this.txtSerial.Text,
                                ghichu: this.txtGhiChu.Text,
                                nhomtbi_id: this.nhomtbi_id,
                                thietbi_cha_id: vthietbi_cha_id
                            }

                            this.vthietbi_id = await this.phutro_them_tbi_v3(data);
                            await this.ThemThuocTinh(this.vthietbi_id);

                            if (this.tinh_id > 0 && this.quan_id > 0) {
                                data = {
                                    thietbi_id: this.vthietbi_id,
                                    tinh_id: this.tinh_id,
                                    quan_id: this.quan_id,
                                    phuong_id: this.phuong_id,
                                    pho_id: this.pho_id,
                                    khu_id: this.khu_id,
                                    ap_id: this.ap_id,
                                    dacdiem_id: this.dacdiem_id,
                                    sonha: this.sonha,
                                    diachi: this.diachi,
                                    kinhdo: this.kinhdo,
                                    vido: this.vido,
                                }
                                await this.themmoi_diachi_thietbi(data);
                            }
                        }
                    }

                    this.$root.toastSuccess("Cập nhật thành công");
                    this.xacnhan = true;
                    this.hide()
                } catch(err) {
                    this.$root.toastError(err.toString())
                } finally {
                     this.loading(false)
                }
            },
            async themmoi_diachi_thietbi(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/themmoi_diachi_thietbi`, data)
                    
                    if (response.error_code !== 'BSS-00000000') {
                        throw {response: { data: response}}
                    }
                } catch (err) {
                    let responseData = err.response.data
                    let message = [] 

                    if (responseData.message) {
                        message.push(responseData.message)
                    }

                    if (responseData.message_detail) {
                        message.push(responseData.message_detail)
                    }

                    if (responseData.data) {
                        let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                        message.push([...new Set(detailMessage)].join('\n'))
                    }

                    throw message.join('\n')
                }
            },
            async ThemThuocTinh(vthietbi_id) {

                let successcount = 0

                for (let i = 0; i < this.lstThuocTinh.length; i++) {

                    let vttt = this.lstThuocTinh[i]

                    let result = await this.update_vattu_thuoctinh({
                        thietbi_id: vthietbi_id,
                        ttvt_id: vttt.ttvt_id,
                        gttt_id: !vttt.ten ? 0 : vttt.GiaTri,
                        kieutt_id: vttt.kieutt_id,
                        giatri: !vttt.ten ? vttt.GiaTri : vttt.ten
                    })

                    if (result != -1 && result != 0) {
                        successcount++
                    }
                }

                if (successcount > 0) {
                    this.$root.toastSuccess(successcount + " Thuộc tính đã được thêm mới")
                }
            },
            async update_vattu_thuoctinh(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/update_vattu_thuoctinh`, data)
                    return response.data[0].MESSAGE
                } catch (err) {
                    return -1
                }
            },
            view_Value_CustomRowCellEdit(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <div>
                                    <template v-if="dieukhien == 'Thang' || dieukhien == 'Ngay'">
                                        <ejs-datepicker 
                                            :style="{'padding-top': '6px'}"	
                                            class="form-control bg-none bor0 fw6 text h24" 
                                            placeholder=""
                                            format="dd/MM/yyyy"
                                            v-model="value"></ejs-datepicker>
                                    </template>
                                    <template v-else-if="dieukhien == 'Combobox' || dieukhien == 'ComboBox Seach'">
                                        <div v-if="!!data.CauLenh" class="value" ref="ref-select-auto">        
                                            <SelectExt
                                                :settings="{dropdownParent: $refs['ref-select-auto']}"
                                                :dataSource="dataSource" 
                                                v-model="value"
                                                dataValueField="GTTT_ID"
                                                dataTextField="GIATRI" />
                                        </div>
                                    </template>
                                    <template v-else-if="dieukhien == 'Button'">
                                        <div class="value" v-if="data.ThuocTinh == 'Địa chỉ'">
                                            <div style="border: 1px solid transparent; border-radius: 5px;">
                                                <div class="input-icon-right">
                                                    <input type="text" class="form-control" v-model="value"> 
                                                    <a class="-ap icon-more_horiz icon btn btn-main pad0 h24 w24 text-white" @click="parent.rpBtnDiaChi_ButtonClick(data)"></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="value" v-if="parent.capnhat_kt_giatri_tt && ['Lực căng của dây co', 'Khoảng cách móng néo tới tâm cột (m)', 'Vị trí móng néo (lệch so với chính Bắc)'].includes(data.ThuocTinh)">
                                            <div style="border: 1px solid transparent; border-radius: 5px;">
                                                <div class="input-icon-right">
                                                    <input type="text" class="form-control" v-model="value"> 
                                                    <a class="-ap icon-more_horiz icon btn btn-main pad0 h24 w24 text-white" @click="rpBtnGiaTriTT_ButtonClick"></a>
                                                </div>
                                            </div>
                                        </div>
                                    </template>
                                    <template v-else-if="dieukhien == 'Textbox' && parent.capnhat_kt_giatri_tt">
                                        <input v-if="data.ThuocTinh == 'Tọa độ Latitude'" type="text" class="form-control" v-model="value"> 
                                        <input v-else-if="data.ThuocTinh == 'Tọa độ Longitude'" type="text" class="form-control" v-model="value"> 
                                        <input v-else type="text" class="form-control" v-model="value"> 
                                    </template>
                                    <template v-else-if="dieukhien == 'Anh'">
                                        <div style="border: 1px solid transparent; border-radius: 5px;">
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control" v-model="imageFileName"> 
                                                <a class="-ap icon-more_horiz icon btn btn-main pad0 h24 w24 text-white" @click="rpBtnChonAnh_ButtonClick"></a>
                                            </div>
                                        </div>
                                    </template>
                                    <template v-else>
                                        <input type="text" class="form-control" v-model="value"> 
                                    </template>
                                </div>
                            `,
                            data() {
                                return { 
                                    parent: parent,
                                    dataSource: [],
                                    imageFileName: null,
                                    value: null,
                                    data: { }
                                }
                            },
                            watch: {
                                "parent.diachi": function(value) {
                                    if (this.data.ThuocTinh == 'Địa chỉ') {
                                        this.value = value
                                    }
                                },
                                value(newVal) {

                                    let thuocTinh = this.parent.lstThuocTinh.find(item => item.ThuocTinh == this.data.ThuocTinh)

                                    if (this.dieukhien == 'Thang' || this.dieukhien == 'Ngay') {
                                        thuocTinh.GiaTri = this.parent.ddmmyyyy(newVal)
                                    } else {
                                        thuocTinh.GiaTri = newVal
                                    }

                                    if (this.dataSource.length > 0) {
                                        let giaTri = this.dataSource.find(item => item.GTTT_ID == parseInt(newVal))
                                        thuocTinh.ten = !giaTri ? null : giaTri.GIATRI
                                    }

                                    this.data.GiaTri = thuocTinh.GiaTri

                                    if (this.dieukhien == 'Anh') {
                                        if (!!this.data.GiaTri) {
                                            this.imageFileName = this.data.GiaTri.split('|').map(item => item.substr(item.lastIndexOf('/') + 1)).join('|')
                                        } else {
                                            this.imageFileName = null
                                        }
                                    }
                                }
                            },
                            computed: {
                                dieukhien() {
                                    return this.data.Ten_loai
                                }
                            },
                            async mounted() {

                                if ((this.dieukhien == 'Combobox' || this.dieukhien == 'ComboBox Seach') && !!this.data.CauLenh) {
                                    
                                    this.dataSource = await this.parent.lay_ds_giatri_thuoctinh({
                                        vattu_id: this.data.vattu_id, // 10944
                                        thuoctinh_id: this.data.ThuocTinhId // 30
                                    })

                                    let giaTri = this.dataSource.find(item => item.GTTT_ID == parseInt(this.data.GiaTri))
                                    this.value = !giaTri ? null : giaTri.GTTT_ID // 62
                                } else {
                                    this.value = this.data.GiaTri
                                }
                            },
                            methods: {
                                async rpBtnGiaTriTT_ButtonClick() {

                                    if (!this.parent.capnhat_kt_giatri_tt) {
                                        return
                                    }

                                    this.value = await this.parent.rpBtnGiaTriTT_ButtonClick(this.data)
                                },
                                async rpBtnChonAnh_ButtonClick() {
                                    this.value = await this.parent.rpBtnChonAnh_ButtonClick(this.data)
                                }
                            }
                        }
                    }
                }
            },
            onChonDiaChi(data) {
                this.tinh_id = data.tinh_id;
                this.quan_id = data.quan_id;
                this.phuong_id = data.phuong_id;
                this.pho_id = data.pho_id;
                this.dacdiem_id = data.dacdiem_id;
                this.ap_id = data.ap_id;
                this.khu_id = data.khu_id;
                this.sonha = data.so_nha;
                this.diachi = data.diachimoi;
            },
            rpBtnDiaChi_ButtonClick(data) {
                this.diachi = data.GiaTri
                this.propDiaChi = {
                    tieuDe: "Địa chỉ",
                    tinh_id: this.tinh_id,
                    quan_id: this.quan_id,
                    phuong_id: this.phuong_id,
                    pho_id: this.pho_id,
                    dacdiem_id: this.dacdiem_id,
                    ap_id: this.ap_id,
                    khu_id: this.khu_id,
                    so_nha: this.sonha,
                    diachi: this.diachi
                }
                this.$bvModal.show("frm-them-thiet-bi--modal-chon-dia-chi")
            },
            async rpBtnChonAnh_ButtonClick(data) {
                let modal = this.$refs.frmChonAnh
                modal.value = data.GiaTri
                return await modal.show()
            },
            async rpBtnGiaTriTT_ButtonClick(data) {
                let modal = this.$refs.frmCapNhatGiaTriTT
                modal.TenThuocTinh = data.ThuocTinh
                modal.GiaTri = data.GiaTri
                modal.Type = data.ThuocTinh == "Lực căng của dây co" ? 1 : 2
                await modal.frmCapNhatGiaTriTT_Load()
                let result = await modal.show()
                return !result.ChapNhan ? null : result.GiaTri
            },
            async lay_ds_giatri_thuoctinh(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_ds_giatri_thuoctinh`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async cboTBI_EditValueChanged() {

                this.lstThuocTinh = []

                let vattu_id = parseInt(this.cboLoaiTbi.Value);
                let nhomtbi_id = parseInt(this.cboTBI.Value);
                let dt_tt = await this.lay_thongtin_thuoctinh_theo_nhom_thietbi({ vattu_id, nhomtbi_id });

                for (let i = 0; i < dt_tt.length; i++) {

                    let item = dt_tt[i]

                    let vttt = {
                        vattu_id: parseInt(item["VATTU_ID"]),
                        ThuocTinhId: item["TTVT_ID"],
                        ThuocTinh: item["THUOCTINH"],
                        Ten_loai: item["TEN_LOAI"],
                        CauLenh: item["CAULENH"],
                        kieutt_id: parseInt(item["KIEUTT_ID"]),
                        nhom_tt: item["NHOM_TT"],
                        GiaTri: !item["GIATRI"] ? null : item["GIATRI"]
                    }
                    
                    if (vttt.ThuocTinh == "Địa chỉ")
                    {
                        vttt.Ten_loai = "Button";
                    }

                    if (this.capnhat_kt_giatri_tt 
                        && (vttt.ThuocTinh == "Lực căng của dây co" 
                            || vttt.ThuocTinh == "Khoảng cách móng néo tới tâm cột (m)" 
                            || vttt.ThuocTinh == "Vị trí móng néo (lệch so với chính Bắc)"))
                    {
                        vttt.Ten_loai = "Button";
                    }

                    if (!!item["TTVT_ID"]) {
                        vttt.ttvt_id = parseInt(item["TTVT_ID"]);
                    }

                    if (!!item["GTTT_ID"]) {
                        vttt.gttt_id = parseInt(item["GTTT_ID"]);
                    }

                    this.lstThuocTinh.push(vttt);
                }

                this.grid_Value.DataSource = [...this.lstThuocTinh]
            },
            async cboLoaiTbi_EditValueChanged() {

                this.cboTBI.DataSource = await this.lay_ds_thietbi_loaithietbi(this.cboLoaiTbi.Value)

                if (this.cboTBI.DataSource.length == 0)
                {
                    this.cboTBI.Visible = false;
                }
                else
                {
                    this.cboTBI.Visible = true;
                }

                this.lstThuocTinh = []

                let vattu_id = parseInt(this.cboLoaiTbi.Value);
                let nhomtbi_id = parseInt(this.cboTBI.Value);
                let dt_tt = await this.lay_thongtin_thuoctinh_theo_nhom_thietbi({ vattu_id, nhomtbi_id });

                for (let i = 0; i < dt_tt.length; i++) {

                    let item = dt_tt[i]

                    let vttt = {
                        vattu_id: parseInt(item["VATTU_ID"]),
                        ThuocTinhId: item["TTVT_ID"],
                        ThuocTinh: item["THUOCTINH"],
                        Ten_loai: item["TEN_LOAI"],
                        CauLenh: item["CAULENH"],
                        kieutt_id: parseInt(item["KIEUTT_ID"]),
                        nhom_tt: item["NHOM_TT"],
                        GiaTri: !item["GIATRI"] ? null : { Value: item["GIATRI"], ten: null }
                    }
                    
                    if (vttt.ThuocTinh == "Địa chỉ")
                    {
                        vttt.Ten_loai = "Button";
                    }

                    if (this.capnhat_kt_giatri_tt 
                        && (vttt.ThuocTinh == "Lực căng của dây co" 
                            || vttt.ThuocTinh == "Khoảng cách móng néo tới tâm cột (m)" 
                            || vttt.ThuocTinh == "Vị trí móng néo (lệch so với chính Bắc)"))
                    {
                        vttt.Ten_loai = "Button";
                    }

                    if (!!item["TTVT_ID"]) {
                        vttt.ttvt_id = parseInt(item["TTVT_ID"]);
                    }

                    if (!!item["GTTT_ID"]) {
                        vttt.gttt_id = parseInt(item["GTTT_ID"]);
                    }

                    this.lstThuocTinh.push(vttt);
                }

                this.grid_Value.DataSource = [...this.lstThuocTinh]

                this.cboThietBiCha.DataSource = await this.lay_ds_tbi_cha({
                    vattu_id: this.cboLoaiTbi.Value,
                    donvi_id: this.cboTramVT.Value
                })

                this.cboThietBiCha.Value = 0

                this.txtSoLuong.Enabled = await this.phutro_lay_doituong_nhap_sl(this.cboLoaiTbi.Value)
            },
            async phutro_lay_doituong_nhap_sl(vattu_id) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/phutro_lay_doituong_nhap_sl`, { vattu_id })
                    return response.data.length > 0
                } catch (err) {
                    return false
                }
            },
            async lay_ds_tbi_cha(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_ds_tbi_cha`, data)
                    return response.data
                } catch (err) {
                    return [{ THIETBI_ID: 0, MOTA: 'Lựa chọn thiết bị'}]
                }
            },
            async lay_thongtin_thuoctinh_theo_nhom_thietbi(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_thongtin_thuoctinh_theo_nhom_thietbi`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_thietbi_loaithietbi(vattu_id) {
                try {
                    let response = await this.$root.context.get(`/web-ecms/them_thietbi/lay_ds_thietbi_loaithietbi`, { vattu_id })
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async phutro_them_tbi_v3(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/phutro_them_tbi_v3`, data)
                    return response.data[0].THIETBI_ID
                } catch (err) {
                    return null
                }
            },
            async kiemtra_csht_tram_moi(donvi_id) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/kiemtra_csht_tram_moi`, { donvi_id })
                    return response.data[0].KQ != 0
                } catch (err) {
                    return false
                }
            },
            async lay_ds_thamso_md() {
                try {
                    let response = await this.$root.context.get(`/web-ecms/them_thietbi/lay_ds_thamso_md`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_donvi_loai_dvi_v2(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_ds_donvi_loai_dvi_v2`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_donvi_con_loaidv(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_ds_donvi_con_loaidv`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_donvi_cua_loai_dvi(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_ds_donvi_cua_loai_dvi`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_thietbi_loaitbi(ma_vt) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/them_thietbi/lay_ds_thietbi_loaitbi`, { ma_vt })
                    return response.data
                } catch (err) {
                    return []
                }
            },
            getDateElements(date) {

                date = new Date(date)

                let day = date.getDate()
                day = day > 9 ? day : '0' + day

                let month = date.getMonth() + 1
                month = month > 9 ? month : '0' + month

                let hour = date.getHours()
                hour = hour > 9 ? hour : '0' + hour

                let min = date.getMinutes()
                min = min > 9 ? min : '0' + min

                let second = date.getSeconds()
                second = second > 9 ? second : '0' + second

                return {
                    y: date.getFullYear(),
                    M: month,
                    d: day,
                    h: hour,
                    m: min,
                    s: second
                }
            },
            ddmmyyyy(date) {

                if (!date) {
                    return ''
                }
                
                date = this.getDateElements(date)
                return `${date.d}/${date.M}/${date.y}`
            },
        }
    }
</script>