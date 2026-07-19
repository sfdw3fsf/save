<template>
    <b-modal
    ref="popupThonSoTSL"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> THÔNG SỐ TRUYỀN SỐ LIỆU
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">

        <div class="grid-stack-box">
          <div class="box-col box-form" style="width: calc(50% - 8px);">
            <div class="legend-title">Thông tin dịch vụ tỉnh bán</div>

            <div class="info-row">
                <div class="key w120"></div>
                <div class="value" style="height: 32px !important; padding: 0.375rem 0.75rem; font-size: 1rem;">
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Loại kênh</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Tốc độ</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Chủ quản</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Thiết bị đầu</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Thiết bị cuối</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Trang bị</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đối tượng</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Mức cước</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đơn vị TC</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>

          </div>
          <div class="gutter gutter-horizontal" style="width: 16px;"></div>
          <div class="box-col box-form" style="width: calc(50% - 8px);" >
            <div class="legend-title">Thông tin dịch vụ tỉnh thi công</div>
            <div class="info-row">
                <div class="key w120">Kiểu lắp đặt</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Loại kênh</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Tốc độ</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Chủ quản</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Thiết bị đầu</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Thiết bị cuối</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Trang bị</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đối tượng</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Mức cước</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đơn vị TC</div>
                <div class="value">
                    <div class="select-custom">
                        <select class="form-control">
                            <option></option>
                        </select>
                    </div>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
//WinUI.WinUIBanCheo.frmThongSoTSL
export default {
    name:'ThongSoTSLModal',
    props:{
        tinhtc:{
            type:Number,
            default:0
        },
        ploaitb_ld:{
            type:Number,
            default:0
        },
        ploaitb:{
            type:Number,
            default:0
        },
        kieuld_id:{
            type:Number,
            default:0
        },
        pthongso_Ban:{
            type:Object,
            default:()=>{}
        },
        pthongso_TC:{
            type:Object,
            default:()=>{}
        },
        vloaihd:{
            type:Number,
            default:0
        },
        vkenhtrang:{
            type:Boolean,
            default:false
        }
    },
    components:{
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-save'
                }
            ],
        }
    },
    methods:{
        showModal() {
            this.$refs["popupThonSoTSL"].show()
        },
        hideModal() {
            this.$refs["popupThonSoTSL"].hide()
        },
        onActionClick(action){
            
        },
        handleShowModal(){

        }
    }

}
</script>