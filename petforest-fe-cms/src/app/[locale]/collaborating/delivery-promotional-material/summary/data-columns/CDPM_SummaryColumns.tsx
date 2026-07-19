import { HospitalDeliverySummaryListDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const CPHD_SummaryColumns = (): TableColumnsType<HospitalDeliverySummaryListDTO> => {
  const { t: tDeliveryPromotionalMaterial } = useTranslation('collaborating_delivery_promotional_material');

  return [
    {
      title: tDeliveryPromotionalMaterial('table.hospital'),
      dataIndex: 'hospitalName',
      width: 200,
      onCell: () => ({
        className: 'text-id',
      }),
      render: (value: string, record: HospitalDeliverySummaryListDTO) => (
        <div
          onClick={() => {
            console.log('id', record.hospitalId);
            if (!record.hospitalId) return;
            window.open(
              `/collaborating/partner-hospital/?id=${record.hospitalId}`,
              '_blank',
              `noopener,noreferrer,width=800,height=${window.screen.availHeight},left=0,top=0`,
            );
          }}
          className='text-id cursor-pointer'
          role='button'
          tabIndex={0}>
          {value}
        </div>
      ),
    },
    {
      title: tDeliveryPromotionalMaterial('table.funeral_connection_count'),
      dataIndex: 'numOfFunerals',
      width: 100,
      render: (value: number) => <div>{value.toLocaleString()}</div>,
    },
    {
      title: tDeliveryPromotionalMaterial('table.request'),
      width: 300,
      className: 'ant-column-center',
      children: [
        {
          title: tDeliveryPromotionalMaterial('table.brochure'),
          dataIndex: 'requestedBrochure',
          width: 100,
          sorter: true,
          className: 'ant-column-center',
          onCell: () => ({
            className: 'ant-column-center',
          }),
          render: (value: number) => <div>{value.toLocaleString()}</div>,
        },
        {
          title: tDeliveryPromotionalMaterial('table.transport_case'),
          width: 100,
          sorter: true,
          className: 'ant-column-center',
          children: [
            {
              title: tDeliveryPromotionalMaterial('table.large'),
              dataIndex: 'requestedBigTransportCase',
              className: 'ant-column-center',
              width: 80,
              sorter: true,
              render: (value: number) => <div>{value.toLocaleString()}</div>,
            },
            {
              title: tDeliveryPromotionalMaterial('table.small'),
              dataIndex: 'requestedSmallTransportCase',
              className: 'ant-column-center',
              width: 80,
              sorter: true,

              render: (value: number) => <div>{value.toLocaleString()}</div>,
            },
          ],
        },
      ],
    },
    {
      title: tDeliveryPromotionalMaterial('table.delivery'),
      width: 300,
      className: 'ant-column-center',
      children: [
        {
          title: tDeliveryPromotionalMaterial('table.brochure'),
          width: 100,
          className: 'ant-column-center',
          sorter: true,
          dataIndex: 'deliveredBrochure',
          render: (value: number) => <div>{value.toLocaleString()}</div>,
        },
        {
          title: tDeliveryPromotionalMaterial('table.transport_case'),
          width: 100,
          className: 'ant-column-center',
          children: [
            {
              title: tDeliveryPromotionalMaterial('table.large'),
              dataIndex: 'deliveredBigTransportCase',
              className: 'ant-column-center',
              width: 80,
              render: (value: number) => <div>{value.toLocaleString()}</div>,
            },
            {
              title: tDeliveryPromotionalMaterial('table.small'),
              dataIndex: 'deliveredSmallTransportCase',
              className: 'ant-column-center',
              width: 80,

              render: (value: number) => <div>{value.toLocaleString()}</div>,
            },
          ],
        },
      ],
    },
  ];
};
