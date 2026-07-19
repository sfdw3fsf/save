
import ChonThietBi from '../../khoa-phieu/chon-thiet-bi'
import ChonCongSoi from '../../khoa-phieu/chon-congsoi'
import ComboMultiSelection from '../../khoa-phieu/tab-item/ComboMultiSelection'
import XemThongTin from '../../modal-xem-thongtin/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'

import { required } from 'vuelidate/lib/validators'

export default function(args, parent) {

    return {
        args: {...args},
        components: {
            ComboMultiSelection,
            ChonThietBi, 
            ChonCongSoi, 
            XemThongTin,
            BssErrorMarker,
            BssRequiredMarker
        },
        template: `
            <div ref="ref-${args.id}" class="tab-pane" :class="{active: active, show: active}">
                <div class="info-row">
                    <div class="key w130">
                        Thiết bị <bss-required-marker />
                    </div>
                    <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="onClickBtnThongTinTbi">
                                <span class="-ap icon-info f20"></span>
                            </button>
                            <bss-error-marker :has-error="$v.data.thietBiId.$error">
                                <div class="input-icon-right">
                                    <input :value="tenTbiChon" type="text" readonly class="form-control" placeholder="Nhấn vào đây để chọn thiết bị" @click="onClickBtnChonThietBi">
                                    <a class="-ap icon-more_horiz icon btn btn-main pad0 h24 w24 text-white" @click="onClickBtnChonThietBi"></a>
                                </div>
                            </bss-error-marker>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Tình trạng <bss-required-marker />
                    </div>
                    <div class="value">
                        <div class="row">
                            <div class="col-12">
                                <bss-error-marker :has-error="$v.data.dsTinhTrangId.$error">
                                <ComboMultiSelection
                                    valueField="TINHTRANG_ID"
                                    textField="TINHTRANG_SC"
                                    v-model="data.dsTinhTrangId"
                                    :dataSource="dsTinhTrangSc"/>
                                </bss-error-marker>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">
                        Cổng / Sợi bị AH <bss-required-marker />
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
                    <div class="key w130">
                        Phỏng đoán <br> nguyên nhân
                    </div>
                    <div class="value">
                        <textarea v-model="data.ghiChuTt" cols="30" rows="10" class="form-control" style="height: 62px;resize: none;"></textarea>
                    </div>
                </div>
                <chon-thiet-bi ref="ref-modal-chon-thietbi" @select="onSelectThietBi"></chon-thiet-bi>
                <chon-cong-soi ref="ref-chon-cong-soi" @select="onSelectCongSoi"></chon-cong-soi>
                <xem-thong-tin ref="ref-modal-xem-thongtin"></xem-thong-tin>
            </div>
        `,
        validations: {
            data: {
                thietBiId:{
                    required
                },
                dsTinhTrangId: {
                    notEmpty: (dsTinhTrangId, vm) => dsTinhTrangId.length > 0
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
                    suCoPtmId: !args.data || !args.data.SUCO_PTM_ID ? null : args.data.SUCO_PTM_ID,
                    loaiTbiId: !args.data || !args.data.LOAIPTM_ID ? null : args.data.LOAIPTM_ID,
                    thietBiId: !args.data || !args.data.PTM_ID ? null : args.data.PTM_ID,
                    tenTbi: !args.data || !args.data.TEN_PTM ? null : args.data.TEN_PTM,
                    tenLoaiTbi: !args.data || !args.data.LOAI_PTM ? null : args.data.LOAI_PTM,
                    dsTinhTrangId: !args.data || !args.data.DS_TINHTRANG ? [] : args.data.DS_TINHTRANG.split(',').map(item => parseInt(item)),
                    congSoi: !args.data || !args.data.CONGSOI ? null : args.data.CONGSOI,
                    ghiChuTt: !args.data || !args.data.GHICHU ? null : args.data.GHICHU,
                },
                nguonScId: !args.data || !args.data.NGUONSC_ID ? null : args.data.NGUONSC_ID,
                dsTinhTrangSc: args.data.dsTinhTrangScMvt
            }
        },
        computed: {
            active() {
                return this.id == this.parent.selectedTabId
            },
            tenTbiChon() {
                return !this.data.thietBiId ? '' : `${this.data.tenLoaiTbi} ${this.data.tenTbi}`
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
        },
        methods: {
            async onClickBtnThongTinTbi() {

                let thongTinTbi = null

                try {
                    this.loading(true)
                    thongTinTbi = await this.layThongTinTbi({
                        loai_tbi: this.data.loaiTbiId,
                        thietbi_id: this.data.thietBiId
                    })
                } catch (err) {
    
                } finally {
                    this.loading(false)
                }

                thongTinTbi = !thongTinTbi || !thongTinTbi.MOTA ? 'Không có thông tin' : thongTinTbi.MOTA
                let modal = this.$refs['ref-modal-xem-thongtin']
                modal.setTitle('Thông tin thiết bị')
                modal.setContent(thongTinTbi)
                modal.show()
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

                if (!this.nguonScId || [1, 3].indexOf(this.nguonScId) >= 0) {

                    if (!this.data.thietBiId) {
                        this.$root.toastError('Bạn chưa chọn thiết bị')
                        return
                    }
                    
                    let modalChonCongSoi = this.$refs['ref-chon-cong-soi']
                    modalChonCongSoi.setLoaiTbiId(this.data.loaiTbiId)
                    modalChonCongSoi.setThietBiId(this.data.thietBiId)
                    modalChonCongSoi.setCongSoi(this.data.congSoi)

                    if (this.nguonScId == 3) {
                        modalChonCongSoi.setKieu(1)
                    }

                    modalChonCongSoi.show()
                }
            },
            onSelectThietBi(dsThietBiDuoChon) {
                let firstItem = dsThietBiDuoChon[0]
                this.data.loaiTbiId = firstItem.LOAITBI_ID
                this.data.thietBiId = firstItem.THIETBI_ID
                this.data.tenLoaiTbi = firstItem.LOAI_TBI
                this.data.tenTbi = firstItem.TEN_TBI
                this.data.congSoi = null
                this.parent.setNameOfTabItemById(this.id, `${firstItem.LOAI_TBI} ${firstItem.TEN_TBI}`)

                let dsComponentTabItem = this.parent.getDsComponentTabItem()
                
                let dsThietBiDaChon = dsComponentTabItem.filter(item => !!item.getData().thietBiId)
                let dsIdThietBiDaChon = dsThietBiDaChon.map(item => {
                    let data = item.getData()
                    return `${data.loaiTbiId}-${data.thietBiId}`
                })
                
                let dsThietBiChuaChon = dsThietBiDuoChon.filter(item => {
                    let thietBiId = `${item.LOAITBI_ID}-${item.THIETBI_ID}`
                    return !dsIdThietBiDaChon.includes(thietBiId)
                })
                
                dsThietBiChuaChon.forEach(item => {

                    let emptyTabComponent = this.parent.getEmptyTabComponent()

                    if (!emptyTabComponent) {
                        let data = {
                            LOAIPTM_ID: item.LOAITBI_ID,
                            PTM_ID: item.THIETBI_ID,
                            LOAI_PTM: item.LOAI_TBI,
                            TEN_PTM: item.TEN_TBI,
                        }
                        this.parent.addTab(data, `${item.LOAI_TBI} ${item.TEN_TBI}`)
                        return
                    }

                    emptyTabComponent.data.loaiTbiId = item.LOAITBI_ID
                    emptyTabComponent.data.thietBiId = item.THIETBI_ID
                    emptyTabComponent.data.tenLoaiTbi = item.LOAI_TBI
                    emptyTabComponent.data.tenTbi = item.TEN_TBI
                    this.parent.setNameOfTabItemById(emptyTabComponent.id, `${item.LOAI_TBI} ${item.TEN_TBI}`)
                })
            },
            onClickBtnChonThietBi() {
                this.$refs['ref-modal-chon-thietbi'].show()
            },
            validateInput() {
                let errorMessage = []

                this.$v.$touch()

                if (this.$v.$invalid) {

                    if (!this.$v.data.thietBiId.required) {
                        errorMessage.push("Xin vui lòng nhập thiết bị bị sự cố")
                    }

                    if (!this.$v.data.dsTinhTrangId.notEmpty) {
                        errorMessage.push("Xin vui lòng nhập tình trạng sự cố")
                    }

                    if (!this.$v.data.congSoi.required) {
                        errorMessage.push("Xin vui lòng nhập cổng sợi bị sự cố")
                    }
                }

                if (errorMessage.length > 0) {
                    throw errorMessage.join('\n')
                }
            },
            getData() {
                return {...this.data}
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
