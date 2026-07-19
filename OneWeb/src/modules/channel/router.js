import LayoutMain from '@/layouts/LayoutMain'
export default [
    {
        path: '/channel',
        name: 'channel',
        component: LayoutMain,
        children: [
            {
                path: 'PhaseCatalog',
                name: 'PhaseCatalog',
                meta: {title: '[URIV.1.1.2] - Danh mục công đoạn'},
                component: () => import('./category/PhaseCatalog')
            },
            {
                path: 'PhaseGroupCatalog',
                name: 'PhaseGroupCatalog',
                meta: {title: '[KHDN-13784] - Danh mục nhóm công đoạn'},
                component: () => import('./category/PhaseGroupCatalog')
            },
            {
                path: 'SaleChannelPayment',
                name: 'SaleChannelPayment',
                meta: {title: 'Chi trả thù lao kênh bán'},
                component: () => import('./SaleChannelPayment')
            },
            {
                path: 'PaymentPolicy',
                name: 'PaymentPolicy',
                meta: {title: '[KHDN-13985] - Chính sách thù lao cho đại lý'},
                component: () => import('./PaymentPolicy')
            },
            {
                path: 'PaymentCalculation',
                name: 'PaymentCalculation',
                meta: {title: '[KHDN-13594] - Tính thù lao, hoa hồng cho Đại lý, CTV'},
                component: () => import('./PaymentCalculation')
            },
            {
                path: 'Publication',
                name: 'Publication',
                meta: {title: 'IV.1.6 -Công bố số liệu'},
                component: () => import('./report/Publication')
            },
            {
                path: 'SalesChannel',
                name: 'SalesChannel',
                meta: {title: 'IV.1.9 -Lịch sử chi trả'},
                component: () => import('./report/SaleChannel')
            },
            {
                path: 'SalesChannelRemuneration',
                name: 'SalesChannelRemuneration',
                meta: {title: 'Chi trả thù lao kênh bán: Gạch nợ'},
                component: () => import('./report/SalesChannelRemuneration')
            },
            {
                path: 'PhieuTra',
                name: 'PhieuTra',
                meta: {title: 'Phiếu trả'},
                component: () => import('./report/PhieuTra')
            },
            {
                path: 'PhieuHuy',
                name: 'PhieuHuy',
                meta: {title: 'Phiếu hủy'},
                component: () => import('./report/PhieuHuy')
            },
            {
                path: 'ArisingScatteredDebts',
                name: 'ArisingScatteredDebts',
                meta: {title: 'Phát sinh công nợ rải rác'},
                component: () => import('./category/ArisingScatteredDebts')
            },
            {
                path: 'ChannelSubscription',
                name: 'ChannelSubscription',
                meta: {title: 'Liệt kê thông tin chung của các phân đoạn'},
                component: () => import('./Search/ChannelSubscription')
            },
            {
                path: 'SearchDataDebit',
                name: 'SearchDataDebit',
                meta: {title: 'Tra cứu khách hàng nợ cước fiber'},
                component: () => import('./Search/DataDebit')
            },
        ]
    }
]
