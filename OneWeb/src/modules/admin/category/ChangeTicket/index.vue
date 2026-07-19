<template>
  <div class="main-wrapper">
        <div class="breadcrumb-top">
            <div class="main-title">phiếu thay đổi thông tin chính sách</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
                    </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
               
              
                <li>
                    <a href="#">
                        <span class="icon one-reload"></span>Làm mới
                    </a>
                </li>
                <li>
                    <a @click="callPopup">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li>
                    <a @click="callPopup1">
                        <span class="icon one-file-plus"></span>Nhập mới 1
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>               
            </ul>
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin phiếu</div>
                        <div class="info-row">
                            <div class="key w130">Loại chính sách</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130">Chính sách</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130">Tên Phiếu</div>
                            <div class="value">
                                <input type="text" ref="test" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130">Hạn xác nhận</div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon one-calendar"></span>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130">Nội dung chỉnh sửa</div>
                            <div class="value">
                                <textarea name="" class="form-control" style="height: 180px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách phiếu</div>
                        <div class="table-content" style="height: 286px;">
                            <table class="table-result table-gachle">
                                <thead class="center">
                                    <tr>
                                        <th class="w20"></th>
                                        <th>Loại chính sách</th>
                                        <th>Tên chính sách</th>
                                        <th>Tên Phiếu</th>
                                        <th>Ngày tạo</th>
                                        <th>Hạn xác nhận</th>
                                        <th>Người tạo</th>
                                    </tr>
                                    <tr>
                                        <th class="w20"></th>
                                        <th>
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control">
                                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                                            </div>
                                        </th>
                                        <th>
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control">
                                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                                            </div>
                                        </th>
                                        <th>
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control">
                                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                                            </div>
                                        </th>
                                        <th>
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control">
                                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                                            </div>
                                        </th>
                                        <th>
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control">
                                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                                            </div>
                                        </th>
                                        <th>
                                            <div class="input-icon-right">
                                                <input type="text" class="form-control">
                                                <span class="icon nc-icon-outline ui-1_zoom"></span>
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="highlight">
                                        <td class="w20 center">
                                            <span class="fa fa-play text-main"></span>
                                        </td>
                                        <td class="selected highlight1"></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="tc-pagination">
                            <a href="#" class="btn  one-arrow-left-forward"></a>
                            <a href="#" class="btn fa fa-angle-double-left"></a>
                            <a href="#" class="btn fa fa-angle-left"></a>
                            <span class="text">Bản ghi</span>
                            <div class="dropdown inline">
                                <div class="fw6 input-number" data-toggle="dropdown">
                                    <span class="text-main">10</span>/1033
                                </div>
                                <div class="dropdown-menu">
                                    <a href="#" class="dropdown-item">10</a>
                                    <a href="#" class="dropdown-item">20</a>
                                </div>
                            </div>
                            <a href="#" class="btn fa fa-angle-right"></a>
                            <a href="#" class="btn fa fa-angle-double-right"></a>
                            <a href="#" class="btn one-arrow-right-forward"></a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Các đơn vị nhận phiếu</div>
                        <div class="table-content" style="height: 700px;">
                            <table class="table-result table-gachle">
                                <thead class="center">
                                    <tr>
                                        <th class="w20"></th>
                                        <th>Đơn vị</th>
                                        <th>Ngày xác nhận</th>
                                        <th>Người xác nhận</th>
                                        <th>Ghi chú của tỉnh</th>
                                    </tr>
                                  
                                </thead>
                                <tbody>
                                    <tr class="highlight">
                                        <td class="w20 center">
                                            <span class="fa fa-play text-main"></span>
                                        </td>
                                        <td class="selected highlight1"></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="w20 center">
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <ChangeIPAccess ref="ChangeIPAccessModal" />
        <ejs-dialog :enableResize='true' :visible="false" ref="popupFrmTraCuuHopDong"
            :header='"Tra cứu hợp đồng, phụ lục"' showCloseIcon=true width="80%" target="#app .main-wrapper" :position="position" height="800">
            <SearchContract ref="SearchContract" :kieu="1" @accept="getDataPopup"/>
        </ejs-dialog>

        <footer class="footer">
            VNPT-IT &copy; 2021
        </footer>
    </div>
</template>
<script>
import breadcrumb from "@/components/breadcrumb";
import Vue from "vue";
import ChangeIPAccess from "@/modules/contract/profile/ChangeIPAccess/ChangeIPAccess.vue";
import SearchContract from "../../../search/subscriber/SearchContract/SearchContract.vue"
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
Vue.use(DialogPlugin);
export default {
     components: {
        breadcrumb,ChangeIPAccess,SearchContract
    },
    data() {
        return {
           position: { X: 'center', Y: 'top' },
        }
    },
    methods:{
        callPopup(){
            this.$refs.ChangeIPAccessModal.showModal();
        },
        callPopup1(){
            this.$refs.popupFrmTraCuuHopDong.$children[0].clearData();
            this.$refs.popupFrmTraCuuHopDong.$children[0].loadPopup();
            this.$refs.popupFrmTraCuuHopDong.show();
        },
        getDataPopup(val){
            this.$refs.popupFrmTraCuuHopDong.hide();
            console.log(val);
        }
    }
}
</script>