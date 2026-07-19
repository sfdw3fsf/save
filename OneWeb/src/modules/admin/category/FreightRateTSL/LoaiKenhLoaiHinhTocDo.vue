<template>
    <div>
        <!-- Loại kênh -->
        <div class="box-col box-form">
            <div class="box-move-select-table mart10">
                <div class="box-col">
                    <KTableV3 
                        :columns="columnsLoaiKenh"
                        :dataSources="dataSourcesLoaiKenhGan"
                        id="id"
                        tableHeight="120px"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
                <div class="actions">
                    <button class="btn">
                        <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn">
                        <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                </div>
                <div class="box-col">
                    <KTableV3 
                        :columns="columnsLoaiKenh"
                        :dataSources="dataSourcesLoaiKenhChuaGan"
                        id="id"
                        tableHeight="120px"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
            </div>
        </div>
        <!-- Loại hình -->
        <div class="box-col box-form">
            <div class="box-move-select-table mart10">
                <div class="box-col">
                    <KTableV3 
                        :columns="columnsLoaiHinh"
                        :dataSources="dataSourcesLoaiHinhGan"
                        id="id"
                        tableHeight="120px"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
                <div class="actions">
                    <button class="btn">
                        <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn">
                        <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                </div>
                <div class="box-col">
                    <KTableV3 
                        :columns="columnsLoaiHinh"
                        :dataSources="dataSourcesLoaiHinhChuaGan"
                        id="id"
                        tableHeight="120px"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
            </div>
        </div>

        <!-- Tốc độ -->
        <div class="box-col box-form">
            <div class="box-move-select-table mart10">
                <div class="box-col">
                    <KTableV3 
                        :columns="columnsTocDo"
                        :dataSources="dataSourcesTocDoGan"
                        id="id"
                        tableHeight="120px"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
                <div class="actions">
                    <button class="btn">
                        <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn">
                        <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                </div>
                <div class="box-col">
                    <KTableV3 
                        :columns="columnsTocDo"
                        :dataSources="dataSourcesTocDoChuaGan"
                        id="id"
                        tableHeight="120px"
                        :allowFilter="true"
                        :allowCheckBox="true"/>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
export default {
    name:'LoaiKenhLoaiHinhTocDo',
    components:{
        KTableV3
    },
    data(){
        return {
            columnsLoaiKenh:[
                {
                    field:'loaikenh',
                    label:'Loại kênh',
                    allowFilter:true
                }
            ],
            dataSourcesLoaiKenhGan:[],
            dataSourcesLoaiKenhChuaGan:[],
            columnsLoaiHinh:[
                {
                    field:'loaikenh',
                    label:'Loại kênh',
                    allowFilter:true
                }
            ],
            dataSourcesLoaiHinhGan:[],
            dataSourcesLoaiHinhChuaGan:[],
            columnsTocDo:[
                {
                    field:'tocdo',
                    label:'Tốc độ',
                    allowFilter:true
                }
            ],
            dataSourcesTocDoGan:[],
            dataSourcesTocDoChuaGan:[],
        }
    }
}
</script>