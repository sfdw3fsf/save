<template>
<div>
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Chọn cộng tác viên</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form">
            <div class="legend-title">
                <div class="pull-left">
                    Thông tin tìm kiếm
                </div>
                <div class="pull-right red">
                    Cộng tác viên: 00030003 - TTAnh
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="info-row">
                <div class="key w90">Nhóm</div>
                <div class="value">
                    <div class="select-custom">
                        <select name="" id="" class="form-control">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">
                    <div class="check-action">
                        <input type="checkbox" class="check">
                        <span class="name">Đơn vị QL</span>
                    </div>
                </div>
                <div class="value">
                    <div class="select-custom">
                        <select name="" id="" class="form-control">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">Danh sách nhân viên</div>
            <div class="table-content">
                <table class="table-result table-gachle">
                    <thead class="center">
                        <tr>
                            <th class="w20"></th>
                            <th>STT</th>
                            <th>Mã thẻ</th>
                            <th>Mã nhân viên</th>
                            <th>Mã đơn vị</th>
                            <th>Tên nhân viên</th>
                            <th>Tên đơn vị</th>
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
</div>
</template>
<style scoped>

</style>
<script>
export default {
    name: 'popupCTV',
    props: [ ],
    watch: {
    },
    data() {
        return {
            page: 0, //Page number
        }
    },
    mounted() {
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',null);
        },
    }
}
</script>