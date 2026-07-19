<template src="./index.template.html"></template>
<script>
export default {
    props: {
        idcDetail: {
            type: Object,
            required: true
        },
        deviceDetail: {
            type: Object,
            required: true
        },
        idPortNguon: {
            type: Number,
            default: null
        },
        typePortNguon: {
            type: Number,
            default: null
        },
    },
    data: function () {
        return {
            deviceTypes: [],
            selectedDeviceType: null,
            deviceList: [],
            selectedDevice: null,
            showSelectCard: false,
            cardList: [],
            selectedCard: null,
            cardTypeList: [
                { ID: 1, TEN: 'Interface' },
                { ID: 2, TEN: 'Power' },
                { ID: 3, TEN: 'Console' }
            ],
            portTypeList: [
                { ID: 1, TEN: 'Interface' },
                { ID: 2, TEN: 'Power' },
                { ID: 3, TEN: 'Console' }
            ],
            selectedCardType: null,
            cardListByType: [],
            cardSelected: null,
            portFreeList: [],
            portSelected: null,
            typePortSelected: null,
            ghiChu: '',
        };
    },
    mounted() {
        this.loadType();
    },
    computed: {

    },
    watch: {
        'cardSelected': function (newVal) {
            this.getPortFreeList();
        },
    },
    created: function () {
        this.getDeviceTypes();
    },
    methods: {
        loadType() {
            switch (this.typePortNguon) {
                case 'Interface': // Interface
                    this.typePortSelected = 1;
                    this.selectedCardType = 1;
                    break;
                case 'Power': // Power
                    this.typePortSelected = 2;
                    this.selectedCardType = 2;
                    break;
                case 'Console': // Console
                    this.selectedCardType = 3;
                    this.selectedCardType = 3;
                    break;
                default:
                    this.typePortSelected = null;
                    this.selectedCardType = null;
            }
        },
        show() {
            console.log('show connect');
        },
        closeModal() {
            this.$emit('close');
        },
        saveLinkDevice() {
            this.$root.context.post(`/web-ecms/hatang/daunoi-thietbi/add-link`, {
                thietBiNguonId: this.deviceDetail.id,
                thietBiDichId: +this.selectedDevice,
                portNguonId: this.idPortNguon,
                portDichId: +this.portSelected,
                ghiChu: this.ghiChu,
            })
                .then(response => {
                    if (response.error_code == 'BSS-00000000') {
                        this.$emit('success', response.data);
                        console.log('Link device saved successfully:', response.data);
                    }
                })
                .catch(error => {
                    console.error('Error saving link device:', error);
                });
        },
        getDeviceTypes() {
            this.$root.context.post(`/web-ecms/hatang/daunoi-thietbi/get-device-types`, {})
                .then(response => {
                    if (response.error_code == 'BSS-00000000') {
                        this.deviceTypes = response.data;
                        console.log('Device types fetched successfully:', this.deviceTypes);
                    }
                })
                .catch(error => {
                    console.error('Error fetching device types:', error);
                });
        },
        onChangeTypeDevice() {
            console.log('Selected device type:', this.selectedDeviceType);
            this.getDeviceList();
        },
        getDeviceList() {
            this.deviceList = [];
            this.selectedDevice = null;
            this.cardList = [];
            this.selectedCard = null;
            this.cardListByType = [];
            this.selectedCardType = null;
            this.cardSelected = null;
            this.portFreeList = [];
            this.portSelected = null;
            this.showSelectCard = false;
            if (this.selectedDeviceType) {
                this.$root.context.post(`/web-ecms/hatang/daunoi-thietbi/get-devices`, {
                    thietBiNguonId: this.deviceDetail.id,
                    idcId: this.idcDetail.ID,
                    loaiThietBiId: this.selectedDeviceType,
                })
                    .then(response => {
                        if (response.error_code == 'BSS-00000000') {
                            this.deviceList = response.data.result;
                            this.deviceList.forEach(device => {
                                device.TENHIENTHI = device.THIETBI_TEN + ' (' + device.THIETBI_MA + ')';
                            });
                            console.log('Device list fetched successfully:', this.deviceList);
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching device list:', error);
                    });
            }
        },
        onChangeDevice() {
            let cards = []
            this.cardList = [];
            this.selectedCard = null;
            this.cardListByType = [];
            this.selectedCardType = null;
            this.cardSelected = null;
            this.portFreeList = [];
            this.portSelected = null;
            this.showSelectCard = false;
            this.deviceList.forEach(device => {
                if (device.THIETBI_ID == this.selectedDevice) {
                    cards = JSON.parse(device.CARD_LIST);
                    this.cardList = cards;
                    if (cards?.length > 0) {
                        this.showSelectCard = true;
                        this.loadType();
                        setTimeout(() => {
                            this.onChangeTypeCard();
                        }, 100);
                    } else {
                        this.showSelectCard = false;
                        setTimeout(() => {
                            this.onChangeTypePort();
                        }, 100);
                    }
                }
            });
        },
        onChangeTypeCard() {
            this.cardListByType = [];
            this.selectedCard = null;
            this.cardSelected = null;
            this.portFreeList = [];
            this.portSelected = null;
            if (this.selectedCardType) {
                switch (this.selectedCardType) {
                    case 1: // Interface
                        this.cardListByType = this.cardList.filter(card => card.is_interface == 1);
                        break;
                    case 2: // Power
                        this.cardListByType = this.cardList.filter(card => card.is_power == 1);
                        break;
                    case 3: // Console
                        this.cardListByType = this.cardList.filter(card => card.is_console == 1);
                        break;
                    default:
                        this.cardListByType = [];
                }
                this.cardListByType.forEach(card => {
                    card.TENHIENTHI = card.card_ten + ' (' + card.card_ma + ')';
                });
            } else {
                this.cardListByType = this.cardList;
                this.cardListByType.forEach(card => {
                    card.TENHIENTHI = card.card_ten + ' (' + card.card_ma + ')';
                });
            }
            console.log('Filtered card list by type:', this.cardListByType);
        },
        onChangeCard() {
            console.log('Selected card:', this.cardSelected);
            this.getPortFreeList();
        },
        async onChangeTypePort() {
            try {
                this.portFreeList = [];
                this.portSelected = null;
                console.log(1);
                await this.getPortFreeList(this.selectedDevice);
                console.log(21);
                switch (this.typePortSelected) {
                    case 1: // Interface
                        this.portFreeList = this.portFreeList.filter(port => port.phanloai == 1);
                        break;
                    case 2: // Power
                        this.portFreeList = this.portFreeList.filter(port => port.phanloai == 2);
                        break;
                    case 3: // Console
                        this.portFreeList = this.portFreeList.filter(port => port.phanloai == 3);
                        break;
                    default:
                        this.portFreeList = [];
                }
                console.log(31);
            } catch (error) {
                console.error('Error filtering port list by type:', error);
            }
        },
        getPortFreeList(deviceId) {
            this.portFreeList = [];
            this.$root.context.post(`/web-ecms/hatang/daunoi-thietbi/get-ports-free`, {
                thietBiId: deviceId,
            })
                .then(response => {
                    if (response.error_code == 'BSS-00000000') {
                        this.portFreeList = response.data.result;
                        console.log('Port free list fetched successfully:', this.portFreeList);
                    }
                })
                .catch(error => {
                    console.error('Error fetching port free list:', error);
                });
        },
        onChangePort() {
            console.log('Selected port:', this.portSelected);
        },
    },
};
</script>
<style scoped></style>