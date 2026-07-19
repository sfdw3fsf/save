<template>
   
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#"  @click.prevent="btnLayTT">
                        <span class="icon one-file-attach"></span> Lấy TT
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon nc-icon-glyph arrows-1_simple-left"></span> Thu lại
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnNewClicked">
                        <span class="icon one-file-plus"></span> Nhập mới
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnUpdateClicked" :class="{disabled:!isAddNew && !isUpdate}">
                        <span class="icon  one-reload1"></span> Cập nhật
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnHuyBo" :class="{disabled:!isAddNew && !isHuy}">
                        <span class="icon  ui-1_circle-remove nc-icon-glyph"></span> Huỷ bỏ
                    </a>
                </li>
                <li>
                    <a href="#" :class="{'disabled':(isAddNew || !listbox.ds_file_hs.value || !listbox.ds_file_hs.value.loaifile_id)}" @click.prevent="btnDeleteClicked">
                        <span class="icon one-trash"></span> Xoá file
                    </a>
                </li>
                <li>
                    <a href="#" :class="{'disabled':isAddNew || !listbox.ds_file_hs.value || !listbox.ds_file_hs.value.loaifile_id}" @click.prevent="btnDeleteTBClicked">
                        <span class="icon  one-trash"></span> Xoá TB
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-book1"></span> Hướng dẫn
                    </a>
                </li>
                
            </ul>
             <UploadEProfile />
        </div>
       
        
   
</template>
<script>
import UploadEProfile from "./UploadEProfile.vue"
export default {
    components: {
        UploadEProfile
    }
}
</script>
