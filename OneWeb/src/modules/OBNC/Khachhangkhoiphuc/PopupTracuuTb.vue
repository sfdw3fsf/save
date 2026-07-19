<template>
    <div class="modal-overlay">
        <div class="modal-container large">
            <div :class="{'popup-box': isPopup}">
                <!-- Tabs Section -->
                <ejs-tab>
                    <e-tabitems>
                        <e-tabitem header="Tab 1">
                            <!-- Nội dung của Tab 1 -->
                            <div class="tab-content">Nội dung của Tab 1</div>
                        </e-tabitem>
                        <e-tabitem header="Tab 2">
                            <!-- Nội dung của Tab 2 -->
                            <div class="tab-content">Nội dung của Tab 2</div>
                        </e-tabitem>
                        <e-tabitem header="Tab 3">
                            <!-- Nội dung của Tab 3 -->
                            <div class="tab-content">Nội dung của Tab 3</div>
                        </e-tabitem>
                    </e-tabitems>
                </ejs-tab>

                <!-- Popup Header -->
                <div class="popup-header">
                    <div class="title">
                        <span class="icon one-notepad"></span>Tra cứu danh bạ
                    </div>
                    <div class="close -ap icon-close" @click="closePopup"></div>
                </div>

                <!-- Action Buttons -->
                <div class="list-actions-top">
                    <ul class="list">
                        <li title="Lấy thông tin">
                            <a href="#" @click.prevent="listData()">
                                <span class="icon one-reload1"></span>Lấy TT
                            </a>
                        </li>
                        <li title="Ghi lại thông tin hợp đồng"> 
                            <a href="#" @click.prevent="saveData()">
                                <span class="icon one-save"></span>Ghi lại
                            </a>
                        </li>
                        <li title="Thực hiện phân giao">
                            <a href="#" @click.prevent="Phangiao()">
                                <span class="icon one-file-arrow-right1"></span>Thực hiện phân giao
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { TabComponent, TabItemDirective, TabItemsDirective } from '@syncfusion/ej2-vue-navigations';

export default {
    props: ['isPopup'],
    components: {
        'ejs-tab': TabComponent,
        'e-tabitem': TabItemDirective,
        'e-tabitems': TabItemsDirective
    },
    methods: {
        closePopup() {
            this.$emit('close');
        },
        listData() {
            // Logic to list data
        },
        saveData() {
            // Logic to save data
        },
        Phangiao() {
            // Logic to execute assignment
        }
    }
}
</script>

<style>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.modal-container {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    position: relative;
    width: 70%; /* Điều chỉnh kích thước lớn hơn */
    max-width: 1000px;
    min-height: 500px; /* Tăng chiều cao tối thiểu */
}

.modal-container.large {
    width: 90%;
    max-width: 1200px; /* Tăng chiều rộng của popup */
}

.popup-header .close {
    cursor: pointer;
    position: absolute;
    right: 10px;
    top: 10px;
}

.e-tab .e-toolbar-item {
    padding: 10px 20px;
}

.tab-content {
    padding: 20px;
    border: 1px solid #e5e5e5;
    margin-top: 10px;
}
</style>