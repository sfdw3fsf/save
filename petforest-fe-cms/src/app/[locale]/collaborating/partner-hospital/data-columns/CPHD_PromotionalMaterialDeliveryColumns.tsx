import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import Status from '@/components/common/Status';
import EditableHeader from '@/components/common/table/EditableHeader';
import { HospitalDeliveryDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { HospitalDeliveryUpdateREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter } from '@/utils/helpers/common.helper';
import { FilterOptions } from '@/utils/types/option.type';
import {
  handleBlurUpdate,
  handleChangeDateUpdate,
  handleChangeUpdate,
  handleNumericUpdate,
} from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { Dayjs } from 'dayjs';
import { useTranslation } from 'react-i18next';

type CPHD_PromotionalMaterialDeliveryColumnsProps = {
  onUpdateHospitalDelivery: (id: number, values: HospitalDeliveryUpdateREQ) => void;
  managerOptions: FilterOptions;
};
export const CPHD_PromotionalMaterialDeliveryColumns = ({
  onUpdateHospitalDelivery,
  managerOptions,
}: CPHD_PromotionalMaterialDeliveryColumnsProps): TableColumnsType<HospitalDeliveryDTO> => {
  const { t: tDeliveryPromotionalMaterial } = useTranslation('collaborating_delivery_promotional_material');

  const { t: tEnum } = useTranslation('enum');
  const { t: tCommon } = useTranslation('common');

  const STATUS_FILTER = helperFilter('status', ENUM_STATUS, tEnum);

  return [
    {
      title: <EditableHeader title={tDeliveryPromotionalMaterial('table.request')} />,
      width: 300,
      className: 'ant-column-center',
      children: [
        {
          title: tDeliveryPromotionalMaterial('table.brochure'),
          dataIndex: 'requestedBrochure',
          width: 100,
          className: 'ant-column-center',
          render: (value: number, record) => (
            <CustomNumericFormat
              defaultValue={value}
              onBlur={handleNumericUpdate<HospitalDeliveryUpdateREQ>(
                record.id,
                'requestedBrochure',
                value,
                onUpdateHospitalDelivery,
              )}
            />
          ),
        },
        {
          title: tDeliveryPromotionalMaterial('table.transport_case'),
          width: 100,
          className: 'ant-column-center',
          children: [
            {
              title: tDeliveryPromotionalMaterial('table.large'),
              dataIndex: 'requestedBigTransportCase',
              className: 'ant-column-center',
              width: 80,
              sorter: true,
              render: (value: number, record) => (
                <CustomNumericFormat
                  defaultValue={value}
                  onBlur={handleNumericUpdate<HospitalDeliveryUpdateREQ>(
                    record.id,
                    'requestedBigTransportCase',
                    value,
                    onUpdateHospitalDelivery,
                  )}
                />
              ),
            },
            {
              title: tDeliveryPromotionalMaterial('table.small'),
              dataIndex: 'requestedSmallTransportCase',
              className: 'ant-column-center',
              width: 80,
              sorter: true,
              key: '238',
              render: (value: number, record) => (
                <CustomNumericFormat
                  defaultValue={value}
                  onBlur={handleNumericUpdate<HospitalDeliveryUpdateREQ>(
                    record.id,
                    'requestedSmallTransportCase',
                    value,
                    onUpdateHospitalDelivery,
                  )}
                />
              ),
            },
          ],
        },
      ],
    },
    {
      title: <EditableHeader title={tDeliveryPromotionalMaterial('table.delivery')} />,
      width: 300,
      className: 'ant-column-center',
      children: [
        {
          title: tDeliveryPromotionalMaterial('table.brochure'),
          width: 100,
          className: 'ant-column-center',
          dataIndex: 'deliveredBrochure',
          render: (value: number, record) => (
            <CustomNumericFormat
              defaultValue={value}
              onBlur={handleNumericUpdate<HospitalDeliveryUpdateREQ>(
                record.id,
                'deliveredBrochure',
                value,
                onUpdateHospitalDelivery,
              )}
            />
          ),
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
              sorter: true,
              render: (value: number, record) => (
                <CustomNumericFormat
                  defaultValue={value}
                  onBlur={handleNumericUpdate<HospitalDeliveryUpdateREQ>(
                    record.id,
                    'deliveredBigTransportCase',
                    value,
                    onUpdateHospitalDelivery,
                  )}
                />
              ),
            },
            {
              title: tDeliveryPromotionalMaterial('table.small'),
              dataIndex: 'deliveredSmallTransportCase',
              className: 'ant-column-center',
              width: 80,
              sorter: true,
              key: '238',
              render: (value: number, record) => (
                <CustomNumericFormat
                  defaultValue={value}
                  onBlur={handleNumericUpdate<HospitalDeliveryUpdateREQ>(
                    record.id,
                    'deliveredSmallTransportCase',
                    value,
                    onUpdateHospitalDelivery,
                  )}
                />
              ),
            },
          ],
        },
      ],
    },
    {
      title: <EditableHeader title={tDeliveryPromotionalMaterial('table.request_date')} />,
      dataIndex: 'requestedAt',
      width: 130,
      sorter: true,
      render: (value: Dayjs, record) => (
        <CustomDatePicker
          defaultValue={value}
          onChange={handleChangeDateUpdate<HospitalDeliveryUpdateREQ>(record.id, 'requestedAt', onUpdateHospitalDelivery)}
        />
      ),
    },
    {
      title: tDeliveryPromotionalMaterial('table.delivery_status'),
      dataIndex: 'deliveryStatus',
      width: 100,
      sorter: true,
      filters: STATUS_FILTER,
      render: (status) =>
        status && <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}` as any)} />,
    },
    {
      title: <EditableHeader title={tDeliveryPromotionalMaterial('table.delivery_date')} />,
      dataIndex: 'deliveredAt',
      width: 130,
      sorter: true,
      render: (value: Dayjs, record) => (
        <CustomDatePicker
          defaultValue={value}
          onChange={handleChangeDateUpdate<HospitalDeliveryUpdateREQ>(record.id, 'deliveredAt', onUpdateHospitalDelivery)}
        />
      ),
    },
    {
      title: <EditableHeader title={tDeliveryPromotionalMaterial('table.person_in_charge')} />,
      dataIndex: 'managerId',
      width: 100,
      sorter: true,
      render: (value: string, record) => (
        <CustomSelect
          variant='borderless'
          options={managerOptions}
          defaultValue={value}
          onChange={handleChangeUpdate<HospitalDeliveryUpdateREQ>(record.id, 'managerId', value, onUpdateHospitalDelivery)}
        />
      ),
    },
    {
      title: <EditableHeader title={tCommon('table.memo')} />,
      dataIndex: 'note',
      width: 200,
      sorter: true,
      render: (text: string, record) => (
        <CustomTextInput
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<HospitalDeliveryUpdateREQ>(record.id, 'note', text, onUpdateHospitalDelivery)}
        />
      ),
    },
  ];
};
