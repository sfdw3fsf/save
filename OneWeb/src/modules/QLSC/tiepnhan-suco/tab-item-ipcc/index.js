
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
            XemThongTin,
            BssErrorMarker,
            BssRequiredMarker
        },
        template: `
            <div ref="ref-${args.id}" class="tab-pane" :class="{active: active, show: active}">
                <div class="row"> 
                    <div class="col-sm-8 col-12"> 
                        <div class="info-row"> 
                            <div class="key w90"> Hệ thống <bss-required-marker /></div> 
                            <div class="value"> 
                                <div class="input-more-button"> 
                                    <button class="btn" @click="onClickXemThongTinHeThong"> 
                                        <span class="-ap icon-info f20"></span> 
                                    </button> 
                                    <bss-error-marker :has-error="$v.data.heThongItId.$error">
                                        <treeselect
                                            ref="ref-hethong"
                                            v-model="data.heThongItId"
                                            :multiple="false"
                                            :flat="true"
                                            :options="treeOptions"
                                            :searchable="true"
                                            :clearable="false"
                                            :disableFuzzyMatching="true"
                                            noOptionsText="Không có dữ liệu"
                                            noResultsText="Không có dữ liệu"
                                            placeholder="">
                                            <label slot="option-label" slot-scope="{ node, labelClassName }" :class="labelClassName">
                                                <span :title="node.label">{{node.label}}</span>
                                            </label>
                                        </treeselect>
                                    </bss-error-marker>
                                </div> 
                            </div> 
                        </div>
                    </div> 
                    <div class="col-sm-4 col-12"> 
                        <div class="info-row"> 
                            <div class="key w80"> Cấp độ </div> 
                            <div class="value"> 
                                <input type="text" :value="textCapDoSc" class="form-control" readonly> 
                            </div> 
                        </div> 
                    </div> 
                </div>
                <div class="info-row"> 
                    <div class="key w90"> Tình trạng <bss-required-marker /></div> 
                    <div class="value"> 
                        <bss-error-marker :has-error="$v.data.dsTinhTrangId.$error">
                            <ComboMultiSelection
                                valueField="TINHTRANG_ID"
                                textField="TINHTRANG_SC"
                                v-model="data.dsTinhTrangId"
                                :dataSource="dsTinhTrangSc"/>
                        </bss-error-marker>
                    </div> 
                </div>
                <div class="info-row"> 
                    <div class="key w90"> Ghi chú </div> 
                    <div class="value"> 
                        <textarea v-model="data.ghiChuTt" class="form-control" style="height: 100px;resize: none;"></textarea> 
                    </div> 
                </div>
                <xem-thong-tin ref="ref-modal-xem-thongtin"></xem-thong-tin>
            </div>
        `,
        validations: {
            data: {
                heThongItId: {
                    required
                },
                dsTinhTrangId: {
                    notEmpty: (dsTinhTrangId, vm) => dsTinhTrangId.length > 0
                }
            }
        },
        data() {
            return {
                parent: parent,
                id: args.id,
                data: {
                    suCoPtmId: !args.data || !args.data.SUCO_PTM_ID ? null : args.data.SUCO_PTM_ID,
                    heThongItId: !args.data || !args.data.HETHONGIT_ID ? null : args.data.HETHONGIT_ID,
                    dsTinhTrangId: !args.data || !args.data.DS_TINHTRANG ? [] : args.data.DS_TINHTRANG.split(','),
                    capDoId: !args.data || !args.data.CAPDO_ID ? null : args.data.CAPDO_ID,
                    ghiChuTt: !args.data || !args.data.GHICHU ? null : args.data.GHICHU
                },
                dsHeThongCntt: args.data.dsHeThongCntt,
                dsTinhTrangSc: args.data.dsTinhTrangScCntt
            }
        },
        computed: {
            active() {
                return this.id == this.parent.selectedTabId
            },
            heThongDuoChon() {
                return this.dsHeThongCntt.find(item => item.HETHONGIT_ID == this.data.heThongItId)
            },
            textCapDoSc() {
                return !this.heThongDuoChon ? '' : this.heThongDuoChon.CAPDO_SC
            },
            treeOptions() {

                let dsCapDoSc = []
                let dsCapDoScId = []
                
                this.dsHeThongCntt.forEach(item => { 

                    if (dsCapDoScId.indexOf(item.CAPDO_ID) >= 0) {
                        return
                    }

                    dsCapDoScId.push(item.CAPDO_ID)
                    dsCapDoSc.push({
                        CAPDO_ID: item.CAPDO_ID,
                        id: `capdo-${item.CAPDO_ID}`,
                        pid: 0,
                        label: item.CAPDO_SC,
                        children: [],
                        isDisabled: true,
                        isDefaultExpanded: true
                    })
                })

                dsCapDoSc.forEach(capDoSc => {
                    this.dsHeThongCntt.forEach(heThong => {
                        if (heThong.CAPDO_ID == capDoSc.CAPDO_ID) {
                            capDoSc.children.push({
                                id: heThong.HETHONGIT_ID,
                                pid: capDoSc.id,
                                label: heThong.TEN_HT
                            })
                        }
                    })
                })

                return dsCapDoSc
            }
        },
        watch: {
            'data.heThongItId': function(newVal) {
                let tabObj = this.parent.items.find(item => item.args.id == this.id)
                tabObj.args.name = !this.heThongDuoChon ? '' : this.heThongDuoChon.TEN_HT
            }
        },
        methods: {
            async onClickXemThongTinHeThong() {
                let modal = this.$refs['ref-modal-xem-thongtin']
                modal.setTitle('Thông tin hệ thống')
                modal.setContent(!this.heThongDuoChon ? 'Không có thông tin' : this.heThongDuoChon.MOTA)
                modal.show()
            },
            validateInput() {
                let errorMessage = []

                this.$v.$touch()

                if (this.$v.$invalid) {

                    if (!this.$v.data.heThongItId.required) {
                        errorMessage.push("Xin vui lòng nhập hệ thống bị sự cố")
                    }

                    if (!this.$v.data.dsTinhTrangId.notEmpty) {
                        errorMessage.push("Xin vui lòng nhập tình trạng sự cố")
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
