
import ChonThietBi from '../chon-thiet-bi'
import ChonCongSoi from '../chon-congsoi'
import ComboMultiSelection from './ComboMultiSelection'
import ComboSingleSelection from './ComboSingleSelection'
import XemThongTin from '../../capnhat-ketqua-xuly-suco/modal-xem-thongtin/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'

export default function(args, parent) {

    args.name = !args.name ? 'New tab' : args.name.trim()

    return {
        args: {...args},
        components: {
            ChonThietBi, 
            ChonCongSoi, 
            ComboMultiSelection, 
            ComboSingleSelection, 
            XemThongTin,
            BssErrorMarker,
            BssRequiredMarker
        },
        template: `
            <div ref="ref-${args.id}" class="tab-pane" :class="{active: active, show: active}" :id="id">
                <div class="info-row">
                    <div class="key w140">
                        Thiết bị <bss-required-marker />
                    </div>
                    <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="onClickBtnThongTinTbi">
                                <span class="-ap icon-info f20"></span>
                            </button>
                            <bss-error-marker :has-error="$v.data.thietBi.thietBiId.$error">
                                <div class="input-icon-right">
                                    <input :value="tenTbiChon" type="text" readonly class="form-control" placeholder="Nhấn vào đây để chọn thiết bị" @click="onClickBtnChonThietBi">
                                    <a class="-ap icon-more_horiz icon btn btn-main pad0 h24 w24 text-white" @click="onClickBtnChonThietBi"></a>
                                </div>
                            </bss-error-marker>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w140">
                        Nguyên nhân <bss-required-marker />
                    </div>
                    <div class="value">
                        <div class="row">
                            <div class="col-sm-6 col-12" ref="ref-select-nhom-nn">
                                <bss-error-marker :has-error="$v.data.nhomNnId.$error">
                                    <ComboSingleSelection
                                        valueField="NHOMNN_ID"
                                        textField="NHOM_NN"
                                        v-model="data.nhomNnId"
                                        :dataSource="parent.dsNhomNn"/>
                                </bss-error-marker>
                            </div>
                            <div class="col-sm-6 col-12">
                                <bss-error-marker :has-error="$v.data.dsNguyenNhanId.$error">
                                    <ComboMultiSelection
                                        valueField="NGUYENNHAN_ID"
                                        textField="NGUYENNHAN"
                                        v-model="data.dsNguyenNhanId"
                                        :dataSource="dsNguyenNhan"/>
                                </bss-error-marker>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w140">
                        Cổng / Sợi bị sự cố <bss-required-marker />
                    </div>
                    <div class="value">
                        <bss-error-marker :has-error="$v.data.congSoi.$error">
                            <div class="input-icon-right">
                                <input type="text" :value="congSoiChon" readonly class="form-control" @click="onClickBtnChonCongSoi">
                                <a class="-ap icon-more_horiz icon btn btn-main pad0 h24 w24 text-white" @click="onClickBtnChonCongSoi"></a>
                            </div>
                        </bss-error-marker>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w140">
                        Ghi chú
                    </div>
                    <div class="value">
                        <textarea v-model="data.ghiChu" cols="30" rows="10" class="form-control" style="height: 62px;resize: none;"></textarea>
                    </div>
                </div>
                <chon-thiet-bi ref="ref-modal-chon-thietbi" @select="onSelectThietBi"></chon-thiet-bi>
                <chon-cong-soi ref="ref-chon-cong-soi" @select="onSelectCongSoi"></chon-cong-soi>
                <xem-thong-tin :modal-id="modalIdXemThongTinTbi" ref="ref-modal-xem-thongtin"></xem-thong-tin>
            </div>
        `,
        validations: {
            data: {
                thietBi: {
                    thietBiId:{
                        required
                    }
                },
                nhomNnId: {
                    required
                },
                dsNguyenNhanId: {
                    notEmpty: (dsNguyenNhanId, vm) => dsNguyenNhanId.length > 0
                },
                congSoi: {
                    required
                }
            }
        },
        data() {
            return {
                parent: parent,
                id: args.id,
                data: {
                    ghiChu: !args.data || !args.data.ghiChu ? null : args.data.ghiChu,
                    nhomNnId: null,
                    dsNguyenNhanId: [],
                    thietBi: {
                        loaiTbiId: !args.data ? null : args.data.loaiTbiId,
                        thietBiId: !args.data ? null : args.data.thietBiId,
                        tenLoaiTbi: !args.data ? null : args.data.tenLoaiTbi,
                        tenTbi: !args.data ? null : args.data.tenTbi,
                    },
                    congSoi: !args.data || !args.data.congSoi ? null : args.data.congSoi,
                    nguonScId: !args.data || !args.data.nguonScId ? null : args.data.nguonScId,
                },
                dsNguyenNhan: [],
                allowReactNguyenNhan: true
            }
        },
        computed: {
            active() {
                return this.id == this.parent.selectedTabId
            },
            tenTbiChon() {
                return !this.data.thietBi.thietBiId ? '' : `${this.data.thietBi.tenLoaiTbi} ${this.data.thietBi.tenTbi}`
            },
            congSoiChon() {
                switch (this.nguonScId) {
                    case 2: // Telsoft NMS - Mạng di động
                        if (!this.data.congSoi) {
                            return 'Không có thông tin chi tiết về thiết bị'
                        }
                        return this.data.congSoi
                    default:
                        if (!this.data.congSoi) {
                            return 'Nhấn vào đây để chọn danh sách cổng hoặc sợi bị sự cố'
                        }
                        return `Đã chọn (${this.data.congSoi.split(',').length})`
                }
            },
            modalIdXemThongTinTbi() {
                return `modal-xem-thongtin-${this.id}`
            }
        },
        watch: {
            'data.nhomNnId': async function(newVal) {

                if (!this.allowReactNguyenNhan) {
                    this.allowReactNguyenNhan = true
                    return
                }

                try {
                    this.loading(true)
                    await this.layDsNguyenNhan(newVal)
                } catch (err) {
    
                } finally {
                    this.loading(false)
                }
            }
        },
        methods: {
            async onClickBtnThongTinTbi() {

                let thongTinTbi = null

                try {
                    this.loading(true)
                    thongTinTbi = await this.layThongTinTbi({
                        loai_tbi: this.data.thietBi.loaiTbiId,
                        thietbi_id: this.data.thietBi.thietBiId
                    })
                } catch (err) {
    
                } finally {
                    this.loading(false)
                }

                thongTinTbi = !thongTinTbi || !thongTinTbi.MOTA ? 'Không có thông tin' : thongTinTbi.MOTA
                this.$refs['ref-modal-xem-thongtin'].setTitle('Thông tin thiết bị')
                this.$refs['ref-modal-xem-thongtin'].setContent(thongTinTbi)
                this.$bvModal.show(this.modalIdXemThongTinTbi)
            },
            onSelectCongSoi(data) {
                this.data.congSoi = data
            },
            onClickBtnChonCongSoi() {

                if (this.nguonScId == 2) {
                    this.$refs['ref-modal-xem-thongtin'].setTitle('Chi tiết sự cố')
                    this.$refs['ref-modal-xem-thongtin'].setContent(this.congSoiChon)
                    return
                }

                if ([1, 3].indexOf(this.nguonScId)) {

                    if (!this.data.thietBi.thietBiId) {
                        this.$root.toastError('Bạn chưa chọn thiết bị')
                        return
                    }
                    
                    let modalChonCongSoi = this.$refs['ref-chon-cong-soi']
                    modalChonCongSoi.setLoaiTbiId(this.data.thietBi.loaiTbiId)
                    modalChonCongSoi.setThietBiId(this.data.thietBi.thietBiId)
                    modalChonCongSoi.setCongSoi(this.data.congSoi)

                    if (this.nguonScId == 3) {
                        modalChonCongSoi.setKieu(1)
                    }

                    modalChonCongSoi.show()
                }
            },
            onSelectThietBi(data) {
                let firstItem = data[0]
                this.data.thietBi.loaiTbiId = firstItem.LOAITBI_ID
                this.data.thietBi.thietBiId = firstItem.THIETBI_ID
                this.data.thietBi.tenLoaiTbi = firstItem.LOAI_TBI
                this.data.thietBi.tenTbi = firstItem.TEN_TBI
                this.data.congSoi = null
                let tabObj = this.parent.items.find(item => item.args.id == this.id)
                tabObj.args.name = `${firstItem.LOAI_TBI} ${firstItem.TEN_TBI}`
                this.parent.onSelectThietBi(data)
            },
            onClickBtnChonThietBi() {
                this.$refs['ref-modal-chon-thietbi'].show()
            },
            validateInput() {
                let errorMessage = []

                this.$v.$touch()

                if (this.$v.$invalid) {

                    if (!this.$v.data.thietBi.thietBiId.required) {
                        errorMessage.push("Hãy chọn thiết bị bị sự cố")
                    }

                    if (!this.$v.data.nhomNnId.required) {
                        errorMessage.push("Hãy chọn nhóm nguyên nhân sự cố")
                    }

                    if (!this.$v.data.dsNguyenNhanId.notEmpty) {
                        errorMessage.push("Hãy chọn nguyên nhân sự cố")
                    }

                    if (!this.$v.data.congSoi.required) {
                        errorMessage.push("Hãy chọn nhóm cổng / sợi bị sự cố")
                    }
                }

                if (errorMessage.length > 0) {
                    throw errorMessage.join('\n')
                }
            },
            getData() {
                return {...this.data}
            },
            setThietBi(value) {
                this.thietBi = value
            },
            async layDsNguyenNhan(nhomNnId) {
                try {
                    let response = await this.$root.context.get(`/web-suco/danhmuc/ds-nguyen-nhan?nhomnn_id=${nhomNnId}`)
            
                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        this.dsNguyenNhan = response.data
                        return
                    } 
                   
                    throw {response: { data: response}}
                } catch (err) {
                    this.dsNguyenNhan = []
                }
            },
            async layThongTinTbi(data) {
                try {
                    let response = await this.$root.context.post(`/web-suco/danhmuc/thongtin-thietbi`, data)
            
                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        return response.data
                    } 
                   
                    throw {response: { data: response}}
                } catch (err) {
                    return null
                }
            },
        }
    }
} 
